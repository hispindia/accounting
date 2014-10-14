package org.openmrs.module.accounting.api.model;


public enum GeneralStatus {
	ACTIVE("ACTIVE"),
	INACTIVE("INACTIVE"),
	DELETED("DELETED"),
	CLOSED("CLOSED"),
	OPEN("OPEN");
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
