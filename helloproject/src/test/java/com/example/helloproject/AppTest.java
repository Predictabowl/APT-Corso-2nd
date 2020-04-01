package com.example.helloproject;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AppTest {

	private App app;

	@Before
	public void setup() {
		app = new App();
	}

	@Test
	public void test_say_hello() {
		assertEquals("Hello", app.sayHello());
	}
	
	@Test
	public void test_say_hello_with_name() {
		assertEquals("Hello Mario", app.sayHello("Mario"));
	}

}
