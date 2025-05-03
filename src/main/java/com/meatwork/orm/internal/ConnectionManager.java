package com.meatwork.orm.internal;


import com.meatwork.core.api.di.IService;

import java.sql.Connection;

/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
@IService
public interface ConnectionManager extends AutoCloseable {
	Connection getConnection();
}
