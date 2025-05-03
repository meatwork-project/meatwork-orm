package com.meatwork.orm.test.model;


import com.meatwork.core.api.di.Service;
import com.meatwork.orm.api.MeatRepository;
import com.meatwork.orm.internal.ConnectionManager;
import jakarta.inject.Inject;

/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
@Service
public class AnEntity2Repository extends MeatRepository<AnEntity2, String> {

	@Inject
	public AnEntity2Repository(ConnectionManager connectionManager) {
		super(
				AnEntity2.class,
				connectionManager
		);
	}
}
