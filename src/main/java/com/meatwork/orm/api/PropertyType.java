package com.meatwork.orm.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
public enum PropertyType {
	BOOLEAN(Boolean.class),
	STRING(String.class),
	INTEGER(Integer.class),
	LONG(Long.class),
	BIGDECIMAL(BigDecimal.class),
	LOCALDATE(LocalDate.class),
	LOCALDATETIME(LocalDateTime.class),
	LOCALTIME(LocalTime.class),
	ENTITY_REF(EntityRef.class)
	;

	private final Class<?> type;

	PropertyType(Class<?> type) {
		this.type = type;
	}

	public Class<?> getType() {
		return type;
	}
}
