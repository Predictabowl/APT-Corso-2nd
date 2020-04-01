package com.example.helloproject;

import static org.assertj.core.api.Assertions.*;
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
		assertThat(app.sayHello()).isEqualTo("Hello");
	}
	
	@Test
	public void test_say_hello_with_name() {
		assertThat(app.sayHello("Mario")).isEqualTo("Hello Mario");
	}

}
