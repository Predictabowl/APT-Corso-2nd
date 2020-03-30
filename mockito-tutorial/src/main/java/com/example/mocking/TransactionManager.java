package com.example.mocking;

public interface TransactionManager {
	<T> T doInTransaction(TransactionCode<T> code);
}
