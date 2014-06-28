package org.openmrs.module.accounting.api.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.hibernate.annotations.Type;


@Entity
@Table(name="accounting_budget")
public class Budget {
	@Id
	@GeneratedValue
	@Column(name="budget_id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "start_date")
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "end_date")
	private Date endDate;
	
	@Type(type="timestamp")
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name="description")
	private String description;
	
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

	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="budget")
	private List<BudgetItem> budgetItems ;
	
	
    public Integer getId() {
    	return id;
    }

	
    public void setId(Integer id) {
    	this.id = id;
    }

	
    public String getName() {
    	return name;
    }

	
    public void setName(String name) {
    	this.name = name;
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

	
    public Date getCreatedDate() {
    	return createdDate;
    }

	
    public void setCreatedDate(Date createdDate) {
    	this.createdDate = createdDate;
    }

	
    public String getDescription() {
    	return description;
    }

	
    public void setDescription(String description) {
    	this.description = description;
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
    
    public void addBudgetItem(BudgetItem item) {
    	if (budgetItems == null) {
    		budgetItems = new ArrayList<BudgetItem>();
    	} 
    	budgetItems.add(item);
    }


	
    public List<BudgetItem> getBudgetItems() {
    	return budgetItems;
    }


	
    public void setBudgetItems(List<BudgetItem> budgetItems) {
    	this.budgetItems = budgetItems;
    }
}
