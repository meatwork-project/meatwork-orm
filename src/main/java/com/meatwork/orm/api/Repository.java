package com.meatwork.orm.api;


import com.meatwork.orm.internal.Change;
import com.meatwork.orm.internal.ModeTransactional;

import java.util.List;

/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
public interface Repository<T extends Entity, ID> {
	T findById(ID id);

	List<T> findAll();

	T save(T entity);

	boolean deleteById(ID id);

	default SqlQueryBuilder update(T entity) {
		var sqlQueryBuilder = SqlQueryBuilder.of(((AbstractMeatEntity)entity).getTableName());
		var transactionalProperties = ((AbstractMeatEntity) entity).getTransactionalProperties();
		for (var entry : transactionalProperties.entrySet()) {
			sqlQueryBuilder.update(entry.getKey(), entry.getKey(), entry.getValue());
		}
		((AbstractMeatEntity)entity).setModeTransactional(ModeTransactional.UPDATE);
		return sqlQueryBuilder;
	}

	default SqlQueryBuilder insert(T entity) {
		var transactionalProperties = ((AbstractMeatEntity) entity).getTransactionalProperties();
		var queryBuilder = SqlQueryBuilder.of(((AbstractMeatEntity)entity).getTableName());
		queryBuilder.insert(transactionalProperties);
		((AbstractMeatEntity)entity).setModeTransactional(ModeTransactional.UPDATE);
		return queryBuilder;
	}

	default T changesToEntity(Change[] changes,
	                          T entity) {
		for (var change : changes) {
			((AbstractMeatEntity) entity).updateProperty(
					change.fieldName(),
					change.newValue()
			);
		}
		((AbstractMeatEntity)entity).save();
		((AbstractMeatEntity)entity).setModeTransactional(ModeTransactional.UPDATE);
		return entity;
	}
}
