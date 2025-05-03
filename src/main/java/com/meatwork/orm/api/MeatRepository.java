package com.meatwork.orm.api;


/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */

import com.meatwork.common.Preconditions;
import com.meatwork.orm.internal.ConnectionManager;
import com.meatwork.orm.internal.EntityManager;
import com.meatwork.orm.internal.ModeTransactional;
import com.meatwork.orm.internal.QueryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


public abstract class MeatRepository<T extends Entity, ID> implements Repository<T, ID> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MeatRepository.class);

	private final QueryManager queryManager;
	private final Class<T> cls;

	public MeatRepository(Class<T> cls,
	                      ConnectionManager connectionManager) {
		this.queryManager = new QueryManager(connectionManager);
		this.cls = cls;

	}

	@Override
	public T findById(ID id) {
		var entity = getEntity();
		var metaProperties = ((AbstractMeatEntity)entity).getMetaProperties();
		MetaProperty metaPropertyId = null;
		for (var metaProperty : metaProperties) {
			if (metaProperty.primaryKey()) {
				metaPropertyId = metaProperty;
				break;
			}
		}

		Preconditions.checkNotNull(
				metaPropertyId,
				"Entity " + entity
						.getClass()
						.getName() + " cannot have id property"
		);
		Preconditions.checkNotNull(
				metaProperties,
				"Cannot found id property"
		);

		try {
			var changes = queryManager.executeQuery(
					SqlQueryBuilder.of(((AbstractMeatEntity)entity).getTableName())
							.select()
							.where(metaPropertyId.fieldName() + " = :id",
							       Map.of("id", id)),
					metaProperties
			);

			if(changes.isEmpty()){
				return null;
			}

			return changesToEntity(
					changes.getFirst(),
					entity
			);
		} catch (SQLException | OrmQueryException e) {
			LOGGER.error(
					e.getMessage(),
					e
			);
			return null;
		}
	}

	@Override
	public List<T> findAll() {
		var entity = getEntity();
		var metaProperties = ((AbstractMeatEntity)entity).getMetaProperties();
		try {
			var changes = queryManager.executeQuery(
					SqlQueryBuilder
							.of(((AbstractMeatEntity)entity).getTableName())
							.select()
					,
					metaProperties
			);
			return changes
					.stream()
					.map(it -> changesToEntity(
							it,
							EntityManager.get(cls)
					))
					.toList();
		} catch (SQLException | OrmQueryException e) {
			LOGGER.error(
					e.getMessage(),
					e
			);
			return null;
		}
	}

	@Override
	public T save(T entity) {
		var metaProperties = ((AbstractMeatEntity)entity).getMetaProperties();
		var metaProperty = Stream
				.of(metaProperties)
				.filter(MetaProperty::primaryKey)
				.findFirst()
				.orElse(null);

		SqlQueryBuilder query;
		if (metaProperty != null && ModeTransactional.CREATE.equals(((AbstractMeatEntity)entity).getTransactionalProperties())) {
			query = update(entity);
		} else {
			query = insert(entity);
		}

		try {
			queryManager.executeUpdate(
					query
			);
			((AbstractMeatEntity)entity).save();
			return entity;
		} catch (SQLException | OrmQueryException e) {
			LOGGER.error(
					e.getMessage(),
					e
			);
			return null;
		}
	}

	@Override
	public boolean deleteById(ID id) {
		var entity = getEntity();
		var metaProperties = ((AbstractMeatEntity)entity).getMetaProperties();
		MetaProperty metaPropertyId = null;
		for (var metaProperty : metaProperties) {
			if (metaProperty.primaryKey()) {
				metaPropertyId = metaProperty;
				break;
			}
		}

		Preconditions.checkNotNull(
				metaPropertyId,
				"Entity " + entity
						.getClass()
						.getName() + " cannot have id property"
		);

		return deleteById(id, ((AbstractMeatEntity)entity).getTableName(), metaPropertyId.fieldName());
	}

	private boolean deleteById(ID id, String tableName, String fieldNameId) {
		try {
			return queryManager.executeUpdate(SqlQueryBuilder.of(tableName)
					.delete()
					.where(fieldNameId + " = :id", Map.of("id", id)));
		} catch (SQLException | OrmQueryException e) {
			LOGGER.error(
					e.getMessage(),
					e
			);
			return false;
		}
	}

	private T getEntity() {
		return EntityManager.get(cls);
	}
}
