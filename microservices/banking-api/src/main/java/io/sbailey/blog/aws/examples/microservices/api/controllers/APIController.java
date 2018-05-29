package io.sbailey.blog.aws.examples.microservices.api.controllers;

import io.sbailey.aws.examples.microservices.domain.Account;
import io.sbailey.aws.examples.microservices.domain.Transaction;
import io.sbailey.aws.examples.microservices.domain.TxType;
import io.sbailey.blog.aws.examples.microservices.api.services.APIWebClientService;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/v1/api/banking")
public class APIController {

	@Autowired
	private APIWebClientService apiWebClientService;
	
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
	 * list all the accounts held
	 * 
	 * @return json string serialized representation of {@link List} of {@link Account}s
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value="/listaccounts", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Account> listAccounts(){
		appLog.info("listaccounts");
		return apiWebClientService.listAccounts();
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
		return apiWebClientService.getAccountByName(name);
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
		return apiWebClientService.getAccountByNumber(number);
	}
	
	
	
	/**
	 * create an account
	 * 
	 * @param accoutName - name of the account to be opened
	 * @param openingBalance - opening balance
	 * @return json string serialized representation of the {@link Account}
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value="/createaccount", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Account createAccount(HttpServletRequest request){		
		String accountname = request.getParameter("name");
		String openingBalance = request.getParameter("opbal");
		appLog.info("createAccount: " + accountname + "," + openingBalance);
		return apiWebClientService.createAccount(accountname, openingBalance);
	}
	
	
	
	/**
	 * get the account transactions
	 * 
	 * @param number - account number
	 * @return json string serialized representation of the {@link Account}
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value="/getaccounttransactions/{number}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Transaction> getAccountTransactions(@PathVariable String number){
		appLog.info("getAccountTransactions: " + number);
		return apiWebClientService.getAccountTransactions(number);
	}
	
	
	/**
	 * add an account transaction
	 * 
	 * @return json string serialized representation of the {@link Account}
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value="/addaccounttransaction", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public Account addAccountTransaction(HttpServletRequest request){
		Integer accountNo = Integer.parseInt(request.getParameter("number"));
		TxType type = TxType.getTxType(request.getParameter("type"));
		BigDecimal amount = new BigDecimal(request.getParameter("amount"));
		appLog.info("addAccountTransaction: " + accountNo + "," + type + "," + amount);
		return apiWebClientService.addAccountTransaction(accountNo, type, amount);
	}
	
}
