package com.meatwork.orm.test.model;


import com.meatwork.core.api.di.Service;
import com.meatwork.orm.api.DriverType;
import com.meatwork.orm.api.OrmConfiguration;

/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
@Service
public class OrmConfigImpl implements OrmConfiguration {
	@Override
	public String getUrl() {
		return "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
	}

	@Override
	public DriverType getDriverName() {
		return DriverType.H2;
	}

	@Override
	public String getUsername() {
		return "admin";
	}

	@Override
	public String getPassword() {
		return "admin";
	}
}
