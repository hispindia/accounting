package org.openmrs.module.accounting.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="accounting_bank_account")
public class BankAccount {
	@Id
	@GeneratedValue
	@Column(name = "bank_account_id")
	private Integer id;
	
	@Column(name = "account_name", length = 255)
	private String accountName;
	
	@Column(name = "account_number", length = 255)
	private String accountNumber;
	
	@Column(name = "bank_name", length = 255)
	private String bankName;
	
	@Column(name = "bank_branch", length = 255)
	private String bankBranch;
	
	@Column(name = "bank_code", length = 255)
	private String bankCode;
	
	@Column(name = "description", length = 1000)
	private String description;

	@Column(name = "deleted", columnDefinition = "SMALLINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean deleted = false;

	
	@Column(name = "created_by")
	private int createdBy;
	
	@Column(name = "created_date")
	@Type(type = "timestamp")
	private Date createdDate;
	
	@Column(name ="updated_by")
	private int updatedBy;
	
	@Column(name = "updated_date")
	@Type(type="timestamp")
	private Date updatedDate;
	
    public Integer getId() {
    	return id;
    }

	
    public void setId(Integer id) {
    	this.id = id;
    }

	
    public String getAccountName() {
    	return accountName;
    }

	
    public void setAccountName(String accountName) {
    	this.accountName = accountName;
    }

	
    public String getAccountNumber() {
    	return accountNumber;
    }

	
    public void setAccountNumber(String accountNumber) {
    	this.accountNumber = accountNumber;
    }

	
    public String getBankName() {
    	return bankName;
    }

	
    public void setBankName(String bankName) {
    	this.bankName = bankName;
    }

	
    public String getBankBranch() {
    	return bankBranch;
    }

	
    public void setBankBranch(String bankBranch) {
    	this.bankBranch = bankBranch;
    }

	
    public String getBankCode() {
    	return bankCode;
    }

	
    public void setBankCode(String bankCode) {
    	this.bankCode = bankCode;
    }

	
    public String getDescription() {
    	return description;
    }

	
    public void setDescription(String description) {
    	this.description = description;
    }


	
    public boolean getDeleted() {
    	return deleted;
    }


	
    public void setDeleted(boolean deleted) {
    	this.deleted = deleted;
    }


	
    public int getCreatedBy() {
    	return createdBy;
    }


	
    public void setCreatedBy(int createdBy) {
    	this.createdBy = createdBy;
    }


	
    public Date getCreatedDate() {
    	return createdDate;
    }


	
    public void setCreatedDate(Date createdDate) {
    	this.createdDate = createdDate;
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
	
	
}
