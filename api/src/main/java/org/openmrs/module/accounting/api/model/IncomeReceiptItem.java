package org.openmrs.module.accounting.api.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;


@Entity
@Table(name="accounting_income_receipt_item")
public class IncomeReceiptItem {
	
	@Id
	@GeneratedValue
	@Column(name = "income_receipt_item_id")
	private Integer id;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="account_id")
	private Account account;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="income_receipt_id")
	private IncomeReceipt receipt;
	
	@Column(name="amount", precision = 19, scale = 2)
	private BigDecimal amount;
	
	@Column(name = "cheque_number")
	private String chequeNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private IncomeReceiptType type; 
	
	@Temporal(TemporalType.DATE)
	@Column(name = "transaction_date")
	private Date transactionDate;
	
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
	
	@Column(name = "txn_number")
	private String txnNumber;
	
	/**
	 * This attribute is only for building the json object from view
	 */
	@Transient
	private String accountName;
	
    public Integer getId() {
    	return id;
    }

	
    public void setId(Integer id) {
    	this.id = id;
    }

	
    public Account getAccount() {
    	return account;
    }

	
    public void setAccount(Account account) {
    	this.account = account;
    }

	
    public IncomeReceipt getReceipt() {
    	return receipt;
    }

	
    public void setReceipt(IncomeReceipt receipt) {
    	this.receipt = receipt;
    }

	
    public BigDecimal getAmount() {
    	return amount;
    }

	
    public void setAmount(BigDecimal amount) {
    	this.amount = amount;
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


	
    public Date getTransactionDate() {
    	return transactionDate;
    }


	
    public void setTransactionDate(Date transactionDate) {
    	this.transactionDate = transactionDate;
    }


	
    public String getDescription() {
    	return description;
    }


	
    public void setDescription(String description) {
    	this.description = description;
    }


	
    public String getChequeNumber() {
    	return chequeNumber;
    }


	
    public void setChequeNumber(String chequeNumber) {
    	this.chequeNumber = chequeNumber;
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
	    return "IncomeReceiptItem [id=" + id + ", description=" + description + ", account=" + account + ", receipt="
	            + receipt + ", amount=" + amount + ", chequeNumber=" + chequeNumber + ", type=" + type
	            + ", transactionDate=" + transactionDate + ", createdDate=" + createdDate + ", createdBy=" + createdBy
	            + ", voided=" + voided + ", voideddDate=" + voideddDate + ", voidedBy=" + voidedBy + ", updatedBy="
	            + updatedBy + ", updatedDate=" + updatedDate + "]";
    }


	
    public String getAccountName() {
    	return accountName;
    }


	
    public void setAccountName(String accountName) {
    	this.accountName = accountName;
    }


	
    public IncomeReceiptType getType() {
    	return type;
    }


	
    public void setType(IncomeReceiptType type) {
    	this.type = type;
    }


	
    public String getTxnNumber() {
    	return txnNumber;
    }


	
    public void setTxnNumber(String txnNumber) {
    	this.txnNumber = txnNumber;
    }
	
}
