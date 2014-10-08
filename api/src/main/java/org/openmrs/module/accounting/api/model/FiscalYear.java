package org.openmrs.module.accounting.api.model;

/**
 * @author viet
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "accounting_fiscal_year")
public class FiscalYear {
	
	@Id
	@GeneratedValue
	@Column(name = "year_id")
	private Integer id;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, mappedBy = "fiscalYear", targetEntity = FiscalPeriod.class)
	@javax.persistence.OrderBy("startDate, endDate")
	private List<FiscalPeriod> periods;
	
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
	
	@Column(name = "name", length = 255)
	private String name;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "created_date")
	@Type(type="timestamp")
	private Date createdDate;
	
	@Column(name = "created_by")
	private int createdBy;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "updated_date")
	@Type(type="timestamp")
	private Date updatedDate;
	
	@Column(name = "updated_by")
	private int updatedBy;
	
	@Enumerated(EnumType.STRING)
	private GeneralStatus status;
	
	public FiscalYear() {
		this.status = GeneralStatus.ACTIVE;
	}
	
	public FiscalYear(String name, Date createdDate, int createdBy) {
		super();
		this.name = name;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public BigDecimal getOpeningBalance() {
		return openingBalance;
	}
	
	public void setOpeningBalance(BigDecimal openingBalance) {
		this.openingBalance = openingBalance;
	}
	
	public BigDecimal getClosingBalance() {
		return closingBalance;
	}
	
	public void setClosingBalance(BigDecimal closingBalance) {
		this.closingBalance = closingBalance;
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
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
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
		FiscalYear other = (FiscalYear) obj;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
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
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public int getCreatedBy() {
		return createdBy;
	}
	
	public int getUpdatedBy() {
		return updatedBy;
	}
	
    public GeneralStatus getStatus() {
    	return status;
    }

	
    public void setStatus(GeneralStatus status) {
    	this.status = status;
    }

	@Override
    public String toString() {
	    return "FiscalYear [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate
	            + ", openingBalance=" + openingBalance + ", closingBalance=" + closingBalance + ", name=" + name
	            + ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", updatedDate=" + updatedDate
	            + ", updatedBy=" + updatedBy + ", status=" + status + "]";
    }

	
    public List<FiscalPeriod> getPeriods() {
    	return periods;
    }

	
    public void setPeriods(List<FiscalPeriod> periods) {
    	this.periods = periods;
    }

    public void addPeriod(FiscalPeriod period) {
    	if (periods == null) {
    		periods = new ArrayList<FiscalPeriod>();
    	} 
    	periods.add(period);
    }
    
    public boolean getClosed(){
    	return GeneralStatus.CLOSED.equals(this.status);
    }
    
}
