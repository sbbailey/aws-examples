package io.sbailey.aws.examples.microservices.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
	
	
	private String name;
	private Integer number;
	private BigDecimal balance;
	private List<Transaction> txs = new ArrayList<Transaction>();

	public Account(String name, Integer number, BigDecimal balance) {
		super();
		this.name = name;
		this.number = number;
		this.balance = balance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public List<Transaction> getTxs() {
		return txs;
	}
	public void setTxs(List<Transaction> txs) {
		this.txs = txs;
	}
	
	
}
