package com.meatwork.orm.internal;


import com.meatwork.common.Preconditions;
import com.meatwork.core.api.di.Service;
import com.meatwork.orm.api.OrmConfiguration;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
@Service
public class ConnectionManagerImpl implements ConnectionManager {

	private final OrmConfiguration ormConfiguration;
	private Connection connection;

	@Inject
	public ConnectionManagerImpl(OrmConfiguration ormConfiguration) {
		this.ormConfiguration = ormConfiguration;
	}

	@Override
	public Connection getConnection() {
		checkOrmConfigurationNotNull();
		try {
			Class.forName(ormConfiguration.getDriverName().getValue());
		} catch (ClassNotFoundException e) {
			throw new OrmConnectionException("Error driver " + ormConfiguration.getDriverName(), e);
		}

		try {
			connection = DriverManager.getConnection(
					ormConfiguration.getUrl(),
					ormConfiguration.getUsername(),
					ormConfiguration.getPassword()
			);
			return connection;
		} catch (SQLException e) {
			throw new OrmConnectionException(e);
		}
	}

	private void checkOrmConfigurationNotNull() {
		if (ormConfiguration == null) {
			throw new OrmConnectionException("Orm configuration not implemented - create class extended to com.meatwork.orm.api.OrmConfiguration");
		}
		Preconditions.checkNotNull(ormConfiguration.getDriverName(), "Driver cannot be null");
		Preconditions.checkNotNull(ormConfiguration.getUrl(), "Url cannot be null");
		Preconditions.checkNotNull(ormConfiguration.getUsername(), "Username cannot be null");
		Preconditions.checkNotNull(ormConfiguration.getPassword(), "Password cannot be null");
	}

	@Override
	public void close() throws Exception {
		connection.close();
	}
}
