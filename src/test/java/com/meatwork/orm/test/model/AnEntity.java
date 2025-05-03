package com.meatwork.orm.test.model;


import com.meatwork.orm.api.AbstractMeatEntity;
import com.meatwork.orm.api.EntityRef;
import com.meatwork.orm.api.MetaProperty;
import com.meatwork.orm.api.PropertyType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
public class AnEntity extends AbstractMeatEntity {

	@Override
	public MetaProperty[] getMetaProperties() {
		return new MetaProperty[] {
				new MetaProperty("ID", PropertyType.STRING, true, true, false),
				new MetaProperty("NAME", PropertyType.STRING, false, false, true),
				new MetaProperty("TOTO", PropertyType.STRING, false, false, true),
				new MetaProperty("AGE", PropertyType.INTEGER, false, false, true),
				new MetaProperty("NUMBER_ID", PropertyType.LONG, false, false, true),
				new MetaProperty("REF", PropertyType.ENTITY_REF, false, false, true),
				new MetaProperty("DATE", PropertyType.LOCALDATE, false, false, true),
				new MetaProperty("TIME", PropertyType.LOCALTIME, false, false, true),
				new MetaProperty("DATETIME", PropertyType.LOCALDATETIME, false, false, true),
				new MetaProperty("BIGDECIMAL", PropertyType.BIGDECIMAL, false, false, true),
		};
	}

	@Override
	public String getTableName() {
		return "AnEntity";
	}

	public void setId(String id) {
		this.updateProperty("ID", id);
	}

	public void setName(String name) {
		this.updateProperty("NAME", name);
	}

	public void setToto(String toto) {
		this.updateProperty("TOTO", toto);
	}

	public Integer getAge() {
		return this.getProperty("AGE");
	}
	public Long getNumberId() {
		return this.getProperty("NUMBER_ID");
	}
	public EntityRef<AnEntity2> getRef() {
		return this.getProperty("REF");
	}
	public LocalDate getDate() {
		return this.getProperty("DATE");
	}
	public LocalTime getTime() {
		return this.getProperty("TIME");
	}
	public LocalDateTime getDatetime() {
		return this.getProperty("DATETIME");
	}
	public BigDecimal getBigdecimal() {
		return this.getProperty("BIGDECIMAL");
	}

	public void setAge(Integer age) {
		this.updateProperty("AGE", age);
	}
	public void setNumberId(Long number_id) {
		this.updateProperty("NUMBER_ID", number_id);
	}
	public void setRef(EntityRef<AnEntity2> ref) {
		this.updateProperty("REF", ref);
	}
	public void setDate(LocalDate date) {
		this.updateProperty("DATE", date);
	}
	public void setTime(LocalTime time) {
		this.updateProperty("TIME", time);
	}
	public void setDatetime(LocalDateTime datetime) {
		this.updateProperty("DATETIME", datetime);
	}
	public void setBigdecimal(BigDecimal bigdecimal) {
		this.updateProperty("BIGDECIMAL", bigdecimal);
	}

	public String getId() {
		return this.getProperty("ID");
	}

	public String getName() {
		return this.getProperty("NAME");
	}

	public String getToto() {
		return this.getProperty("TOTO");
	}



}
