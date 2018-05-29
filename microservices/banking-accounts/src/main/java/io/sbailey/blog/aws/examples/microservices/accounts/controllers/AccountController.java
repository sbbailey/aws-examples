package io.sbailey.blog.aws.examples.microservices.accounts.controllers;

import io.sbailey.aws.examples.microservices.domain.Account;
import io.sbailey.blog.aws.examples.microservices.accounts.services.AccountsService;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/accounts")
public class AccountController {
	
	@Autowired
	private AccountsService accountsService;

	private static final Logger appLog = LoggerFactory.getLogger("applicationLog");
	
	
	/**
	 * ALB health check
	 * 
	 * @return
	 */
	@RequestMapping(value="/apihealth", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Boolean apihealth(){
		return Boolean.TRUE;
	}
	
	
	
	
	@CrossOrigin(origins="*")
	@RequestMapping(value="/listaccounts", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Account> listAccounts(){
		appLog.info("listaccounts");
		return accountsService.listAccounts();
	}
	
	
	/**
	 * get the account using the account name
	 * 
	 * @param name - account name
	 * @return json string serialized representation of the {@link Account}
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value="/getaccountbyname/{name}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Account getAccountByName(@PathVariable String name){
		appLog.info("getAccountByName: " + name);
		return accountsService.getAccountByName(name);
	}
	
	
	/**
	 * get the account using the account number
	 * 
	 * @param name - account number
	 * @return json string serialized representation of the {@link Account}
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value="/getaccountbynumber/{number}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Account getAccountByNunber(@PathVariable Integer number){
		appLog.info("getAccountByNunber: " + number);
		return accountsService.getAccountByNumber(number);
	}
	
	
	// TODO: change to POST
	/**
	 * create an account
	 * 
	 * @param accoutName - name of the account to be opened
	 * @param openingBalance - opening balance
	 * @return json string serialized representation of the {@link Account}
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value="/createaccount/{name}/{opbal}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Account createAccount(@PathVariable String name, @PathVariable BigDecimal opbal){		
		appLog.info("createAccount: " + name + "," + opbal);
		return accountsService.createAccount(name, opbal);
	}

}
