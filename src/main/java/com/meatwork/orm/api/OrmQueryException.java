package com.meatwork.orm.api;


/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
public class OrmQueryException extends Exception {

	public OrmQueryException(String message) {
		super(message);
	}

	public OrmQueryException(String message,
	                         Throwable cause) {
		super(
				message,
				cause
		);
	}

	public OrmQueryException(Throwable cause) {
		super(cause);
	}
}
