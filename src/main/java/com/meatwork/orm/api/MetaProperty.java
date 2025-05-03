package com.meatwork.orm.api;


/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
public record MetaProperty(String fieldName, PropertyType type, boolean primaryKey, boolean unique, boolean nullable)  {
}
