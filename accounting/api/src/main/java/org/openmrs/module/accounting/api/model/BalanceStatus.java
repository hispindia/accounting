package org.openmrs.module.accounting.api.model;

public enum BalanceStatus {
	ACTIVE("ACTIVE"), DISABLED("DISABLED"), CLOSED("CLOSED");
	
	private String name;
	
	BalanceStatus(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
