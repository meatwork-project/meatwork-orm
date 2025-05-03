package com.meatwork.orm.api;


import com.meatwork.core.api.di.IService;

/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
@IService
public interface OrmConfiguration {
	String getUrl();
	DriverType getDriverName();
	String getUsername();
	String getPassword();
}
