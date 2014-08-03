package org.openmrs.module.accounting.api.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;


@Entity
@Table(name="accounting_expense_balance")
public class ExpenseBalance {
	
	@Id
	@GeneratedValue
	@Column(name = "expense_balance_id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "fiscal_period_id")
	private FiscalPeriod period;
	
	@Column(name = "start_date")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Column(name = "end_date")
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@Column(name = "new_aie", precision = 19, scale = 2)
	private BigDecimal newAIE;
	
	@Column(name = "cummulative_aie", precision = 19, scale = 2)
	private BigDecimal cummulativeAIE;
	
	@Column(name = "current_payment", precision = 19, scale = 2)
	private BigDecimal currentPayment;
	
	@Column(name = "cummulative_payment", precision = 19, scale = 2)
	private  BigDecimal cummulativePayment;
	
	@Column(name = "available_balance", precision = 19, scale = 2)
	private BigDecimal availableBalance;
	
	@Column(name = "total_committed", precision = 19, scale = 2)
	private BigDecimal totalCommitted;
	
	@Column(name = "ledger_balance", precision = 19, scale = 2)
	private BigDecimal ledgerBalance;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private BalanceStatus status;
	
	@Column(name = "created_Date")
	@Type(type="timestamp")
	private Date createdDate;
	
	@Column(name = "created_by")
	private int createdBy;
	
	@Type(type="timestamp")
	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Column(name = "updated_by")
	private int updatedBy;
	

	public ExpenseBalance() {
		this.availableBalance = new BigDecimal("0");
		this.cummulativePayment = new BigDecimal("0");
		this.currentPayment = new BigDecimal("0");
		this.cummulativeAIE = new BigDecimal("0");
		this.newAIE = new BigDecimal("0");
		this.totalCommitted = new BigDecimal("0");
		this.ledgerBalance = new BigDecimal("0");
	}
	
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

	
    public BigDecimal getNewAIE() {
    	return newAIE;
    }

	
    public void setNewAIE(BigDecimal newAIE) {
    	this.newAIE = newAIE;
    }

	
    public BigDecimal getCummulativeAIE() {
    	return cummulativeAIE;
    }

	
    public void setCummulativeAIE(BigDecimal cummulativeAIE) {
    	this.cummulativeAIE = cummulativeAIE;
    }

	
    public BigDecimal getCurrentPayment() {
    	return currentPayment;
    }

	
    public void setCurrentPayment(BigDecimal currentPayment) {
    	this.currentPayment = currentPayment;
    }

	
    public BigDecimal getCummulativePayment() {
    	return cummulativePayment;
    }

	
    public void setCummulativePayment(BigDecimal cummulativePayment) {
    	this.cummulativePayment = cummulativePayment;
    }

	
    public BigDecimal getAvailableBalance() {
    	return availableBalance;
    }

	
    public void setAvailableBalance(BigDecimal availableBalance) {
    	this.availableBalance = availableBalance;
    }


	
    public Date getCreatedDate() {
    	return createdDate;
    }

	
    public void setCreatedDate(Date createdDate) {
    	this.createdDate = createdDate;
    }

	

	
    public Date getUpdatedDate() {
    	return updatedDate;
    }

	
    public void setUpdatedDate(Date updatedDate) {
    	this.updatedDate = updatedDate;
    }



	
    public FiscalPeriod getPeriod() {
    	return period;
    }


	
    public void setPeriod(FiscalPeriod period) {
    	this.period = period;
    }


	
    public int getUpdatedBy() {
    	return updatedBy;
    }


	
    public void setUpdatedBy(int updatedBy) {
    	this.updatedBy = updatedBy;
    }

	
    public int getCreatedBy() {
    	return createdBy;
    }

	
    public void setCreatedBy(int createdBy) {
    	this.createdBy = createdBy;
    }

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
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
	    ExpenseBalance other = (ExpenseBalance) obj;
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

	@Override
    public String toString() {
	    return "ExpenseBalance [id=" + id + ", period=" + period + ", startDate=" + startDate + ", endDate=" + endDate
	            + ", newAIE=" + newAIE + ", cummulativeAIE=" + cummulativeAIE + ", currentPayment=" + currentPayment
	            + ", cummulativePayment=" + cummulativePayment + ", availableBalance=" + availableBalance + ", status="
	            + status + ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", updatedDate=" + updatedDate
	            + ", updatedBy=" + updatedBy + "]";
    }

	
    public BalanceStatus getStatus() {
    	return status;
    }

	
    public void setStatus(BalanceStatus status) {
    	this.status = status;
    }

	
    public BigDecimal getTotalCommitted() {
    	return totalCommitted;
    }

	
    public void setTotalCommitted(BigDecimal totalCommitted) {
    	this.totalCommitted = totalCommitted;
    }

	
    public BigDecimal getLedgerBalance() {
    	return ledgerBalance;
    }

	
    public void setLedgerBalance(BigDecimal ledgerBalance) {
    	this.ledgerBalance = ledgerBalance;
    }
	
	
	
}
