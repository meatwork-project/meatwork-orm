package com.meatwork.orm.internal;


import com.meatwork.orm.api.Entity;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;

/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
public final class EntityManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(EntityManager.class);

	public static void init() {
		List<String> list = ModuleLayer
				.boot()
				.modules()
				.stream()
				.flatMap(it -> it
						.getPackages()
						.stream())
				.toList();
		Reflections reflections = new Reflections(list);
		Set<Class<? extends Entity>> subTypesOf = reflections.getSubTypesOf(Entity.class);
		LOGGER.debug("Entity {} found", subTypesOf);
		for (Class<? extends Entity> aClass : subTypesOf) {
			if(isNotAbstractClass(aClass)) {
				EntityGraph.register(aClass);
			}
		}
	}

	public static <T extends Entity> T get(Class<T> cls) {
		return EntityGraph.get(cls);
	}

	public static <T extends Entity> Set<T> getAll() {
		return EntityGraph.getAll();
	}

	private static boolean isNotAbstractClass(Class<?> cls) {
		return !Modifier.isAbstract(cls.getModifiers());
	}
}
