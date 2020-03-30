package com.example.mocking;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmployeeManager {

	private EmployeeRepository employeeRepository;
	private BankService bankService;
	private static final Logger LOGGER = LogManager.getLogger(EmployeeManager.class);


	public EmployeeManager(EmployeeRepository employeeRepository, BankService bankService) {
		this.employeeRepository = employeeRepository;
		this.bankService = bankService;
	}

	public int payEmployees() {
		List<Employee> allEmployees = employeeRepository.findAll();
		int paidEmployees = 0;
		for (Employee employee: allEmployees ) {
			try {
				bankService.pay(employee.getId(), employee.getSalary());
				employee.setPaid(true);
				paidEmployees++;
			} catch (RuntimeException e) {
				employee.setPaid(false);
				LOGGER.error("Failed payment of "+employee,e);
			}
		}
		return paidEmployees;
	}

}
