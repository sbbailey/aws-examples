package io.sbailey.aws.examples.microservices.domain;

public enum TxType {
	
	CREDIT(1, "CREDIT"),
	DEBIT(2, "DEBIT");
	
	/* Type Id */
    private Integer id;
    /* Type Name*/
    private String name;
	
    TxType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	/**
     * Get the {@link TxType} {@link Enum} based on name
     * 
     * @param txTypeName
     * @return {@link TxType}
     */
	public static TxType getTxType(String txTypeName){
		TxType txType = null;
		
		if(txTypeName != null){
			if(txTypeName.equals(TxType.CREDIT.getName())){
				txType = TxType.CREDIT;
			}
			if(txTypeName.equals(TxType.DEBIT.getName())){
				txType = TxType.DEBIT;
			}	
		}
		
		return txType;
	}

}
