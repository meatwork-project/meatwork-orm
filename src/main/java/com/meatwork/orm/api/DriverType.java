package com.meatwork.orm.api;


/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
public enum DriverType {
	POSTGRES("org.postgresql.Driver"),
	SQLITE("org.sqlite.JDBC"),
	H2("org.h2.Driver")
	;

	private final String value;

	DriverType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
