package com.meatwork.orm.api;

public record EntityRefProperty(PropertyType typeId, String tableName, String columnNameId) implements ExtraMetraProperty {}
