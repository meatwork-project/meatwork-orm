/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
module com.meatwork.orm.test {
	requires com.meatwork.orm;
	requires org.junit.jupiter.api;
	requires org.mockito.junit.jupiter;
	requires org.mockito;
	requires com.meatwork.test;
	requires com.meatwork.core;
	requires jakarta.inject;
	opens com.meatwork.orm.test;
	exports com.meatwork.orm.test to com.meatwork.core;
	exports com.meatwork.orm.test.model to com.meatwork.core, com.meatwork.orm;
}
