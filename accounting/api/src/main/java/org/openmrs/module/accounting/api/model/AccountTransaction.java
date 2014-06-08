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
	
	@Enumerated(EnumType.STRING)
	@Column(name = "txn_type")
	private TransactionType type;
	
	@Column(name = "credit", precision = 19, scale = 2)
	private BigDecimal credit;
	
	@Column(name = "debit", precision = 19, scale = 2)
	private BigDecimal debit;
	
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

	
    public TransactionType getType() {
    	return type;
    }

	
    public void setType(TransactionType type) {
    	this.type = type;
    }

	
    public BigDecimal getCredit() {
    	return credit;
    }

	
    public void setCredit(BigDecimal credit) {
    	this.credit = credit;
    }

	
    public BigDecimal getDebit() {
    	return debit;
    }

	
    public void setDebit(BigDecimal debit) {
    	this.debit = debit;
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
	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((balance == null) ? 0 : balance.hashCode());
	    result = prime * result + ((credit == null) ? 0 : credit.hashCode());
	    result = prime * result + ((debit == null) ? 0 : debit.hashCode());
	    result = prime * result + ((id == null) ? 0 : id.hashCode());
	    result = prime * result + ((transactionDate == null) ? 0 : transactionDate.hashCode());
	    result = prime * result + ((type == null) ? 0 : type.hashCode());
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
	    if (balance == null) {
		    if (other.balance != null)
			    return false;
	    } else if (!balance.equals(other.balance))
		    return false;
	    if (credit == null) {
		    if (other.credit != null)
			    return false;
	    } else if (!credit.equals(other.credit))
		    return false;
	    if (debit == null) {
		    if (other.debit != null)
			    return false;
	    } else if (!debit.equals(other.debit))
		    return false;
	    if (id == null) {
		    if (other.id != null)
			    return false;
	    } else if (!id.equals(other.id))
		    return false;
	    if (transactionDate == null) {
		    if (other.transactionDate != null)
			    return false;
	    } else if (!transactionDate.equals(other.transactionDate))
		    return false;
	    if (type != other.type)
		    return false;
	    return true;
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


	@Override
    public String toString() {
	    return "AccountTransaction [id=" + id + ", account=" + account + ", type=" + type + ", credit=" + credit
	            + ", debit=" + debit + ", balance=" + balance + ", transctionDate=" + transactionDate + ", createdDate="
	            + createdDate + ", updatedDate=" + updatedDate + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy
	            + ", txnNumber=" + txnNumber + ", cancelForTxn=" + cancelForTxn + ", baseTxnNumber=" + baseTxnNumber
	            + ", txnStatus=" + txnStatus + "]";
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




	
	
}
