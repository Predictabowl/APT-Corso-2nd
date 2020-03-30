package com.example.mocking;
/*
 * Upgraded version which use the TransactionManager 
 */

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransactionalEmployeeManager {

	private BankService bankService;
	private static final Logger LOGGER = LogManager.getLogger(TransactionalEmployeeManager.class);
	private TransactionManager transactionManager;


	public TransactionalEmployeeManager(TransactionManager transactionManager, BankService bankService) {
		this.transactionManager = transactionManager;
		this.bankService = bankService;
	}

	public void payEmployees() {
		List<Employee> allEmployees =  transactionManager.doInTransaction(EmployeeRepository::findAll);
		for (Employee employee: allEmployees ) {
			try {
				bankService.pay(employee.getId(), employee.getSalary());
				employee.setPaid(true);
				transactionManager.doInTransaction(employeeRepository -> employeeRepository.save(employee));
			} catch (RuntimeException e) {
				employee.setPaid(false);
				LOGGER.error("Failed payment of "+employee,e);
			}
		}
	}

}
