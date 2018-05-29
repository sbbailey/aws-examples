package io.sbailey.blog.aws.examples.microservices.transactions.services;

import io.sbailey.aws.examples.microservices.domain.Transaction;
import io.sbailey.aws.examples.microservices.domain.TxType;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionsService {
	
	
	
	/**
	 * get the account transactions
	 * 
	 * @param number - account number
	 * @return {@link List} of {@link Transaction}s
	 */
	public List<Transaction> getAccountTransactions(Integer number);
	
	
	/**
	 * add an account transaction
	 * 
	 * @return {@link List} of {@link Transaction}s
	 */
	public List<Transaction> addAccountTransaction(Integer accountNumber, TxType txType, BigDecimal amount);
	
}
