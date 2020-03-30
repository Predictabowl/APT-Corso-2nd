package com.example.mocking;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

import java.util.Arrays;

public class EmployeeManagerTest {

	private EmployeeManager employeeManager;
	private EmployeeRepository employeeRepository;
	private BankService bankService;

	@Before
	public void setup() {
		employeeRepository = mock(EmployeeRepository.class);
		bankService = mock(BankService.class);
		employeeManager = new EmployeeManager(employeeRepository, bankService);
	}

	@Test
	public void test_pay_employee_when_no_employees_are_present() {
		int numberOfPayments = employeeManager.payEmployees();
		assertThat(numberOfPayments).isEqualTo(0);
	}

	@Test
	public void test_pay_employees_when_several_employess_are_present() {
		when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("1", 1000), new Employee("2", 1500)));
		assertThat(employeeManager.payEmployees()).isEqualTo(2);
		verify(bankService).pay("1", 1000);
		verify(bankService).pay("2", 1500);
		verifyNoMoreInteractions(bankService);
	}

	// This test use inOrder just for exercise purpose, in reality it should not be
	// needed
	@Test
	public void test_employee_set_paid_is_called_after_paying() {
		Employee employee = spy(new Employee("1", 1000));
		when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));
		assertThat(employeeManager.payEmployees()).isEqualTo(1);
		InOrder inOrder = inOrder(bankService, employee);
		inOrder.verify(bankService).pay("1", 1000);
		inOrder.verify(employee).setPaid(true);
	}

	@Test
	public void test_pay_employees_when_BankService_throws_exception() {
		Employee employee = spy(new Employee("1", 1000));
		Employee employeeError = spy(new Employee("2", 1500));
		when(employeeRepository.findAll()).thenReturn(Arrays.asList(employeeError, employee));
		doThrow(new RuntimeException()).doNothing().when(bankService).pay(anyString(), anyDouble());
		assertThat(employeeManager.payEmployees()).isEqualTo(1);
		verify(employeeError).setPaid(false);
		verify(employee).setPaid(true);
	}

}
