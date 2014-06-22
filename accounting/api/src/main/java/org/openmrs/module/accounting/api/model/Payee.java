package org.openmrs.module.accounting.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "accounting_payee")
public class Payee {
	
	@Id
	@GeneratedValue
	@Column(name="payee_id")
	private Integer id;
	
	@Column(name="name", length=255)
	private String name;
	
	@Column(name = "description" , length=1000)
	private String description;
	
	@Column(name = "created_date")
	@Type(type="timestamp")
	private Date createdDate;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "updated_by")
	private Integer updatedBy;
	
	@Column(name = "updated_date")
	@Type(type="timestamp")
	private Date updatedDate;
	
	@Column(name = "retired")
	private Boolean retired;
	
	@Column(name = "retired_date")
	@Type(type="timestamp")
	private Date retiredDate;
	
	@Column(name = "retired_by")
	private Integer retiredBy;

	
	public Payee(){
		this.retired = false;
	}
	
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

	
    public String getDescription() {
    	return description;
    }

	
    public void setDescription(String description) {
    	this.description = description;
    }

	
    public Date getCreatedDate() {
    	return createdDate;
    }

	
    public void setCreatedDate(Date createdDate) {
    	this.createdDate = createdDate;
    }

	
    public Integer getCreatedBy() {
    	return createdBy;
    }

	
    public void setCreatedBy(Integer createdBy) {
    	this.createdBy = createdBy;
    }

	
    public Integer getUpdatedBy() {
    	return updatedBy;
    }

	
    public void setUpdatedBy(Integer updatedBy) {
    	this.updatedBy = updatedBy;
    }

	
    public Date getUpdatedDate() {
    	return updatedDate;
    }

	
    public void setUpdatedDate(Date updatedDate) {
    	this.updatedDate = updatedDate;
    }

	
    public Boolean getRetired() {
    	return retired;
    }

	
    public void setRetired(Boolean retired) {
    	this.retired = retired;
    }

	
    public Date getRetiredDate() {
    	return retiredDate;
    }

	
    public void setRetiredDate(Date retiredDate) {
    	this.retiredDate = retiredDate;
    }

	
    public Integer getRetiredBy() {
    	return retiredBy;
    }

	
    public void setRetiredBy(Integer retiredBy) {
    	this.retiredBy = retiredBy;
    }
}
