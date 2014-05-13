package org.openmrs.module.accounting.api.model;

public enum AccountType {
	B("INCOME & EXPENSE"),I("INCOME"),E("EXPENSE");
	
	private String name;
	
	AccountType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
