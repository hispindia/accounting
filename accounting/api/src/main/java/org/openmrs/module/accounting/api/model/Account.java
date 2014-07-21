package org.openmrs.module.accounting.api.model;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * @author viet
 **/

@Entity
@Table(name = "accounting_account")
public class Account  implements Serializable{
	
	/**
     * 
     */
    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "account_id")
	private Integer id;
	
	@Column(name = "name", length = 255)
	private String name;
	
	@Column(name = "account_number", length = 50)
	private String accountNumber;
	
	@Column(name = "description", length = 1000)
	private String description;
	
	@Column(name = "created_date")
	@Type(type="timestamp")
	private Date createdDate;
	
	@Column(name ="created_by")
	private int createdBy;
	
	@Column(name ="updated_by")
	private int updatedBy;
	
	@Column(name = "updated_date")
	@Type(type="timestamp")
	private Date updatedDate;
	
	@Column(name ="retired")
	private boolean retired;
	
	@Column(name ="retired_date")
	@Type(type="timestamp")
	private Date retiredDate;
	
	@Column(name ="retired_by")
	private int retiredBy;
	
	@Column(name = "parrent_account_id")
	private Integer parentAccountId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "account_type")
	private AccountType accountType;
	
	@Column(name="concept_id")
	private Integer conceptId;
	
	public Account() {
		this.retired = false;
	}
	
	public Account(String name) {
		super();
		this.name = name;
		this.retired = false;
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
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
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

	
    public AccountType getAccountType() {
    	return accountType;
    }

	
    public void setAccountType(AccountType accountType) {
    	this.accountType = accountType;
    }

	
    public Integer getConceptId() {
    	return conceptId;
    }

	
    public void setConceptId(Integer conceptId) {
    	this.conceptId = conceptId;
    }

	@Override
    public String toString() {
	    return "Account [id=" + id + ", name=" + name + "]";
    }

	
    public String getAccountNumber() {
    	return accountNumber;
    }

	
    public void setAccountNumber(String accountNumber) {
    	this.accountNumber = accountNumber;
    }

	
	
}
