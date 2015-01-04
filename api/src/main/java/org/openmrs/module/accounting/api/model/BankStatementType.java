package org.openmrs.module.accounting.api.model;


public enum BankStatementType {
	CASH("CASH"), EFT("EFT");
	
	private String name;
	
	BankStatementType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
