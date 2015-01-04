package org.openmrs.module.accounting.api.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="accounting_budget_item")
public class BudgetItem {
	
	@Id
	@GeneratedValue
	@Column(name="budget_item_id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="account_id")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name="budget_id")
	private Budget budget;
	
	@Column(name="description", length=1000)
	private String description;
	
	@Column(name = "amount", precision = 19, scale = 2)
	private BigDecimal amount;
	
	@Type(type="timestamp")
	@Column(name = "start_date")
	private Date startDate;
	
	@Type(type="timestamp")
	@Column(name = "end_date")
	private Date endDate;
	
	@Type(type="timestamp")
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name="created_by")
	private int createdBy;
	
	@Type(type="timestamp")
	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Column(name="updated_by")
	private int updatedBy;
	
	@Column(name="retired")
	private Boolean retired = false;
	
	@Column(name="retired_by")
	private int retiredBy;
	
	@Type(type="timestamp")
	@Column(name="retired_date")
	private Date retiredDate;
	
	@Column(name = "txn_number")
	private String txnNumber;

	
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

	
    public Date getUpdatedDate() {
    	return updatedDate;
    }

	
    public void setUpdatedDate(Date updatedDate) {
    	this.updatedDate = updatedDate;
    }

	
    public int getUpdatedBy() {
    	return updatedBy;
    }

	
    public void setUpdatedBy(int updatedBy) {
    	this.updatedBy = updatedBy;
    }

	
    public Boolean getRetired() {
    	return retired;
    }

	
    public void setRetired(Boolean retired) {
    	this.retired = retired;
    }

	
    public int getRetiredBy() {
    	return retiredBy;
    }

	
    public void setRetiredBy(int retiredBy) {
    	this.retiredBy = retiredBy;
    }

	
    public Date getRetiredDate() {
    	return retiredDate;
    }

	
    public void setRetiredDate(Date retiredDate) {
    	this.retiredDate = retiredDate;
    }


	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((amount == null) ? 0 : amount.hashCode());
	    result = prime * result + createdBy;
	    result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
	    result = prime * result + ((id == null) ? 0 : id.hashCode());
	    return result;
    }


	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    BudgetItem other = (BudgetItem) obj;
	    if (amount == null) {
		    if (other.amount != null)
			    return false;
	    } else if (!amount.equals(other.amount))
		    return false;
	    if (createdBy != other.createdBy)
		    return false;
	    if (createdDate == null) {
		    if (other.createdDate != null)
			    return false;
	    } else if (!createdDate.equals(other.createdDate))
		    return false;
	    if (id == null) {
		    if (other.id != null)
			    return false;
	    } else if (!id.equals(other.id))
		    return false;
	    return true;
    }

    public Budget getBudget() {
    	return budget;
    }


	
    public void setBudget(Budget budget) {
    	this.budget = budget;
    }


	
    public String getDescription() {
    	return description;
    }


	
    public void setDescription(String description) {
    	this.description = description;
    }


	
    public Date getStartDate() {
    	return startDate;
    }


	
    public void setStartDate(Date startDate) {
    	this.startDate = startDate;
    }


	
    public Date getEndDate() {
    	return endDate;
    }


	
    public void setEndDate(Date endDate) {
    	this.endDate = endDate;
    }


	@Override
    public String toString() {
	    return "BudgetItem [id=" + id + ", account=" + account + ", budget=" + budget + ", description=" + description
	            + ", amount=" + amount + ", startDate=" + startDate + ", endDate=" + endDate + ", createdDate="
	            + createdDate + ", createdBy=" + createdBy + ", updatedDate=" + updatedDate + ", updatedBy=" + updatedBy
	            + ", retired=" + retired + ", retiredBy=" + retiredBy + ", retiredDate=" + retiredDate + "]";
    }


	
    public String getTxnNumber() {
    	return txnNumber;
    }


	
    public void setTxnNumber(String txnNumber) {
    	this.txnNumber = txnNumber;
    }
	
	
	
}
