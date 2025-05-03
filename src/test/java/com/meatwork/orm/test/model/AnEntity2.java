package com.meatwork.orm.test.model;


import com.meatwork.orm.api.AbstractMeatEntity;
import com.meatwork.orm.api.MetaProperty;
import com.meatwork.orm.api.PropertyType;

/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
public class AnEntity2 extends AbstractMeatEntity {
	@Override
	public MetaProperty[] getMetaProperties() {
		return new MetaProperty[] {
				new MetaProperty("ID", PropertyType.STRING, true, true, false),
				new MetaProperty("NAME", PropertyType.STRING, false, false, true)
		};
	}

	@Override
	public String getTableName() {
		return "AnEntity2";
	}

	public String getId() {
		return this.getProperty("ID");
	}
	public String getName() {
		return this.getProperty("NAME");
	}

	public void setId(String id) {
		this.updateProperty("ID", id);
	}
	public void setName(String name) {
		this.updateProperty("NAME", name);
	}
}
