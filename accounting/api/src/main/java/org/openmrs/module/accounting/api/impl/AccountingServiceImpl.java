package org.openmrs.module.accounting.api.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.db.AccountingDAO;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.AccountBalance;
import org.openmrs.module.accounting.api.model.AccountTransaction;
import org.openmrs.module.accounting.api.model.BalanceStatus;
import org.openmrs.module.accounting.api.model.Budget;
import org.openmrs.module.accounting.api.model.BudgetItem;
import org.openmrs.module.accounting.api.model.FiscalPeriod;
import org.openmrs.module.accounting.api.model.FiscalYear;
import org.openmrs.module.accounting.api.model.GeneralStatus;
import org.openmrs.module.accounting.api.model.IncomeReceipt;
import org.openmrs.module.accounting.api.model.IncomeReceiptItem;
import org.openmrs.module.accounting.api.model.TransactionStatus;
import org.openmrs.module.accounting.api.model.TransactionType;
import org.openmrs.module.accounting.api.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * It is a default implementation of {@link AccountingService}.
 */
public class AccountingServiceImpl extends BaseOpenmrsService implements AccountingService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private AccountingDAO dao;
	
	/**
	 * @param dao the dao to set
	 */
	public void setDao(AccountingDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * @return the dao
	 */
	public AccountingDAO getDao() {
		return dao;
	}
	
	public Account saveAccount(Account acc) {
		
		if (acc.getId() == null) {
			
			log.info("Create new account: " + acc.getName());
			acc.setCreatedDate(Calendar.getInstance().getTime());
			acc.setCreatedBy(Context.getAuthenticatedUser().getId());
			acc = dao.saveAccount(acc);
			
			AccountBalance accBalance = new AccountBalance();
			accBalance.setAccount(acc);
			accBalance.setAvailableBalance(new BigDecimal(0));
			accBalance.setClosingBalance(new BigDecimal(0));
			accBalance.setCreatedBy(acc.getCreatedBy());
			accBalance.setCreatedDate(acc.getCreatedDate());
			accBalance.setLedgerBalance(new BigDecimal(0));
			accBalance.setOpeningBalance(new BigDecimal(0));
			accBalance.setStatus(BalanceStatus.ACTIVE);
			
			dao.saveAccountBalance(accBalance);
			
			return acc;
		} else {
			log.info("update  account: " + acc.getName());
			acc.setUpdatedBy(Context.getAuthenticatedUser().getId());
			acc.setUpdatedDate(Calendar.getInstance().getTime());
			if (acc.isRetired()) {
				acc.setRetiredDate(Calendar.getInstance().getTime());
				acc.setRetiredBy(Context.getAuthenticatedUser().getId());
			}
			return dao.saveAccount(acc);
		}
		
	}
	
	public void deleteAccount(Account acc) {
		dao.deleteAccount(acc);
	}
	
	public Collection<Account> getAccounts(boolean includeDisabled) {
		return dao.getAccounts(includeDisabled);
	}
	
	public Account getAccount(int id) {
		return dao.getAccount(id);
	}
	
	public FiscalYear saveFiscalYear(FiscalYear fy) {
		if (fy.getId() == null) {
			fy.setCreatedDate(Calendar.getInstance().getTime());
			fy.setCreatedBy(Context.getAuthenticatedUser().getId());
		} else {
			fy.setUpdatedBy(Context.getAuthenticatedUser().getId());
			fy.setUpdatedDate(Calendar.getInstance().getTime());
		}
		
		return dao.saveFiscalYear(fy);
	}
	
	public FiscalYear getFiscalYear(int id) {
		return dao.getFiscalYear(id);
	}
	
	public FiscalPeriod saveFiscalPeriod(FiscalPeriod fp) {
		
		fp.setEndDate(DateUtils.getEnd(fp.getEndDate()));
		return dao.saveFiscalPeriod(fp);
	}
	
	public FiscalPeriod getFiscalPeriod(int id) {
		return dao.getFiscalPeriod(id);
	}
	
	public AccountBalance saveAccountBalance(AccountBalance ap) {
		return dao.saveAccountBalance(ap);
	}
	
	public AccountBalance getAccountPeriod(int id) {
		return dao.getAccountPeriod(id);
	}
	
	public Collection<Account> getListParrentAccount() {
		return dao.getListParrentAccount();
	}
	
	public Account getAccountByName(String name) {
		return dao.getAccountByName(name);
	}
	
	public FiscalYear getFiscalYearByName(String name) {
		return dao.getFiscalYearByName(name);
	}
	
	public Collection<FiscalYear> getListFiscalYear(GeneralStatus status) {
		return dao.getListFiscalYear(status);
	}
	
	public void deleteFiscalYear(FiscalYear fiscalYear) {
		dao.deleteFiscalYear(fiscalYear);
		;
	}
	
	public void deletePeriod(FiscalPeriod period) {
		dao.deleteFiscalPeriod(period);
	}
	
	@Override
	public void initModule() {
		log.debug("****************************************");
		log.debug("INIT ACCOUNTING MODULE");
		log.debug("****************************************");
		//		Integer rootServiceConceptId = Integer.valueOf(Context.getAdministrationService().getGlobalProperty(
		//		    BillingConstants.GLOBAL_PROPRETY_SERVICE_CONCEPT));
		//		Concept rootServiceconcept = Context.getConceptService().getConcept(rootServiceConceptId);
		//		Collection<ConceptAnswer> answers = rootServiceconcept.getAnswers();
		//		log.debug(answers);
		//		
		//		for (ConceptAnswer ca : answers) {
		//			log.debug(ca.getAnswerConcept().getName().getName());
		//			
		//		}
		
	}
	
	@Override
	public IncomeReceipt saveIncomeReceipt(IncomeReceipt incomeReceipt) {
		if (incomeReceipt.getId() == null) {
			incomeReceipt.setCreatedBy(Context.getAuthenticatedUser().getId());
			incomeReceipt.setCreatedDate(Calendar.getInstance().getTime());
		} else {
			incomeReceipt.setUpdatedBy(Context.getAuthenticatedUser().getId());
			incomeReceipt.setUpdatedDate(Calendar.getInstance().getTime());
		}
		return dao.saveIncomeReceipt(incomeReceipt);
	}
	
	@Override
	public IncomeReceipt getIncomeReceipt(Integer id) {
		return dao.getIncomeReceipt(id);
	}
	
	@Override
	public List<IncomeReceipt> getListIncomeReceipt(boolean includeVoided) {
		return dao.getListIncomeReceipt(includeVoided);
	}
	
	@Override
	public List<IncomeReceipt> getListIncomeReceiptByDate(String startDate, String endDate, boolean includeVoided) {
		return dao.getListIncomeReceiptByDate(startDate, endDate, includeVoided);
	}
	
	@Override
	public void delete(IncomeReceipt incomeReceipt) {
		dao.delete(incomeReceipt);
	}
	
	@Override
	public IncomeReceiptItem saveIncomeReceiptItem(IncomeReceiptItem incomeReceiptItem) throws Exception {
		IncomeReceiptItem rIncomeReceiptItem;
		log.debug("Save income reeceipt item: " + incomeReceiptItem);
		if (incomeReceiptItem.getId() == null) {
			/** New Receipt **/
			incomeReceiptItem.setCreatedBy(Context.getAuthenticatedUser().getId());
			incomeReceiptItem.setCreatedDate(Calendar.getInstance().getTime());
			
			/** Add Account Transaction **/
			AccountTransaction accTxn = addAccountTransaction(incomeReceiptItem, TransactionType.INCOME);
			
			incomeReceiptItem.setTxnNumber(accTxn.getBaseTxnNumber());
			
			rIncomeReceiptItem = dao.saveIncomeReceiptItem(incomeReceiptItem);
			
			/**
			 * Update Account Balance
			 */
			updateAccountBalance(incomeReceiptItem.getAccount(), incomeReceiptItem.getReceipt().getReceiptDate(),
			    incomeReceiptItem.getAmount(), TransactionType.INCOME);
			
		} else {
			/** Update Receipt **/
			incomeReceiptItem.setUpdatedBy(Context.getAuthenticatedUser().getId());
			incomeReceiptItem.setUpdatedDate(Calendar.getInstance().getTime());
			
			/**
			 * When updating receipt item, there are 2 cases: Voided or Update the amount
			 * If Voided => cancelAccountTransaction for old receipt item
			 * Else If update amount =>  cancelAccountTransaction for old receipt then addAccountTransaction for new receipt amount
			 */
			
			IncomeReceiptItem oldReceipt = dao.getIncomeReceiptItem(incomeReceiptItem.getId());
			
			if (incomeReceiptItem.isVoided()) {
				
				/** Cancel Account Transaction for old receipt **/
				cancelAccountTransaction(oldReceipt);
				
			}else {
				
				/** Check if receipt amount were updated **/
				if (!oldReceipt.getAccount().equals(incomeReceiptItem.getAccount())) {
					
					/** Cancel Account Transaction for old receipt **/
					cancelAccountTransaction(oldReceipt);
					
					/** Add  transaction for new receipt amount value **/
				
					addAccountTransaction(incomeReceiptItem, TransactionType.INCOME); 
					
				}
			} 
			
			/** Update new receipt **/
			rIncomeReceiptItem = dao.saveIncomeReceiptItem(incomeReceiptItem);
			
			/** Update Account Balance **/
			updateAccountBalance(incomeReceiptItem.getAccount(), incomeReceiptItem.getReceipt().getReceiptDate(),
			    incomeReceiptItem.getAmount(), TransactionType.INCOME);
			
		}
		return rIncomeReceiptItem;
	}
	
	public void cancelAccountTransaction(IncomeReceiptItem receiptItem) {
		
		AccountTransaction oldTxn = dao.getAccountTxn(receiptItem.getTxnNumber());
		
		BigDecimal newBalance = oldTxn.getBalance().subtract(receiptItem.getAmount());
		
		AccountTransaction cancelTxn = new AccountTransaction();
		cancelTxn.setAccount(oldTxn.getAccount());
		cancelTxn.setBalance(newBalance);
		cancelTxn.setDebit(receiptItem.getAmount());
		cancelTxn.setCancelForTxn(oldTxn.getTxnNumber());
		cancelTxn.setTxnNumber(UUID.randomUUID().toString());
		cancelTxn.setBaseTxnNumber(oldTxn.getBaseTxnNumber());
		cancelTxn.setReferenceTxn(oldTxn.getTxnNumber());
		cancelTxn.setType(TransactionType.INCOME);
		cancelTxn.setTxnStatus(TransactionStatus.CANCELED);
		cancelTxn.setCreatedDate(receiptItem.getCreatedDate());
		cancelTxn.setCreatedBy(receiptItem.getCreatedBy());
		dao.saveAccountTransaction(cancelTxn);
		
	}
	
	@Override
	public IncomeReceiptItem getIncomeReceiptItem(Integer id) {
		return dao.getIncomeReceiptItem(id);
	}
	
	@Override
	public List<IncomeReceiptItem> getListIncomeReceiptItem(boolean includeVoided) {
		return dao.getListIncomeReceiptItem(includeVoided);
	}
	
	@Override
	public List<IncomeReceiptItem> getListIncomeReceiptItemByDate(String startDate, String endDate) {
		return dao.getListIncomeReceiptItemByDate(startDate, endDate);
	}
	
	@Override
	public void delete(IncomeReceiptItem incomeReceiptItem) {
		dao.delete(incomeReceiptItem);
	}
	
	@Override
	public List<IncomeReceiptItem> getListIncomeReceiptItemByAccount(Account acc) {
		return dao.getListIncomeReceiptItemByAccount(acc);
	}
	
	@Override
	public List<AccountBalance> findAccountBalance(Integer fiscalPeriodId) {
		FiscalPeriod period = dao.getFiscalPeriod(fiscalPeriodId);
		if (period == null) {
			return null;
		} else {
			return dao.findAccountPeriods(period);
		}
	}
	
	public void updateAccountLedgerBalance(Account account, Date receiptDate, BigDecimal updateAmount) throws Exception {
		
		FiscalPeriod period = dao.getPeriodByDate(receiptDate);
		if (period == null) {
			throw new Exception("Can not find  Period with Receipt Date: " + receiptDate.toString());
		}
		
		AccountBalance accBalance = dao.getAccountPeriod(account, period);
		if (accBalance == null) {
			throw new Exception("Can not find Account Period with Receipt Date: " + receiptDate.toString()
			        + " and Account: " + account.getName());
		}
		
		accBalance.setLedgerBalance(accBalance.getLedgerBalance().add(updateAmount));
		accBalance.setUpdatedBy(Context.getAuthenticatedUser().getId());
		accBalance.setUpdatedDate(Calendar.getInstance().getTime());
		
		saveAccountBalance(accBalance);
	}
	
	@Override
	public AccountBalance findAccountPeriod(Account account, Date date) {
		return dao.findAccountPeriod(account, date);
	}
	
	public void updateAccountAvailableBalance(Account account, Date receiptDate, BigDecimal amount) throws Exception {
		AccountBalance accBalance = dao.getLatestAccountBalance(account);
		if (accBalance == null) {
			throw new Exception("Can not find Account Balance with Account:" + account.getName() + " and Receipt Date: "
			        + receiptDate.toString());
		}
		
		accBalance.setAvailableBalance(accBalance.getLedgerBalance().add(amount));
		accBalance.setUpdatedBy(Context.getAuthenticatedUser().getId());
		accBalance.setUpdatedDate(Calendar.getInstance().getTime());
		
		saveAccountBalance(accBalance);
	}
	
	public void updateAccountBalance(Account account, Date receiptDate, BigDecimal amount,TransactionType type) throws Exception {
		AccountBalance accBalance = dao.getLatestAccountBalance(account);
		if (accBalance == null) {
			throw new Exception("Can not find Account Balance with Account:" + account.getName() + " and Receipt Date: "
			        + receiptDate.toString());
		}
		
		if ( type.equals(TransactionType.INCOME) ) {
			accBalance.setAvailableBalance(accBalance.getLedgerBalance().add(amount));
			accBalance.setLedgerBalance(accBalance.getLedgerBalance().add(amount));
		} else if (type.equals(TransactionType.INCOME) ) {
			accBalance.setAvailableBalance(accBalance.getLedgerBalance().subtract(amount));
			accBalance.setLedgerBalance(accBalance.getLedgerBalance().subtract(amount));
		}
		
		accBalance.setUpdatedBy(Context.getAuthenticatedUser().getId());
		accBalance.setUpdatedDate(Calendar.getInstance().getTime());
		
		saveAccountBalance(accBalance);
	}
	
	@Override
	public List<AccountBalance> listActiveAccountBalance() {
		return dao.listAccountBalance(BalanceStatus.ACTIVE);
	}
	
	public AccountTransaction addAccountTransaction(IncomeReceiptItem receipt, TransactionType txnType) {
		
		AccountTransaction oldTxn = dao.getLatestTransaction(receipt.getAccount());
		BigDecimal newBalance = receipt.getAmount();
		if (oldTxn != null) {
			if (txnType.equals(TransactionType.INCOME)) {
				newBalance = newBalance.add(oldTxn.getBalance());
			} else if (txnType.equals(TransactionType.INCOME)) {
				newBalance = oldTxn.getBalance().subtract(newBalance);
			}
		}
		
		AccountTransaction newTxn = new AccountTransaction();
		newTxn.setAccount(receipt.getAccount());
		newTxn.setTxnNumber(UUID.randomUUID().toString());
		newTxn.setBaseTxnNumber(newTxn.getTxnNumber());
		if (oldTxn != null) {
			newTxn.setReferenceTxn(oldTxn.getTxnNumber());
		}
		newTxn.setCreatedBy(receipt.getCreatedBy());
		newTxn.setCreatedDate(receipt.getCreatedDate());
		newTxn.setCredit(receipt.getAmount());
		newTxn.setTransactionDate(receipt.getTransactionDate());
		newTxn.setTxnStatus(TransactionStatus.OPEN);
		newTxn.setBalance(newBalance);
		newTxn.setType(txnType);
		
		return  dao.saveAccountTransaction(newTxn);
		
		
		
	}

	@Override
    public void voidIncomeReceiptItem(Integer id) throws Exception { 
		log.debug("Begin void IncomeReceiptItem id = "+id);
		if ( id == null ) {
			log.error("Can not find Income Receipt Item with id = "+id);
			throw new Exception("Can not find Income Receipt Item with id = "+id);
		}
		IncomeReceiptItem item = dao.getIncomeReceiptItem(id);
		if ( item == null ) {
			log.error("Can not find Income Receipt Item with id = "+id);
			throw new Exception("Can not find Income Receipt Item with id = "+id);
		}
		item.setVoided(true);
		item.setVoidedBy(Context.getAuthenticatedUser().getId());
		item.setVoideddDate(Calendar.getInstance().getTime());
		dao.saveIncomeReceiptItem(item);
		
		AccountTransaction acctxn = dao.getAccountTxn(item.getTxnNumber());
		
		if ( acctxn == null ) {
			log.error("Can not find AccountTransaction of Income Receipt Item with id = "+id);
			throw new Exception("Can not find AccountTransaction of Income Receipt Item with id = "+id);
		}
		
		cancelAccountTransaction(item);
		
		try {
	        updateAccountBalance(item.getAccount(), item.getTransactionDate(), item.getAmount(), TransactionType.INCOME);
        }
        catch (Exception e) {
        	log.error(e);
        	throw new Exception(e);
        }
		
		
    }

	@Override
    public boolean isOverlapFiscalYear(String from, String to) {
	    return dao.isOverlapFiscalYear(DateUtils.getDateFromStr(from), DateUtils.getDateFromStr(to));
    }

	@Override
    public boolean isOverlapFiscalYear(Date from, Date to) {
	    return dao.isOverlapFiscalYear(from, to);
    }

	@Override
    public FiscalYear getActiveFiscalYear() {
	    return dao.getActiveFicalYear();
    }

	@Override
    public Budget saveBudget(Budget budget) {
	    return dao.saveBudget(budget);
    }

	@Override
    public Budget getBudget(Integer id) {
		if (id == null) {
			return null;
		}
		return dao.getBudget(id);
    }

	@Override
    public List<Budget> getBudgets(Boolean includeRetired) {
	    return dao.getBudgets(includeRetired);
    }

	@Override
    public void deleteBudget(Budget budget) {
		dao.deleteBudget(budget);
    }

	@Override
    public void retireBudget(Integer id) {
	    if (id == null) {
	    	return;
	    }
	    
	    Budget budget = dao.getBudget(id);
	    if (budget != null) {
	    	budget.setRetired(true);
	    	budget.setRetiredBy(Context.getAuthenticatedUser().getId());
	    	Date curDate = Calendar.getInstance().getTime();
	    	budget.setRetiredDate(curDate);
	    	
	    	for(BudgetItem item : budget.getBudgetItems()) {
	    		item.setRetired(true);
	    		item.setRetiredBy(Context.getAuthenticatedUser().getId());
	    		item.setRetiredDate(curDate);
	    		dao.saveBudgetItem(item);
	    	}
	    	dao.saveBudget(budget);
	    	
	    	// TODO update expense balance
	    	
	    }
    }

	@Override
    public void retireBudgetItem(Integer id) {
	    if (id == null) {
	    	return;
	    }
	    
	    BudgetItem item = dao.getBudgetItem(id);
	    if (item == null) {
	    	return;
	    }
	    
	    item.setRetired(true);
	    item.setRetired(true);
		item.setRetiredBy(Context.getAuthenticatedUser().getId());
		item.setRetiredDate(Calendar.getInstance().getTime());
		
		dao.saveBudgetItem(item);
		
		// TODO update expense balance
    }

	@Override
    public BudgetItem saveBudgetItem(BudgetItem item) {
		
		if (item == null) {
			return null;
		}
		
		if (item.getId() != null) {
		
			BudgetItem oldItem = dao.getBudgetItem(item.getId());
			
			if (!oldItem.getAccount().getId().equals(item.getAccount().getId())) {
				//oldItem.setAccount(dao.getAccount(item.getAccount().getId()));
				oldItem.setAccount(item.getAccount());
			}
			
			oldItem.setAmount(item.getAmount());
			oldItem.setDescription(item.getDescription());
			oldItem.setEndDate(item.getEndDate());
			oldItem.setStartDate(item.getStartDate());
			
			if (item.getRetired()) {
				oldItem.setRetired(true);
				oldItem.setRetiredBy(Context.getAuthenticatedUser().getId());
				oldItem.setRetiredDate(Calendar.getInstance().getTime());
			} else {
				oldItem.setUpdatedBy(Context.getAuthenticatedUser().getId());
				oldItem.setUpdatedDate(Calendar.getInstance().getTime());
			}
			return dao.saveBudgetItem(oldItem);
		} else {
			
			item.setCreatedBy(Context.getAuthenticatedUser().getId());
			item.setCreatedDate(Calendar.getInstance().getTime());
			return dao.saveBudgetItem(item);
		}
		
		
    }
}
