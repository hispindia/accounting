package org.openmrs.module.accounting.api.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
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
import org.openmrs.module.accounting.api.utils.DateUtils;

@Entity
@Table(name = "accounting_fiscal_period")
public class FiscalPeriod {
	
	@Id
	@GeneratedValue
	@Column(name = "period_id")
	private Integer id;
	
	
	@ManyToOne
	@JoinColumn(name = "fiscal_year_id")
	private FiscalYear fiscalYear;
	
	@Column(name = "name", length = 255)
	private String name;
	
	@Column(name = "start_date")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Column(name = "end_date")
	@Temporal(TemporalType.DATE)
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
	    return "FiscalPeriod [id=" + id + ", name=" + name + ", startDate=" + startDate
	            + ", endDate=" + endDate + ", status=" + status + ", createdDate=" + createdDate + ", updatedDate="
	            + updatedDate + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + "]";
    }


	public boolean isClosed(){
		return status != null && status.equals(GeneralStatus.CLOSED) ? true : false;
	}
	
	public boolean isActive() {
		return status != null && status.equals(GeneralStatus.ACTIVE) ? true : false;
	}
	
	public boolean isOpen() {
		return status != null && status.equals(GeneralStatus.OPEN) ? true : false;
	}

	
    public GeneralStatus getStatus() {
    	return status;
    }



	
    public void setStatus(GeneralStatus status) {
    	this.status = status;
    }



	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + createdBy;
	    result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
	    result = prime * result + ((id == null) ? 0 : id.hashCode());
	    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
	    FiscalPeriod other = (FiscalPeriod) obj;
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
	    if (name == null) {
		    if (other.name != null)
			    return false;
	    } else if (!name.equals(other.name))
		    return false;
	    return true;
    }
	
	public boolean isClosable() {
		Date cur = Calendar.getInstance().getTime();
		
		return !DateUtils.isCurrentPeriod(getStartDate(), getEndDate()) && cur.compareTo(getEndDate()) > 0 ;
	}
	
}
