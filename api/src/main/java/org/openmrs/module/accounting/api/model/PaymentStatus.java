package org.openmrs.module.accounting.api.model;


public enum PaymentStatus {
	COMMITTED("COMMITTED"),
	PAID("PAID"),
	DELETED("DELETED");
	
	private String name;
	
	PaymentStatus(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
