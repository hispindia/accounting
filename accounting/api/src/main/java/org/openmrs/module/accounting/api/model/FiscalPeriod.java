package org.openmrs.module.accounting.api.model;

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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "accounting_fiscal_period")
public class FiscalPeriod {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	
	@ManyToOne
	@JoinColumn(name = "fiscal_year_id")
	private FiscalYear fiscalYear;
	
	@Column(name = "name", length = 255)
	private String name;
	
	@Column(name = "start_date")
	@Type(type="timestamp")
	private Date startDate;
	
	@Column(name = "end_date")
	@Type(type="timestamp")
	private Date endDate;
	
	@Enumerated(EnumType.STRING)
	private GeneralStatus status; // INACTIVE, ACTIVE, CLOSED
	
	@Type(type="timestamp")
	@Column(name = "created_date")
	private Date createdDate;
	
	@Type(type="timestamp")
	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Column(name = "created_by")
	private int createdBy;
	
	@Column(name = "updated_by")
	private int updatedBy;
	
	
	public FiscalPeriod(){
		
	}

	
	
	public FiscalPeriod(String name, Date createdDate, int createdBy) {
	    super();
	    this.name = name;
	    this.createdDate = createdDate;
	    this.createdBy = createdBy;
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
	
	public FiscalYear getFiscalYear() {
		return fiscalYear;
	}
	
	public void setFiscalYear(FiscalYear fiscalYear) {
		this.fiscalYear = fiscalYear;
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



	@Override
    public String toString() {
	    return "FiscalPeriod [id=" + id + ", fiscalYear=" + fiscalYear + ", name=" + name + ", startDate=" + startDate
	            + ", endDate=" + endDate + ", status=" + status + ", createdDate=" + createdDate + ", updatedDate="
	            + updatedDate + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + "]";
    }



	
    public GeneralStatus getStatus() {
    	return status;
    }



	
    public void setStatus(GeneralStatus status) {
    	this.status = status;
    }
	
}
