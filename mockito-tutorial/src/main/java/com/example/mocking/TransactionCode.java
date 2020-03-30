package com.example.mocking;

import java.util.function.Function;

@FunctionalInterface
public interface TransactionCode<S> extends Function<EmployeeRepository, S>{

}
