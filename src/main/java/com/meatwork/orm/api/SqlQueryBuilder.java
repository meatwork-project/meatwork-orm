package com.meatwork.orm.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/*
 * Classe utilitaire pour construire des requêtes SQL standard avec support pour PreparedStatement et paramètres nommés.
 */
public class SqlQueryBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(SqlQueryBuilder.class);

	private String tableName;
	private String queryType;
	private final List<String> columns = new ArrayList<>();
	private final Map<String, Object> namedParameters = new LinkedHashMap<>();
	private final List<String> conditions = new ArrayList<>();
	private final List<String> updates = new ArrayList<>();

	public static SqlQueryBuilder of(String tableName) {
		return new SqlQueryBuilder().table(tableName);
	}

	// Définir la table cible
	public SqlQueryBuilder table(String tableName) {
		this.tableName = tableName;
		return this;
	}

	// Construire une requête SELECT
	public SqlQueryBuilder select(String... columns) {
		this.queryType = "SELECT";
		if (columns != null && columns.length > 0) {
			this.columns.addAll(Arrays.asList(columns));
		} else {
			this.columns.add("*");
		}
		return this;
	}

	// Construire une requête INSERT
	public SqlQueryBuilder insert(Map<String, Object> params) {
		this.queryType = "INSERT";
		this.columns.addAll(new ArrayList<>(params.keySet()));
		this.namedParameters.putAll(params);
		return this;
	}

	// Ajouter des valeurs pour INSERT avec des paramètres nommés
	public SqlQueryBuilder values(Map<String, Object> params) {
		this.namedParameters.putAll(params);
		return this;
	}

	// Construire une requête UPDATE avec des paramètres nommés
	public SqlQueryBuilder update(String column, String paramName, Object value) {
		this.queryType = "UPDATE";
		this.updates.add(column + " = :" + paramName);
		this.namedParameters.put(paramName, value);
		return this;
	}

	// Ajouter une condition WHERE avec des paramètres nommés
	public SqlQueryBuilder where(String condition, Map<String, Object> params) {
		this.conditions.add(condition);
		this.namedParameters.putAll(params);
		return this;
	}

	// Ajouter une requête DELETE
	public SqlQueryBuilder delete() {
		this.queryType = "DELETE";
		return this;
	}

	// Générer la requête SQL finale
	public String build() {
		if (queryType == null || tableName == null) {
			throw new IllegalStateException("Type de requête ou table non spécifiée.");
		}

		StringBuilder query = new StringBuilder();

		switch (queryType) {
			case "SELECT" -> {
				query.append("SELECT ")
				     .append(String.join(", ", columns))
				     .append(" FROM ")
				     .append(tableName);
			}
			case "INSERT" -> {
				query.append("INSERT INTO ")
				     .append(tableName)
				     .append(" (")
				     .append(String.join(", ", columns))
				     .append(") VALUES (")
				     .append(namedParameters.keySet().stream()
				                            .map(key -> ":" + key)
				                            .reduce((a, b) -> a + ", " + b)
				                            .orElse(""))
				     .append(")");
			}
			case "UPDATE" -> {
				query.append("UPDATE ")
				     .append(tableName)
				     .append(" SET ")
				     .append(String.join(", ", updates));
			}
			case "DELETE" -> query.append("DELETE FROM ").append(tableName);
		}

		if (!conditions.isEmpty()) {
			query.append(" WHERE ").append(String.join(" AND ", conditions));
		}

		return query.toString();
	}

	// Générer un PreparedStatement avec les paramètres nommés
	public PreparedStatementData buildPreparedStatement() {
		String sql = build();
		return new PreparedStatementData(sql, namedParameters);
	}

	// Classe interne pour contenir la requête SQL et les paramètres nommés
	public static class PreparedStatementData {
		private final String sql;
		private final Map<String, Object> parameters;

		public PreparedStatementData(String sql, Map<String, Object> parameters) {
			this.sql = sql;
			this.parameters = parameters;
		}

		public String getSql() {
			return this.sql.replaceAll(":[a-zA-Z0-9_]+", "?");
		}

		public Map<String, Object> getParameters() {
			return parameters;
		}
	}
}
