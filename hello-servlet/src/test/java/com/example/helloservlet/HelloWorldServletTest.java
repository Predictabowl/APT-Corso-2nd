package com.example.helloservlet;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.MockitoAnnotations.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/*
 * This test class is onty demonstrative.
 * We should not mock types we don't own, like HttpServletResponse
 */

public class HelloWorldServletTest {

	@Mock
	private HttpServletResponse response;

	@Mock
	private HttpServletRequest request;

	private HelloWorldServlet helloWorldServlet;

	@Mock
	private RequestDispatcher requestDispatcher;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		helloWorldServlet = new HelloWorldServlet();
	}

	@Test
	public void doGet() throws Exception {
		StringWriter stringWriter = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
		when(request.getContextPath()).thenReturn("/");
		helloWorldServlet.doGet(request, response);
		assertThat(stringWriter.toString()).isEqualTo("Served at: /");
	}

	@Test
	public void doPost_without_name() throws Exception {
		when(request.getRequestDispatcher("response.jsp")).thenReturn(requestDispatcher);

		helloWorldServlet.doPost(request, response);

		verify(request).setAttribute("user", "World");
		verify(requestDispatcher).forward(request, response);

	}

	@Test
	public void doPost_with_name() throws Exception{
		when(request.getParameter("name")).thenReturn("Mario");
		when(request.getRequestDispatcher("response.jsp")).thenReturn(requestDispatcher);
		
		helloWorldServlet.doPost(request, response);
		
		verify(request).setAttribute("user", "Mario");
		verify(requestDispatcher).forward(request, response);
	}

}
