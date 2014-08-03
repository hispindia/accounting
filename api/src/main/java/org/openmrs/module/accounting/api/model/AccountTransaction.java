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

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;

@Entity
@Table(name="accounting_account_txn")
public class AccountTransaction {
	
	@Id
	@GeneratedValue
	@Column(name = "account_txn_id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="account_id")
	private Account account;
	
	@Column(name = "amount", precision = 19, scale = 2)
	private BigDecimal amount;
	
	@Column(name = "balance", precision = 19, scale = 2)
	private BigDecimal balance;
	
	@Type(type="timestamp")
	@Column(name = "txn_date")
	@Index(name = "index_accounting_txn_date")
	private Date transactionDate;
	
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
	
	@Column(name = "txn_number")
	private String txnNumber;
	
	@Column(name = "cancel_for_txn")
	private String cancelForTxn;
	
	@Column(name = "base_txn_number")
	private String baseTxnNumber;
	
	@Column(name = "reference_txn")
	private String referenceTxn;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "txn_status")
	private TransactionStatus txnStatus;
	
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

    public BigDecimal getBalance() {
    	return balance;
    }

	
    public void setBalance(BigDecimal balance) {
    	this.balance = balance;
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






	
    public String getTxnNumber() {
    	return txnNumber;
    }


	
    public void setTxnNumber(String txnNumber) {
    	this.txnNumber = txnNumber;
    }


	
    public String getCancelForTxn() {
    	return cancelForTxn;
    }


	
    public void setCancelForTxn(String cancelForTxn) {
    	this.cancelForTxn = cancelForTxn;
    }


	
    public String getBaseTxnNumber() {
    	return baseTxnNumber;
    }


	
    public void setBaseTxnNumber(String baseTxnNumber) {
    	this.baseTxnNumber = baseTxnNumber;
    }


    public TransactionStatus getTxnStatus() {
    	return txnStatus;
    }


	
    public void setTxnStatus(TransactionStatus txnStatus) {
    	this.txnStatus = txnStatus;
    }


	
    public String getReferenceTxn() {
    	return referenceTxn;
    }


	
    public void setReferenceTxn(String referenceTxn) {
    	this.referenceTxn = referenceTxn;
    }


	
    public Date getTransactionDate() {
    	return transactionDate;
    }


	
    public void setTransactionDate(Date transactionDate) {
    	this.transactionDate = transactionDate;
    }


	
    public BigDecimal getAmount() {
    	return amount;
    }


	
    public void setAmount(BigDecimal amount) {
    	this.amount = amount;
    }


	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((amount == null) ? 0 : amount.hashCode());
	    result = prime * result + ((balance == null) ? 0 : balance.hashCode());
	    result = prime * result + ((baseTxnNumber == null) ? 0 : baseTxnNumber.hashCode());
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
	    AccountTransaction other = (AccountTransaction) obj;
	    if (amount == null) {
		    if (other.amount != null)
			    return false;
	    } else if (!amount.equals(other.amount))
		    return false;
	    if (balance == null) {
		    if (other.balance != null)
			    return false;
	    } else if (!balance.equals(other.balance))
		    return false;
	    if (baseTxnNumber == null) {
		    if (other.baseTxnNumber != null)
			    return false;
	    } else if (!baseTxnNumber.equals(other.baseTxnNumber))
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




	
	
}
