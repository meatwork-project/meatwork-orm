package com.meatwork.orm.internal;


import com.meatwork.core.api.di.Service;
import com.meatwork.core.api.service.ApplicationStartup;
import com.meatwork.orm.api.OnCreateTable;
import com.meatwork.orm.api.OrmConfiguration;
import jakarta.inject.Inject;

import java.sql.SQLException;

/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
@Service
public class OrmOnStartup implements ApplicationStartup {

	private final OnCreateTable onCreateTable;
	private final QueryManager queryManager;

	@Inject
	public OrmOnStartup(OnCreateTable onCreateTable,
	                    ConnectionManager connectionManager) {
		this.onCreateTable = onCreateTable;
		this.queryManager = new QueryManager(connectionManager);
	}

	@Override
	public int priority() {
		return 800;
	}

	@Override
	public void run(String[] args) throws SQLException {
		EntityManager.init();
		queryManager.executeUpdate(onCreateTable.onCreate(EntityManager.getAll()));
	}
}
