package org.openmrs.module.accounting.api.model;


public enum GeneralStatus {
	ACTIVE("ACTIVE"),INACTIVE("INACTIVE"),DISABLED("DISABLED"),CLOSED("CLOSED");
	
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
