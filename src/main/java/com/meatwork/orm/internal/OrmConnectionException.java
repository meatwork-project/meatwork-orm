package com.meatwork.orm.internal;


/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
public class OrmConnectionException extends RuntimeException {

	public OrmConnectionException(String message) {
		super(message);
	}

	public OrmConnectionException(String message,
	                              Throwable cause) {
		super(
				message,
				cause
		);
	}

	public OrmConnectionException(Throwable cause) {
		super(cause);
	}
}
