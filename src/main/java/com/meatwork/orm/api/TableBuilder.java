package com.meatwork.orm.api;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
public class TableBuilder {

	private final String tableName;
	private final List<String> columns = new ArrayList<>();
	private final List<String> primaryKeys = new ArrayList<>();
	private final List<String> foreignKeys = new ArrayList<>();

	public TableBuilder(String tableName) {
		this.tableName = tableName;
	}

	public TableBuilder addColumn(String name,
	                              String type,
	                              boolean notNull) {
		String columnDef = name + " " + type + (notNull ? " NOT NULL" : "");
		columns.add(columnDef);
		return this;
	}

	public TableBuilder primaryKey(String... keys) {
		this.primaryKeys.addAll(Arrays.asList(keys));
		return this;
	}

	public TableBuilder foreignKey(String column,
	                               String referencedTable,
	                               String referencedColumn) {
		String fkDef = String.format(
				"FOREIGN KEY (%s) REFERENCES %s(%s)",
				column,
				referencedTable,
				referencedColumn
		);
		foreignKeys.add(fkDef);
		return this;
	}

	public String build() {
		StringJoiner joiner = new StringJoiner(
				",\n  ",
				"CREATE TABLE " + tableName + " (\n  ",
				"\n);"
		);

		for (String column : columns) {
			joiner.add(column);
		}

		if (!primaryKeys.isEmpty()) {
			joiner.add("PRIMARY KEY (" + String.join(
					", ",
					primaryKeys
			) + ")");
		}

		for (String fk : foreignKeys) {
			joiner.add(fk);
		}

		return joiner.toString();
	}
}
