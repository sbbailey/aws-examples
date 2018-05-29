package io.sbailey.blog.aws.examples.microservices.accounts.services;

import io.sbailey.aws.examples.microservices.domain.Account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;


@Service
public class AccountsServiceImpl implements AccountsService {

	
	/*
	 * replaces a repository/datastore for demo purposes 
	 * 
	 */
	private HashMap<Integer, Account> accountStore = new HashMap<Integer, Account>();
	private static Integer accountNumberCount = 100;
	
	
	
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Account getAccountByName(String name){
		Account acc = null;
		for(Account account : accountStore.values()){
			if(name.equals(account.getName())){
				acc = account;
				break;
			}
		}
		return acc;
	}


	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public ArrayList<Account> listAccounts() {
		return new ArrayList<Account>(accountStore.values());
	}


	
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Account getAccountByNumber(Integer number) {
		return accountStore.get(number);
	}


	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Account createAccount(String accountname, BigDecimal openingBalance) {
		accountNumberCount++;
		Account account = new Account(accountname, accountNumberCount, openingBalance);
		accountStore.put(accountNumberCount, account);
		return accountStore.get(accountNumberCount);
	}
	
}
