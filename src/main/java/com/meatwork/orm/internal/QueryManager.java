package com.meatwork.orm.internal;


import com.meatwork.common.Preconditions;
import com.meatwork.orm.api.EntityRef;
import com.meatwork.orm.api.EntityRefProperty;
import com.meatwork.orm.api.MetaProperty;
import com.meatwork.orm.api.OrmQueryException;
import com.meatwork.orm.api.SqlQueryBuilder;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
public final class QueryManager {

	private final ConnectionManager connectionManager;

	public QueryManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;

	}

	public List<Change[]> executeQuery(SqlQueryBuilder query,
	                                   MetaProperty[] metaProperties) throws SQLException, OrmQueryException {
		try {
			return prepareStatement(query, insertStmt -> {
				var resultSet = insertStmt.executeQuery();
				List<Change[]> listChange = new ArrayList<>();
				while (resultSet.next()) {
					var changes = new Change[metaProperties.length];
					for (int i = 0; i < metaProperties.length; i++) {
						var value = convertTypeToObject(
								resultSet,
								metaProperties[i]
						);
						changes[i] = value;
					}
					listChange.add(changes);
				}
				return listChange;
			});
		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	public boolean executeUpdate(SqlQueryBuilder query) throws SQLException, OrmQueryException {
		return prepareStatement(query, insertStmt -> insertStmt.executeUpdate() > 0);
	}

	private <T> T prepareStatement(SqlQueryBuilder query, ThrowFn<PreparedStatement,T> supplier) throws SQLException, OrmQueryException {
		var statementData = query.buildPreparedStatement();
		try (Connection conn = connectionManager.getConnection()) {
			var sql = statementData.getSql();
			PreparedStatement insertStmt = conn.prepareStatement(sql);
			var index = 1;
			for (var prepareStatement : statementData.getParameters().values()) {
				convertObjectToType(
						index++,
						insertStmt,
						prepareStatement
				);
			}

			return supplier.apply(insertStmt);
		}
	}

	public boolean executeUpdate(String query) throws SQLException {
		try (Connection conn = connectionManager.getConnection()) {
			var statement = conn.createStatement();
			return statement.executeUpdate(query) > 0;
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	private void convertObjectToType(int index, PreparedStatement preparedStatement,
	                                 final Object value) throws SQLException {
		Preconditions.checkNotNull(value, "Value is null into prepareStatement");
		if (value
				.getClass()
				.equals(String.class)) {
			preparedStatement.setString(
					index,
					(String) value
			);
		} else if (value
				.getClass()
				.equals(Short.class)) {
			preparedStatement.setShort(
					index,
					(Short) value
			);
		} else if (value
				.getClass()
				.equals(LocalDate.class)) {
			preparedStatement.setDate(
					index,
					Date.valueOf((LocalDate) value)
			);
		} else if (value
				.getClass()
				.equals(LocalDateTime.class)) {
			preparedStatement.setTimestamp(
					index,
					Timestamp.valueOf((LocalDateTime) value)
			);
		} else if (value
				.getClass()
				.equals(LocalTime.class)) {
			preparedStatement.setObject(
					index,
					value
			);
		} else if (value
				.getClass()
				.equals(Double.class)) {
			preparedStatement.setDouble(
					index,
					(Double) value
			);
		} else if (value
				.getClass()
				.equals(BigDecimal.class)) {
			preparedStatement.setBigDecimal(
					index,
					(BigDecimal) value
			);
		} else if (value
				.getClass()
				.equals(Integer.class)) {
			preparedStatement.setInt(
					index,
					(Integer) value
			);
		} else if (value
				.getClass()
				.equals(Long.class)) {
			preparedStatement.setLong(
					index,
					(Long) value
			);
		} else if (value
				.getClass()
				.equals(Float.class)) {
			preparedStatement.setFloat(
					index,
					(Float) value
			);
		} else if (value
				.getClass()
				.equals(Character.class)) {
			preparedStatement.setString(
					index,
					Character.toString((Character) value)
			);
		} else if (value
				.getClass()
				.equals(Boolean.class)) {
			preparedStatement.setBoolean(
					index,
					(Boolean) value
			);
		} else if (value
				.getClass()
				.equals(EntityRef.class)) {
			convertObjectToType(index, preparedStatement, ((EntityRef<?>) value).id());
		} else {
			throw new SQLException("Object " + value.getClass() + " not supported");
		}
	}


	private Change convertTypeToObject(ResultSet resultSet,
	                                   MetaProperty metaProperty) throws SQLException, OrmQueryException {
		return switch (metaProperty.type()) {
			case STRING -> new Change(
					metaProperty.fieldName(),
					resultSet.getString(metaProperty.fieldName()),
					metaProperty
							.type()
							.getType()
			);
			case LOCALDATE -> new Change(
					metaProperty.fieldName(),
					resultSet
							.getDate(metaProperty.fieldName())
							.toLocalDate(),
					metaProperty
							.type()
							.getType()
			);
			case LOCALDATETIME -> new Change(
					metaProperty.fieldName(),
					resultSet
							.getTimestamp(metaProperty.fieldName())
							.toInstant()
							.atZone(ZoneId.systemDefault())
							.toLocalDateTime(),
					metaProperty
							.type()
							.getType()
			);
			case LOCALTIME -> new Change(
					metaProperty.fieldName(),
					resultSet
							.getTimestamp(metaProperty.fieldName())
							.toInstant()
							.atZone(ZoneId.systemDefault())
							.toLocalTime(),
					metaProperty
							.type()
							.getType()
			);
			case BIGDECIMAL -> new Change(
					metaProperty.fieldName(),
					resultSet.getBigDecimal(metaProperty.fieldName()),
					metaProperty
							.type()
							.getType()
			);
			case INTEGER -> new Change(
					metaProperty.fieldName(),
					resultSet.getInt(metaProperty.fieldName()),
					metaProperty
							.type()
							.getType()
			);
			case LONG -> new Change(
					metaProperty.fieldName(),
					resultSet.getLong(metaProperty.fieldName()),
					metaProperty
							.type()
							.getType()
			);
			case BOOLEAN -> new Change(
					metaProperty.fieldName(),
					resultSet.getBoolean(metaProperty.fieldName()),
					metaProperty
							.type()
							.getType()
			);
			case ENTITY_REF -> {
				EntityRefProperty entityRefProperty = Arrays
						.stream(metaProperty.extraMetraProperties())
						.filter(it -> it
								.getClass()
								.equals(EntityRefProperty.class))
						.findFirst()
						.map(EntityRefProperty.class::cast)
						.orElseThrow(() -> new OrmQueryException("Cannot find EntityRefProperty"));
				Object value = switch (entityRefProperty.typeId()) {
					case INTEGER -> resultSet.getInt(metaProperty.fieldName());
					case STRING -> resultSet.getString(metaProperty.fieldName());
					case LONG -> resultSet.getLong(metaProperty.fieldName());
					default -> throw new OrmQueryException("EntityRef not supported for type " + metaProperty.type());
				};
				yield new Change(
					metaProperty.fieldName(),
					new EntityRef<>(null, value),
					metaProperty
							.type()
							.getType()
			);
			}
		};
	}

	@FunctionalInterface
	private interface ThrowFn<T,V> {
		V apply(T t) throws SQLException, OrmQueryException;
	}
}
