package com.example.helloservlet.services;

import org.apache.commons.lang3.StringUtils;

public class HelloNameServiceImpl implements HelloNameService{

	@Override
	public String processName(String name) {
		if(StringUtils.isEmpty(name))
			name = "World";
		return name;
	}

}
