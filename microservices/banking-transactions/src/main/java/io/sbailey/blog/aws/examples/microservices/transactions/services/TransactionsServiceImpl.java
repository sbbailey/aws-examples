package io.sbailey.blog.aws.examples.microservices.transactions.services;

import io.sbailey.aws.examples.microservices.domain.Transaction;
import io.sbailey.aws.examples.microservices.domain.TxType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class TransactionsServiceImpl implements TransactionsService {
	
	/*
	 * replaces a repository/datastore for demo purposes 
	 * 
	 */
	private HashMap<Integer, List<Transaction>> transactionStore = new HashMap<Integer, List<Transaction>>();
	
	
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public List<Transaction> getAccountTransactions(Integer number) {
		return transactionStore.get(number);
	}

	
	
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public List<Transaction> addAccountTransaction(Integer accountNumber,TxType txType, BigDecimal amount) {
		Transaction newTx = new Transaction(accountNumber, txType, amount);
		if(transactionStore.containsKey(accountNumber)){
			transactionStore.get(accountNumber).add(newTx);
		}else{
			List<Transaction> txList = new ArrayList<Transaction>();
			txList.add(newTx);
			transactionStore.put(accountNumber, txList);
		}
		
		return transactionStore.get(accountNumber);
	}	

}
