package org.openmrs.module.accounting.api.model;


public enum GeneralStatus {
	OPEN("OPEN"),DISABLED("DISABLED"),CLOSED("CLOSED"),ACTIVE("ACTIVE");
	
	private String name;
	
	GeneralStatus(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
