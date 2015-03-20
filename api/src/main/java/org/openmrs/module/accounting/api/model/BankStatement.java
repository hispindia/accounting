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

import org.hibernate.annotations.Type;


@Entity
@Table(name="accounting_bank_statement")
public class BankStatement {
	@Id
	@GeneratedValue
	@Column(name = "bank_statement_id")
	private Integer id;
	
	@Column(name = "description", length = 1000)
	private String description;
	
	
	@Column(name="amount", precision = 19, scale = 2)
	private BigDecimal amount;
	
	@Column(name="check_number", length=100)
	private String checkNumber;
	
	@Column(name = "date_from")
	@Temporal(TemporalType.DATE)
	private Date dateFrom;
	
	@Column(name = "date_to")
	@Temporal(TemporalType.DATE)
	private Date dateTo;
	
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

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="bank_account_id")
	private BankAccount bankAccount;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private BankStatementType type;
	
	@Column(name="receipt_type", length=50)
	private String receiptType;
	
	public BankStatement(){
		this.voided = false;
	}
	
    public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	public Integer getId() {
    	return id;
    }

	
    public void setId(Integer id) {
    	this.id = id;
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

	
    public boolean getVoided() {
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


	
    public String getDescription() {
    	return description;
    }


	
    public void setDescription(String description) {
    	this.description = description;
    }

	
    public Date getDateFrom() {
    	return dateFrom;
    }

	
    public void setDateFrom(Date dateFrom) {
    	this.dateFrom = dateFrom;
    }

	
    public Date getDateTo() {
    	return dateTo;
    }

	
    public void setDateTo(Date dateTo) {
    	this.dateTo = dateTo;
    }

	
    public BankAccount getBankAccount() {
    	return bankAccount;
    }

	
    public void setBankAccount(BankAccount bankAccount) {
    	this.bankAccount = bankAccount;
    }

	
    public BankStatementType getType() {
    	return type;
    }

	
    public void setType(BankStatementType type) {
    	this.type = type;
    }

	
    public String getCheckNumber() {
    	return checkNumber;
    }

	
    public void setCheckNumber(String checkNumber) {
    	this.checkNumber = checkNumber;
    }
	
	
	
}
