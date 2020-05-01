package com.example.helloservlet;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class HelloWorldServletIT {

	private static final String BASE_URL = "/hello-servlet/HelloWorldServlet";

	@Test
	public void testGet() {
		when()
			.get(BASE_URL)
		.then()
			.statusCode(200) //200 stands for "ok"
			.body(containsString("Served at: /hello-servlet"));
	}
	
	@Test
	public void testPost() {
		given()
			.formParam("name", "TEST")
		.when()
			.post(BASE_URL)
		.then().statusCode(200)
			.body(containsString("Hello,"))
			.body(containsString("TEST!"));
	}
}
