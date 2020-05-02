package com.example.helloservlet;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;

public class HelloWorldServletIT {

	private static final String BASE_URL = "/hello-servlet/say-hello";
	
	@BeforeClass
	public static void setUp() {
		int tomcatPort = Integer.parseInt(System.getProperty("tomcat.http.port","8080"));
		RestAssured.port = tomcatPort;
		Logger.getLogger(HelloWorldServletIT.class.toString())
			.info("Using Url: "+RestAssured.baseURI+":"+RestAssured.port);
	}

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
