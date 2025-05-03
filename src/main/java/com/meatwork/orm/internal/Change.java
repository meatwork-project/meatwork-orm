package com.meatwork.orm.internal;


/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
public record Change(String fieldName, Object newValue, Class<?> toType) {
}
