package org.openmrs.module.accounting.api.model;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author viet
 **/

@Entity
@Table(name = "accounting_account")
public class Account  {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "name", length = 255)
	private String name;
	
	@Column(name = "description", length = 255)
	private String description;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Column(name ="created_by")
	private int createdBy;
	
	@Column(name ="updated_by")
	private int updatedBy;
	
	@Column(name ="retired")
	private boolean retired;
	
	@Column(name ="retired_date")
	private Date retiredDate;
	
	@Column(name ="retired_by")
	private int retiredBy;
	
	@Column(name = "parrent_account_id")
	private Integer parentAccountId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "account_type")
	private AccountType accountType;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="account")
	private Set<AccountPeriod> accountPeriods;
	
	public Account() {
		super();
		this.retired = false;
	}
	
	public Account(String name) {
		super();
		this.name = name;
		this.retired = false;
	}
	
	public void addAccountPeriod(AccountPeriod ap) {
		if (ap != null) {
			if (!getAccountPeriods().contains(ap)) {
				ap.setAccount(this);
				this.getAccountPeriods().add(ap);
			}
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
	public Set<AccountPeriod> getAccountPeriods() {
		if (accountPeriods == null) {
			accountPeriods = new HashSet<AccountPeriod>();
		}
		return accountPeriods;
	}
	
	public void setAccountPeriods(Set<AccountPeriod> periods) {
		this.accountPeriods = periods;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Account other = (Account) obj;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean isRetired() {
		return retired;
	}
	
	public void setRetired(boolean retired) {
		this.retired = retired;
	}
	
	public AccountType getAccountType() {
		return accountType;
	}
	
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	
	
	public Date getRetiredDate() {
		return retiredDate;
	}
	
	public void setRetiredDate(Date retiredDate) {
		this.retiredDate = retiredDate;
	}
	
	public int getRetiredBy() {
		return retiredBy;
	}
	
	public void setRetiredBy(int retiredBy) {
		this.retiredBy = retiredBy;
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

	
    public Integer getParentAccountId() {
    	return parentAccountId;
    }

	
    public void setParentAccountId(Integer parentAccountId) {
    	this.parentAccountId = parentAccountId;
    }
	
}
