import com.meatwork.core.api.service.ApplicationStartup;

/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
module com.meatwork.orm {

	requires com.meatwork.core;
	requires org.slf4j;
	requires org.reflections;
	requires java.sql;
	requires jakarta.inject;
	requires meatwork.common;

	exports com.meatwork.orm.api;
	exports com.meatwork.orm.internal to com.meatwork.core, com.meatwork.orm.test;
}
