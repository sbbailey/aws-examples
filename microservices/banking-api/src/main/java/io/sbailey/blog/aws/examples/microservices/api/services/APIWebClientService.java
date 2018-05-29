package io.sbailey.blog.aws.examples.microservices.api.services;

import io.sbailey.aws.examples.microservices.domain.Account;
import io.sbailey.aws.examples.microservices.domain.Transaction;
import io.sbailey.aws.examples.microservices.domain.TxType;

import java.math.BigDecimal;
import java.util.List;

public interface APIWebClientService {

	
	/**
	 * list all accounts
	 * 
	 * @return {@link List} of {@link Account}s
	 */
	public List<Account> listAccounts();
	
	
	/**
	 * get the account using the account name
	 * 
	 * @param name - account name
	 * @return {@link Account}
	 */
	public Account getAccountByName(String name);
	
	
	/**
	 * get the account using the account number
	 * 
	 * @param name - account number
	 * @return {@link Account}
	 */
	public Account getAccountByNumber(Integer number);
	
	
	
	/**
	 * create an account
	 * 
	 * @param accoutName - name of the account to be opened
	 * @param openingBalance - opening balance
	 * @return {@link Account}
	 */
	public Account createAccount(String accountname, String openingBalance);
	
	
	
	/**
	 * get the account transactions
	 * 
	 * @param number - account number
	 * @return {@link List} of {@link Transaction}s
	 */
	public List<Transaction> getAccountTransactions(String number);
	
	
	/**
	 * add an account transaction
	 * 
	 * @return {@link Account}
	 */
	public Account addAccountTransaction(Integer accountNumber, TxType txType, BigDecimal amount);
	
}
