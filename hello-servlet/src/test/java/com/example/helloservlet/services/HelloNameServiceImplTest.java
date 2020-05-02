package com.example.helloservlet.services;

import static org.assertj.core.api.Assertions.*;
import org.junit.Test;

public class HelloNameServiceImplTest {
	
	@Test
	public void processName_when_name_is_empty_test() {
		assertThat(new HelloNameServiceImpl().processName(null)).isEqualTo("World");
	}
	
	@Test
	public void processName_when_name_is_not_empty_test() {
		assertThat(new HelloNameServiceImpl().processName("Mario")).isEqualTo("Mario");
	}

}
