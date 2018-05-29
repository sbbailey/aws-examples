package io.sbailey.blog.aws.examples.microservices.transactions.controllers;

import io.sbailey.aws.examples.microservices.domain.Account;
import io.sbailey.aws.examples.microservices.domain.Transaction;
import io.sbailey.aws.examples.microservices.domain.TxType;
import io.sbailey.blog.aws.examples.microservices.transactions.services.TransactionsService;

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
@RequestMapping("/transactions")
public class TransactionController {
	
	@Autowired
	private TransactionsService transactionService;

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


	
	/**
	 * get the account transactions
	 * 
	 * @param number - account number
	 * @return json string serialized representation of the {@link Account}
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value="/getaccounttransactions/{number}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Transaction> getAccountTransactions(@PathVariable Integer number){
		appLog.info("getAccountTransactions: " + number);
		return transactionService.getAccountTransactions(number);
	}
	
	//TODO: CHNAGE TO A POST!
	/**
	 * add an account transaction
	 * 
	 * @return json string serialized representation of the {@link Account}
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value="/addtransaction/{number}/{type}/{amount}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Transaction> addAccountTransaction(@PathVariable Integer number, @PathVariable String type, @PathVariable BigDecimal amount){		
		appLog.info("addAccountTransaction: " + number + "," + type + "," + amount);
		TxType txType = TxType.getTxType(type);
		return transactionService.addAccountTransaction(number, txType, amount);
	}
	
}
