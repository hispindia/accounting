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
			AccountTransaction accTxn = addAccountTransaction(incomeReceiptItem);
			
			incomeReceiptItem.setTxnNumber(accTxn.getBaseTxnNumber());
			
			rIncomeReceiptItem = dao.saveIncomeReceiptItem(incomeReceiptItem);
			
			/**
			 * Update Account Balance
			 */
			increaseBalance(rIncomeReceiptItem);
			
		} else {
			/** Update Receipt **/
			incomeReceiptItem.setUpdatedBy(Context.getAuthenticatedUser().getId());
			incomeReceiptItem.setUpdatedDate(Calendar.getInstance().getTime());
			
			/**
			 * When updating receipt item, there are 2 cases: Voided or Update the amount
			 * If Voided => cancelAccountTransaction for old receipt item
			 * If update amount =>  cancelAccountTransaction for old receipt then addAccountTransaction for new receipt amount
			 */
			
			IncomeReceiptItem oldReceipt = dao.getIncomeReceiptItem(incomeReceiptItem.getId());
			
			AccountTransaction accTxn = null;
			
			if (incomeReceiptItem.isVoided()) {
				
				/** Cancel Account Transaction for old receipt **/
				cancelAccountTransaction(oldReceipt.getTxnNumber(), Calendar.getInstance().getTime() );
				
			}else {
				
				/** Check if receipt amount were updated **/
				if (!oldReceipt.getAccount().equals(incomeReceiptItem.getAccount())) {
					
					/** Cancel Account Transaction for old receipt **/
					cancelAccountTransaction(oldReceipt.getTxnNumber(), Calendar.getInstance().getTime());
					
					/** Add  transaction for new receipt amount value **/
				
					accTxn = addAccountTransaction(incomeReceiptItem); 
					
				}
			} 
			
			incomeReceiptItem.setTxnNumber(accTxn.getBaseTxnNumber());
			
			/** Update new receipt **/
			rIncomeReceiptItem = dao.saveIncomeReceiptItem(incomeReceiptItem);
			
			/** Update Account Balance **/
			increaseBalance(rIncomeReceiptItem);
			
		}
		return rIncomeReceiptItem;
	}
	
	private  void cancelAccountTransaction(String  txnNumber, Date transactedOn) {
		AccountTransaction oldTxn = dao.getAccountTxn(txnNumber);
		if (oldTxn == null) {
			log.debug("Old Transaction = "+oldTxn);
			return;
		} 
		
		BigDecimal newBalance = oldTxn.getBalance().subtract(oldTxn.getAmount());
		AccountTransaction cancelTxn = new AccountTransaction();
		cancelTxn.setAccount(oldTxn.getAccount());
		cancelTxn.setBalance(newBalance);
		cancelTxn.setAmount(oldTxn.getAmount());
		cancelTxn.setCancelForTxn(oldTxn.getTxnNumber());
		cancelTxn.setTxnNumber(UUID.randomUUID().toString());
		cancelTxn.setBaseTxnNumber(oldTxn.getBaseTxnNumber());
		cancelTxn.setReferenceTxn(oldTxn.getTxnNumber());
		cancelTxn.setTxnStatus(TransactionStatus.CANCELED);
		cancelTxn.setCreatedDate(transactedOn);
		cancelTxn.setCreatedBy(Context.getAuthenticatedUser().getId());
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
		
		accBalance.setAvailableBalance(accBalance.getAvailableBalance().add(amount));
		accBalance.setLedgerBalance(accBalance.getLedgerBalance().add(amount));
		
		accBalance.setUpdatedBy(Context.getAuthenticatedUser().getId());
		accBalance.setUpdatedDate(Calendar.getInstance().getTime());
		
		saveAccountBalance(accBalance);
	}
	
	private void increaseBalance(IncomeReceiptItem receipt) throws Exception {
		updateAccountAvailableBalance(receipt.getAccount(), receipt.getReceipt().getReceiptDate(), receipt.getAmount());
	}
	
	private void increaseBalance(BudgetItem budget) throws Exception {
		updateAccountAvailableBalance(budget.getAccount(), budget.getCreatedDate(), budget.getAmount());
	}
	
	private void decreaseBalance(BudgetItem budget) throws Exception {
		BigDecimal amount = budget.getAmount().negate();
		updateAccountAvailableBalance(budget.getAccount(), budget.getCreatedDate(), amount);
	}
	
	private void decreaseBalance(IncomeReceiptItem receipt) throws Exception {
		BigDecimal amount = receipt.getAmount().negate();
		updateAccountAvailableBalance(receipt.getAccount(), receipt.getReceipt().getReceiptDate(), amount);
	}
	
	
	private void updateAccountBalance(BudgetItem budget) throws Exception {
		updateAccountAvailableBalance(budget.getAccount(), budget.getCreatedDate(), budget.getAmount());
		addAccountTransaction(budget);
	}
	
	public void updateAccountBalance(Account account, Date receiptDate, BigDecimal amount,TransactionType type) throws Exception {
		AccountBalance accBalance = dao.getLatestAccountBalance(account);
		if (accBalance == null) {
			throw new Exception("Can not find Account Balance with Account:" + account.getName() + " and Receipt Date: "
			        + receiptDate.toString());
		}
		
		accBalance.setAvailableBalance(accBalance.getAvailableBalance().add(amount));
		accBalance.setLedgerBalance(accBalance.getLedgerBalance().add(amount));
		
		accBalance.setUpdatedBy(Context.getAuthenticatedUser().getId());
		accBalance.setUpdatedDate(Calendar.getInstance().getTime());
		
		saveAccountBalance(accBalance);
	}
	
	@Override
	public List<AccountBalance> listActiveAccountBalance() {
		return dao.listAccountBalance(BalanceStatus.ACTIVE);
	}
	
	
	
	private AccountTransaction addAccountTransaction(IncomeReceiptItem receipt) {
		return addAccountTransaction(receipt.getAccount(), receipt.getAmount(), receipt.getCreatedBy(), receipt.getCreatedDate(), receipt.getTransactionDate());
	}
	
	private AccountTransaction addAccountTransaction(BudgetItem budget) {
		return addAccountTransaction(budget.getAccount(), budget.getAmount(), budget.getCreatedBy(), budget.getCreatedDate(), budget.getCreatedDate());
	}
	
	private AccountTransaction addAccountTransaction(Account account, BigDecimal amount, int createdBy, Date createdDate, Date transactionDate) {
		if ( account == null) return null;
		
		if (amount.compareTo(new BigDecimal("0")) == 0 ) {
			return null;
		}
		
		AccountTransaction oldTxn = dao.getLatestTransaction(account);
		BigDecimal newBalance = new BigDecimal("0");
		AccountTransaction newTxn = new AccountTransaction();
		if (oldTxn != null) {
			/**
			 * Add or Subtract the new balance with the latest transaction
			 */
			newBalance = oldTxn.getBalance().add(amount);
			newTxn.setReferenceTxn(oldTxn.getTxnNumber());
		} else {
			newBalance = amount;
		}
		
		newTxn.setAmount(amount);
		newTxn.setBalance(newBalance);
		newTxn.setAccount(account);
		newTxn.setTxnNumber(UUID.randomUUID().toString());
		newTxn.setBaseTxnNumber(newTxn.getTxnNumber());
		newTxn.setCreatedBy(createdBy);
		newTxn.setCreatedDate(createdDate);
		newTxn.setTransactionDate(transactionDate);
		newTxn.setTxnStatus(TransactionStatus.OPEN);
		
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
		Date curDate = Calendar.getInstance().getTime();
		item.setVoided(true);
		item.setVoidedBy(Context.getAuthenticatedUser().getId());
		item.setVoideddDate(curDate);
		dao.saveIncomeReceiptItem(item);
		
		AccountTransaction acctxn = dao.getAccountTxn(item.getTxnNumber());
		
		if ( acctxn == null ) {
			log.error("Can not find AccountTransaction of Income Receipt Item with id = "+id);
			throw new Exception("Can not find AccountTransaction of Income Receipt Item with id = "+id);
		}
		
		cancelAccountTransaction(item.getTxnNumber(), curDate);
		
		try {
	        decreaseBalance(item);
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
    public Budget saveBudget(Budget budget) throws Exception {
		
		if (budget.getId() == null) {
			/**
			 * Add new Budget => need to update account balance for each Budget Item
			 * Balance should be increased
			 */
			budget = dao.saveBudget(budget);
			if (budget.getBudgetItems() != null && !budget.getBudgetItems().isEmpty()) {
				for (BudgetItem item : budget.getBudgetItems()) {

					AccountTransaction acctxn = addAccountTransaction(item);
					
					item.setTxnNumber(acctxn.getTxnNumber());
					
					increaseBalance(item);
					
				}
			}
		} else {
			budget.setUpdatedBy(Context.getAuthenticatedUser().getId());
			budget.setUpdatedDate(Calendar.getInstance().getTime());
			budget = dao.saveBudget(budget);
		}
	    return budget;
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
    public void retireBudget(Integer id) throws Exception {
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
	    		
	    		cancelAccountTransaction(item.getTxnNumber(),curDate );
	    		
	    		increaseBalance(item);
	    	}
	    	dao.saveBudget(budget);
	    }
    }

	@Override
    public void retireBudgetItem(Integer id) throws Exception {
	    if (id == null) {
	    	return;
	    }
	    
	    BudgetItem item = dao.getBudgetItem(id);
	    if (item == null) {
	    	return;
	    }
	    Date curDate = Calendar.getInstance().getTime();
	    item.setRetired(true);
		item.setRetiredBy(Context.getAuthenticatedUser().getId());
		item.setRetiredDate(curDate);
		
		
		cancelAccountTransaction(item.getTxnNumber(),curDate );
		
		decreaseBalance(item);
		dao.saveBudgetItem(item);
    }

	@Override
    public BudgetItem saveBudgetItem(BudgetItem item) throws Exception {
		
		if (item == null) {
			return null;
		}
		 Date curDate = Calendar.getInstance().getTime();
		if (item.getId() != null) {
			// update
			
			BudgetItem persitedItem = dao.getBudgetItem(item.getId());
			
			// update account if needed
			if (!persitedItem.getAccount().getId().equals(item.getAccount().getId())) {
				persitedItem.setAccount(item.getAccount());
			}
			
			persitedItem.setAmount(item.getAmount());
			persitedItem.setDescription(item.getDescription());
			persitedItem.setEndDate(item.getEndDate());
			persitedItem.setStartDate(item.getStartDate());
			
			if (item.getRetired()) {
				persitedItem.setRetired(true);
				persitedItem.setRetiredBy(Context.getAuthenticatedUser().getId());
				persitedItem.setRetiredDate( curDate);

				cancelAccountTransaction(item.getTxnNumber(),curDate );
				
				decreaseBalance(item);
			} else {
				// Update Budget Item
				persitedItem.setUpdatedBy(Context.getAuthenticatedUser().getId());
				persitedItem.setUpdatedDate(Calendar.getInstance().getTime());
				
				cancelAccountTransaction(persitedItem.getTxnNumber(),curDate );
				decreaseBalance(persitedItem);
				
				// Add new account transaction
				addAccountTransaction(item);
				increaseBalance(item);
			}
			return dao.saveBudgetItem(persitedItem);
		} else {
			// create new
			item.setCreatedBy(Context.getAuthenticatedUser().getId());
			item.setCreatedDate(Calendar.getInstance().getTime());
			
			
			// add account_txn and update balance
			AccountTransaction acctxn = addAccountTransaction(item);
			item.setTxnNumber(acctxn.getTxnNumber());;
			increaseBalance(item);
			item = dao.saveBudgetItem(item);
			
			return item;
		}
		
		
    }
}
