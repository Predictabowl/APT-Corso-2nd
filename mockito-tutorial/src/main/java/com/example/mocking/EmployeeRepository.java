package com.example.mocking;

import java.util.List;

public interface EmployeeRepository {
	List<Employee> findAll();
	Employee save(Employee e);
}
