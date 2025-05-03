package com.meatwork.orm.api;


import com.meatwork.orm.internal.ModeTransactional;

import java.util.HashMap;
import java.util.Map;

/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
public abstract class AbstractMeatEntity implements Entity {

	private final Map<String, Object> properties = new HashMap<>();
	private Map<String, Object> transactionalProperties = new HashMap<>();
	private ModeTransactional modeTransactional = ModeTransactional.CREATE;

	public abstract String getTableName();
	public abstract MetaProperty[] getMetaProperties();

	public void updateProperty(String fieldName,
	                                Object value) {
		transactionalProperties.put(
				fieldName,
				value
		);
	}

	@SuppressWarnings("unchecked")
	public  <T> T getProperty(String fieldName) {
		return (T) properties.get(fieldName);
	}

	public void save() {
		properties.putAll(transactionalProperties);
		transactionalProperties = new HashMap<>();
	}

	Map<String, Object> getTransactionalProperties() {
		return transactionalProperties;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setModeTransactional(ModeTransactional modeTransactional) {
		this.modeTransactional = modeTransactional;
	}

	public ModeTransactional getModeTransactional() {
		return modeTransactional;
	}
}
