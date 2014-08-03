package org.openmrs.module.accounting.api.model;

public enum IncomeReceiptType {
	CASH("CASH"), VOIDED("VOIDED"), WAIVE("WAVIE");
	
	private String name;
	
	IncomeReceiptType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
