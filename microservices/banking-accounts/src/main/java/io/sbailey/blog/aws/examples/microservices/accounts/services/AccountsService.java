package io.sbailey.blog.aws.examples.microservices.accounts.services;

import io.sbailey.aws.examples.microservices.domain.Account;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface AccountsService {
	
	
	
	public Account getAccountByName(String name);


	
	public ArrayList<Account> listAccounts();


	
	public Account getAccountByNumber(Integer number);


	
	public Account createAccount(String accountname, BigDecimal openingBalance);

}
