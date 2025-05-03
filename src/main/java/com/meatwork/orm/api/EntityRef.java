package com.meatwork.orm.api;


/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
public record EntityRef<T extends Entity>(T entity, Object id) {
}
