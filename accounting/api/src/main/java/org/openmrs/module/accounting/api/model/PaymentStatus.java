package org.openmrs.module.accounting.api.model;


public enum PaymentStatus {
	OPEN("OPEN"),
	COMMITTED("COMMITTED"),
	PAID("PAID"),
	DISABLED("DISABLED");
	
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
