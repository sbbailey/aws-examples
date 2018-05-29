package io.sbailey.blog.aws.examples.microservices.api.services;

import io.sbailey.aws.examples.microservices.domain.Account;
import io.sbailey.aws.examples.microservices.domain.Transaction;
import io.sbailey.aws.examples.microservices.domain.TxType;
import io.sbailey.blog.aws.examples.microservices.api.ClientAsyncHandler;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


@Service
public class APIWebClientServiceImpl implements APIWebClientService {
	
	
	@Value("${app.load.bal.address}")
	private String balancerAddress;
	
	@Autowired
	private AsyncHttpClient asyncHttpClient;
	
	private static final Logger appLog = LoggerFactory.getLogger("applicationLog");
	
	Type txListType = new TypeToken<List<Transaction>>() {}.getType();
	Type accListType = new TypeToken<List<Account>>() {}.getType();
	
	private Gson gson = new Gson();
	
	
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Account getAccountByName(String name){
		Account account = null;
		String requestUrl = balancerAddress + "accounts/getaccountbyname/" + name;		
		CompletableFuture<Response> accFuture = asyncHttpClient.prepareGet(requestUrl).execute(new ClientAsyncHandler()).toCompletableFuture();
		try {
			Response accResponse = accFuture.get();			
			String respBody = accResponse.getResponseBody();	
			account = gson.fromJson(respBody, Account.class);
			if(account != null){
				// now get the transactions - would the service for this
				requestUrl = balancerAddress + "transactions/getaccounttransactions/" + account.getNumber();
				CompletableFuture<Response> txFuture = asyncHttpClient.prepareGet(requestUrl).execute(new ClientAsyncHandler()).toCompletableFuture();
				Response txResponse = txFuture.get();
				String txRespBody = txResponse.getResponseBody();
				List<Transaction> txs = gson.fromJson(txRespBody, txListType);
				account.getTxs().addAll(txs);
			}
		} catch (InterruptedException | ExecutionException e) {
			appLog.error(e.getLocalizedMessage(), e);
		}
				
		return account;
	}


	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public List<Account> listAccounts() {
		List<Account> accounts = new ArrayList<Account>();
		String requestUrl = balancerAddress + "accounts/listaccounts";
		CompletableFuture<Response> accFuture = asyncHttpClient.prepareGet(requestUrl).execute(new ClientAsyncHandler()).toCompletableFuture();
		try {
			Response accResponse = accFuture.get();			
			String respBody = accResponse.getResponseBody();			
			List<Account> listedAccs = gson.fromJson(respBody, accListType);			
			accounts.addAll(listedAccs);
		} catch (InterruptedException | ExecutionException e) {
			appLog.error(e.getLocalizedMessage(), e);
		}
				
		return accounts;
	}


	
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Account getAccountByNumber(Integer number) {
		Account account = null;
		String requestUrl = balancerAddress + "accounts/getaccountbynumber/" + number;		
		CompletableFuture<Response> accFuture = asyncHttpClient.prepareGet(requestUrl).execute(new ClientAsyncHandler()).toCompletableFuture();
		try {
			Response accResponse = accFuture.get();			
			String respBody = accResponse.getResponseBody();	
			account = gson.fromJson(respBody, Account.class);
			if(account != null){
				// now get the transactions - would the service for this
				requestUrl = balancerAddress + "transactions/getaccounttransactions/" + account.getNumber();
				CompletableFuture<Response> txFuture = asyncHttpClient.prepareGet(requestUrl).execute(new ClientAsyncHandler()).toCompletableFuture();
				Response txResponse = txFuture.get();
				String txRespBody = txResponse.getResponseBody();
				List<Transaction> txs = gson.fromJson(txRespBody, txListType);
				account.getTxs().addAll(txs);
			}
		} catch (InterruptedException | ExecutionException e) {
			appLog.error(e.getLocalizedMessage(), e);
		}
				
		return account;
	}


	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Account createAccount(String accountname, String openingBalance) {
		Account account = null;
		String requestUrl = balancerAddress + "accounts/createaccount/" + accountname + "/" + openingBalance;		
		CompletableFuture<Response> accFuture = asyncHttpClient.prepareGet(requestUrl).execute(new ClientAsyncHandler()).toCompletableFuture();
		try {
			Response accResponse = accFuture.get();			
			String respBody = accResponse.getResponseBody();	
			account = gson.fromJson(respBody, Account.class);
		} catch (InterruptedException | ExecutionException e) {
			appLog.error(e.getLocalizedMessage(), e);
		}
				
		return account;
	}


	
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public List<Transaction> getAccountTransactions(String number) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		String requestUrl = balancerAddress + "transactions/getaccounttransactions/" + number;		
		CompletableFuture<Response> txFuture = asyncHttpClient.prepareGet(requestUrl).execute(new ClientAsyncHandler()).toCompletableFuture();
		try {
			Response txResponse = txFuture.get();
			String txRespBody = txResponse.getResponseBody();
			List<Transaction> txs = gson.fromJson(txRespBody, txListType);
			transactions.addAll(txs);
		} catch (InterruptedException | ExecutionException e) {
			appLog.error(e.getLocalizedMessage(), e);
		}
		
		return transactions;
	}



	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Account addAccountTransaction(Integer accountNumber, TxType txType, BigDecimal amount) {
		Account account = null;
		String requestUrl = balancerAddress + "accounts/getaccountbynumber/" + accountNumber;		
		CompletableFuture<Response> accFuture = asyncHttpClient.prepareGet(requestUrl).execute(new ClientAsyncHandler()).toCompletableFuture();
		try {
			Response accResponse = accFuture.get();			
			String respBody = accResponse.getResponseBody();	
			account = gson.fromJson(respBody, Account.class);
			if(account != null){
				requestUrl = balancerAddress + "transactions/addtransaction/" + accountNumber + "/" + txType.getName() + "/" + amount;		
				CompletableFuture<Response> txFuture = asyncHttpClient.prepareGet(requestUrl).execute(new ClientAsyncHandler()).toCompletableFuture();
				Response txResponse = txFuture.get();
				String txRespBody = txResponse.getResponseBody();
				List<Transaction> txs = gson.fromJson(txRespBody, txListType);
				account.getTxs().addAll(txs);				
			}						
		} catch (InterruptedException | ExecutionException e) {
			appLog.error(e.getLocalizedMessage(), e);
		}
		
		return account;
	}

}
