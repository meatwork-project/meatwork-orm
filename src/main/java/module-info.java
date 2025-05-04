/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
module com.meatwork.orm {

	requires com.meatwork.core;
	requires meatwork.common;
	requires org.reflections;
	requires transitive java.sql;

	exports com.meatwork.orm.api;
	exports com.meatwork.orm.internal to com.meatwork.core, com.meatwork.orm.test;
}
