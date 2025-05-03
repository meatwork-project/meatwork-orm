package com.meatwork.orm.api;


import java.util.ArrayList;
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
		for (String key : keys) {
			primaryKeys.add(key);
		}
		return this;
	}

	public TableBuilder foreignKey(String column,
	                               String referencedTable,
	                               String referencedColumn,
	                               String onDelete) {
		String fkDef = String.format(
				"FOREIGN KEY (%s) REFERENCES %s(%s) ON DELETE %s",
				column,
				referencedTable,
				referencedColumn,
				onDelete
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
