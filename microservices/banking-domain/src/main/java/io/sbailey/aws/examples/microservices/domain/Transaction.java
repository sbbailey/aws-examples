package io.sbailey.aws.examples.microservices.domain;

import java.math.BigDecimal;

public class Transaction {

	private Integer number;
	private TxType txType;
	private BigDecimal amount;
	
	public Transaction(Integer number, TxType txType, BigDecimal amount) {
		super();
		this.number = number;
		this.txType = txType;
		this.amount = amount;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public TxType getTxType() {
		return txType;
	}
	public void setTxType(TxType txType) {
		this.txType = txType;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	
	
}
