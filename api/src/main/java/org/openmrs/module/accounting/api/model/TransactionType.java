package org.openmrs.module.accounting.api.model;

public enum TransactionType {
	
	CREDIT("CREDIT"), DEBIT("DEBIT");
	
	private String name;
	
	TransactionType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
