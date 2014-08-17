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

/**
 * @author viet
 */

@Entity 	   
@Table(name = "accounting_income_balance")
public class IncomeBalance {
	
	@Id
	@GeneratedValue
	@Column(name = "income_balance_id")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private Account account;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fiscal_period_id")
	private FiscalPeriod period;
	
	@Column(name = "start_date")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Column(name = "end_date")
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@Column(name = "opening_balance", precision = 19, scale = 2)
	private BigDecimal openingBalance;
	
	@Column(name = "closing_balance", precision = 19, scale = 2)
	private BigDecimal closingBalance;
	
	@Column(name = "ledger_balance", precision = 19, scale = 2)
	private BigDecimal ledgerBalance;
	
	@Column(name = "available_balance", precision = 19, scale = 2)
	private BigDecimal availableBalance;
	
	@Type(type="timestamp")
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "created_by")
	private int createdBy;
	
	@Type(type="timestamp")
	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Column(name = "updated_by")
	private int updatedBy;
	
	@Enumerated(EnumType.STRING)
	private BalanceStatus status;
	
	public IncomeBalance(){
		this.availableBalance = new BigDecimal("0");
		this.closingBalance = new BigDecimal("0");
		this.ledgerBalance = new BigDecimal("0");
		this.openingBalance = new BigDecimal("0");
	}
	
	public Account getAccount() {
		return account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
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
	
	public BigDecimal getOpeningBalance() {
		return   openingBalance  == null ? new BigDecimal("0") : openingBalance;
	}
	
	public void setOpeningBalance(BigDecimal openingBalance) {
		this.openingBalance = openingBalance;
	}
	
	public BigDecimal getClosingBalance() {
		return  closingBalance  == null ? new BigDecimal("0") : closingBalance;
	}
	
	public void setClosingBalance(BigDecimal closingBalance) {
		this.closingBalance = closingBalance;
	}
	
	public BigDecimal getLedgerBalance() {
		return ledgerBalance  == null ? new BigDecimal("0") : ledgerBalance;
	}
	
	public void setLedgerBalance(BigDecimal ledgerBalance) {
		this.ledgerBalance = ledgerBalance;
	}
	
	public BigDecimal getAvailableBalance() {
		return availableBalance == null ? new BigDecimal("0") : availableBalance;
	}
	
	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		IncomeBalance other = (IncomeBalance) obj;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public FiscalPeriod getPeriod() {
		return period;
	}
	
	public void setPeriod(FiscalPeriod period) {
		this.period = period;
	}
	
	public int getId() {
		return id;
	}
	
	public int getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	
	public int getUpdatedBy() {
		return updatedBy;
	}
	
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
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

	
    public BalanceStatus getStatus() {
    	return status;
    }

	
    public void setStatus(BalanceStatus status) {
    	this.status = status;
    }

	
}
