/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
module com.meatwork.orm.test {
	requires com.meatwork.orm;
	requires com.meatwork.test;
	requires com.meatwork.core;
	opens com.meatwork.orm.test;
	exports com.meatwork.orm.test;
	exports com.meatwork.orm.test.model;
}
