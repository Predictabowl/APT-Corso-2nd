package com.example.mocking;

import static org.mockito.AdditionalAnswers.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

import java.util.Arrays;

public class TransactionalEmployeeManagerTest {

	private TransactionalEmployeeManager employeeManager;
	private EmployeeRepository employeeRepository;
	private BankService bankService;
	private TransactionManager transactionManager;

	@Before
	public void setup() {
		employeeRepository = mock(EmployeeRepository.class);
		bankService = mock(BankService.class);
		transactionManager = mock(TransactionManager.class);
		when(transactionManager.doInTransaction(any())).thenAnswer(answer((TransactionCode<?> code) -> code.apply(employeeRepository)));
		employeeManager = new TransactionalEmployeeManager(transactionManager, bankService);
	}

	@Test
	public void test_pay_employees_when_several_employess_are_present() {
		Employee employee = new Employee("1", 1000);
		Employee employee2 = new Employee("2", 1500);
		when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee, employee2));
		employeeManager.payEmployees();
		verify(bankService).pay("1", 1000);
		verify(bankService).pay("2", 1500);
		verify(employeeRepository).save(employee);
		verify(employeeRepository).save(employee2);
		verify(transactionManager,times(3)).doInTransaction(any());
		verifyNoMoreInteractions(bankService);
	}

	// This test use inOrder just for exercise purpose, in reality it should not be
	// needed
	@Test
	public void test_employee_set_paid_is_called_after_paying() {
		Employee employee = spy(new Employee("1", 1000));
		when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));
		employeeManager.payEmployees();
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
		employeeManager.payEmployees();
		verify(employeeError).setPaid(false);
		verify(employee).setPaid(true);
	}

}
