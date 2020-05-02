package com.example.helloworldservlet;


import java.util.logging.Logger;
import static org.assertj.core.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.example.helloservlet.HelloWorldServlet;

import io.github.bonigarcia.wdm.WebDriverManager;

public class HelloWorldServletE2E {

	private static final String BASE_URL = "http://localhost:"
			+ Integer.parseInt(System.getProperty("tomcat.http.port", "8080"))+"/hello-servlet";
	
	private FirefoxDriver driver;

	@BeforeClass
	public static void setUpClass() {
		Logger.getLogger(HelloWorldServlet.class.toString()).info("Using URL: "+BASE_URL);
		WebDriverManager.firefoxdriver().setup();
	}
	
	@Before
	public void setUp() {
		driver = new FirefoxDriver();
	}
	
	@After
	public void tearDown() {
		if (driver!= null) {
			driver.quit();
		}
	}
	
	@Test
	public void say_hello_link() throws Exception{
		driver.get(BASE_URL);
		
		driver.findElement(By.linkText("Hello")).click();
		
		assertThat(driver.findElement(By.tagName("body")).getText()).isEqualTo("Served at: /hello-servlet");
	}
	
	@Test
	public void say_hello_button() throws Exception{
		driver.get(BASE_URL);
		
		driver.findElement(By.id("say-hello-text-input")).sendKeys("Mario");
		driver.findElement(By.id("say-hello-button")).click();
		
		assertThat(driver.getTitle()).isEqualTo("Hello Page");
		assertThat(driver.findElement(By.tagName("h2")).getText()).isEqualTo("Hello, Mario!");
	}
}
