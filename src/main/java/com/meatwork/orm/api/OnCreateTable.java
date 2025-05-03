package com.meatwork.orm.api;


import com.meatwork.core.api.di.IService;

import java.util.Set;

/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
@IService
public interface OnCreateTable {
	String onCreate(Set<Entity> entitySet) throws OrmQueryException;
}
