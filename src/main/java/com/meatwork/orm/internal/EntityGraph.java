package com.meatwork.orm.internal;


import com.meatwork.orm.api.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
public class EntityGraph {

	private final static Logger LOGGER = LoggerFactory.getLogger(EntityGraph.class);

	private static final Map<String, Supplier<Entity>> map = new HashMap<>();

	private EntityGraph() {}

	public static void register(Class<? extends Entity> clazz) {
		var entityName = clazz.getName();
		LOGGER.debug(
				"Registering entity: {}",
				entityName
		);
		map.put(
				entityName, () -> {
			try {
				return clazz.getConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException |
			         NoSuchMethodException e) {
				LOGGER.error("Cannot instantiate entity {}", entityName);
				throw new RuntimeException(e);
			}
		});
	}

	@SuppressWarnings("unchecked")
	public static <T extends Entity> T get(Class<T> clazz) {
		return (T) map.get(clazz.getName()).get();
	}

	@SuppressWarnings("unchecked")
	public static <T extends Entity> Set<T> getAll() {
		return (Set<T>) map.values().stream().map(Supplier::get).collect(Collectors.toSet());
	}

}
