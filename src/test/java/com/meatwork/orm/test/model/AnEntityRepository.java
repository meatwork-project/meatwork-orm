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
public class AnEntityRepository extends MeatRepository<AnEntity, String> {

	@Inject
	public AnEntityRepository(ConnectionManager connectionManager) {
		super(
				AnEntity.class,
				connectionManager
		);
	}
}
