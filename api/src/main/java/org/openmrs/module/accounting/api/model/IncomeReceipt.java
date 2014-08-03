/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.accounting.api.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "accounting_income_receipt")
public class IncomeReceipt {
	
	@Id
	@GeneratedValue
	@Column(name = "income_receipt_id")
	private Integer id;
	
	@Column(name = "receipt_no")
	private String receiptNo;
	
	@Column(name = "description")
	private String description;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "receipt_date")
	private Date receiptDate;
	
	@Column(name = "created_date")
	@Type(type = "timestamp")
	private Date createdDate;
	
	@Column(name = "created_by")
	private int createdBy;
	
	@Column(name = "voided")
	private boolean voided;
	
	@Column(name = "voided_date")
	@Type(type = "timestamp")
	private Date voideddDate;
	
	@Column(name = "voided_by")
	private int voidedBy;
	
	@Column(name ="updated_by")
	private int updatedBy;
	
	@Column(name = "updated_date")
	@Type(type="timestamp")
	private Date updatedDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private GeneralStatus status;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "receipt", targetEntity = IncomeReceiptItem.class)
	private Set<IncomeReceiptItem> receiptItems;
	
	
	public IncomeReceipt(String receiptNo, String description){
		this.voided = false;
		this.receiptNo = receiptNo;
		this.description = description;
	}
	
	public IncomeReceipt(){
		this.voided = false;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	
    public String getReceiptNo() {
    	return receiptNo;
    }

	
    public void setReceiptNo(String receiptNo) {
    	this.receiptNo = receiptNo;
    }

	
    public Date getReceiptDate() {
    	return receiptDate;
    }

	
    public void setReceiptDate(Date receiptDate) {
    	this.receiptDate = receiptDate;
    }

	
    public Date getCreatedDate() {
    	return createdDate;
    }

	
    public void setCreatedDate(Date createdDate) {
    	this.createdDate = createdDate;
    }

	
    public int getCreatedBy() {
    	return createdBy;
    }

	
    public void setCreatedBy(int createdBy) {
    	this.createdBy = createdBy;
    }

	
    public boolean isVoided() {
    	return voided;
    }

	
    public void setVoided(boolean voided) {
    	this.voided = voided;
    }

	
    public Date getVoideddDate() {
    	return voideddDate;
    }

	
    public void setVoideddDate(Date voideddDate) {
    	this.voideddDate = voideddDate;
    }

	
    public int getVoidedBy() {
    	return voidedBy;
    }

	
    public void setVoidedBy(int voidedBy) {
    	this.voidedBy = voidedBy;
    }

	
    public String getDescription() {
    	return description;
    }

	
    public void setDescription(String description) {
    	this.description = description;
    }

	
    public int getUpdatedBy() {
    	return updatedBy;
    }

	
    public void setUpdatedBy(int updatedBy) {
    	this.updatedBy = updatedBy;
    }

	
    public Date getUpdatedDate() {
    	return updatedDate;
    }

	
    public void setUpdatedDate(Date updatedDate) {
    	this.updatedDate = updatedDate;
    }

	@Override
    public String toString() {
	    return "IncomeReceipt [id=" + id + ", receiptNo=" + receiptNo + ", description=" + description + ", receiptDate="
	            + receiptDate + ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", voided=" + voided
	            + ", voideddDate=" + voideddDate + ", voidedBy=" + voidedBy + ", updatedBy=" + updatedBy + ", updatedDate="
	            + updatedDate + "]";
    }

	
    public Set<IncomeReceiptItem> getReceiptItems() {
    	return receiptItems;
    }

	
    public void setReceiptItems(Set<IncomeReceiptItem> receiptItems) {
    	this.receiptItems = receiptItems;
    }

    public void addReceiptItem(IncomeReceiptItem item){
    	if (receiptItems == null) {
    		receiptItems = new HashSet<IncomeReceiptItem>();
    	}
    	receiptItems.add(item);
    }
	
    public GeneralStatus getStatus() {
    	return status;
    }

	
    public void setStatus(GeneralStatus status) {
    	this.status = status;
    }

	
}
