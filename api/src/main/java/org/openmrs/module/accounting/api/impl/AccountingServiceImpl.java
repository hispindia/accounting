package org.openmrs.module.accounting.api.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.ConceptClass;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.db.AccountingDAO;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.BankAccount;
import org.openmrs.module.accounting.api.model.BankStatement;
import org.openmrs.module.accounting.api.model.IncomeBalance;
import org.openmrs.module.accounting.api.model.AccountTransaction;
import org.openmrs.module.accounting.api.model.AccountType;
import org.openmrs.module.accounting.api.model.BalanceStatus;
import org.openmrs.module.accounting.api.model.Budget;
import org.openmrs.module.accounting.api.model.BudgetItem;
import org.openmrs.module.accounting.api.model.ExpenseBalance;
import org.openmrs.module.accounting.api.model.FiscalPeriod;
import org.openmrs.module.accounting.api.model.FiscalYear;
import org.openmrs.module.accounting.api.model.GeneralStatus;
import org.openmrs.module.accounting.api.model.IncomeReceipt;
import org.openmrs.module.accounting.api.model.IncomeReceiptItem;
import org.openmrs.module.accounting.api.model.IncomeReceiptType;
import org.openmrs.module.accounting.api.model.Payee;
import org.openmrs.module.accounting.api.model.Payment;
import org.openmrs.module.accounting.api.model.PaymentStatus;
import org.openmrs.module.accounting.api.model.TransactionStatus;
import org.openmrs.module.accounting.api.model.TransactionType;
import org.openmrs.module.accounting.api.utils.AccountingConstants;
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
	
	public Account saveAccount(Account acc, Integer periodId) {
		
		if (acc.getId() == null) {
			
			log.info("Create new account: " + acc.getName());
			acc.setCreatedDate(Calendar.getInstance().getTime());
			acc.setCreatedBy(Context.getAuthenticatedUser().getId());
			
			acc = dao.saveAccount(acc);
			
			if (periodId != null) {
				FiscalPeriod period = dao.getFiscalPeriod(periodId);
				
				if (period != null) {
				
					if (acc.getAccountType().equals(AccountType.INCOME)) {
						IncomeBalance accBalance = new IncomeBalance();
						accBalance.setAccount(acc);
						accBalance.setCreatedBy(acc.getCreatedBy());
						accBalance.setCreatedDate(acc.getCreatedDate());
						accBalance.setStatus(BalanceStatus.ACTIVE);
						accBalance.setStartDate(period.getStartDate());
						accBalance.setEndDate(period.getEndDate());
						accBalance.setPeriod(period);
						dao.saveAccountBalance(accBalance);
					} else if (acc.getAccountType().equals(AccountType.EXPENSE)) {
						ExpenseBalance accBalance = new ExpenseBalance();
						accBalance.setAccount(acc);
						accBalance.setCreatedBy(acc.getCreatedBy());
						accBalance.setCreatedDate(acc.getCreatedDate());
						accBalance.setStatus(BalanceStatus.ACTIVE);
						accBalance.setStartDate(period.getStartDate());
						accBalance.setEndDate(period.getEndDate());
						accBalance.setPeriod(period);
						dao.saveExpenseBalance(accBalance);
					}
				}
			}
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
		//		dao.deleteAccount(acc);
		if (acc != null) {
			acc.setRetired(true);
			acc.setRetiredDate(Calendar.getInstance().getTime());
			dao.saveAccount(acc);
		}
	}
	
	public Collection<Account> getAccounts(boolean includeDisabled) {
		return dao.getAccounts(includeDisabled);
	}
	
	public Account getAccount(int id) {
		return dao.getAccount(id);
	}
	
	public FiscalYear saveFiscalYear(FiscalYear fy) {
		
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
	
	public IncomeBalance saveAccountBalance(IncomeBalance ap) {
		return dao.saveAccountBalance(ap);
	}
	
	public IncomeBalance getAccountBalance(int id) {
		return dao.getAccountBalance(id);
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
		//dao.deleteFiscalYear(fiscalYear);
		fiscalYear.setStatus(GeneralStatus.DELETED);
		fiscalYear.setUpdatedBy(Context.getAuthenticatedUser().getId());
		fiscalYear.setUpdatedDate(Calendar.getInstance().getTime());
		dao.saveFiscalYear(fiscalYear);
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
		//dao.delete(incomeReceipt);
		incomeReceipt.setStatus(GeneralStatus.DELETED);
		incomeReceipt.setVoided(true);
		incomeReceipt.setVoidedBy(Context.getAuthenticatedUser().getId());
		incomeReceipt.setVoideddDate(Calendar.getInstance().getTime());
		dao.saveIncomeReceipt(incomeReceipt);
		
		for (IncomeReceiptItem item : incomeReceipt.getReceiptItems()) {
			try {
				voidIncomeReceiptItem(item.getId());
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public IncomeReceiptItem saveIncomeReceiptItem(IncomeReceiptItem incomeReceiptItem) throws Exception {
		IncomeReceiptItem rIncomeReceiptItem;
		log.debug("Save income reeceipt item: " + incomeReceiptItem);
		if (incomeReceiptItem.getId() == null) {
			/** New Receipt **/
			incomeReceiptItem.setCreatedBy(Context.getAuthenticatedUser().getId());
			incomeReceiptItem.setCreatedDate(Calendar.getInstance().getTime());
			
			/** TransactionDate = receiptDate */
			incomeReceiptItem.setTransactionDate(incomeReceiptItem.getReceipt().getReceiptDate());
			
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
			 * When updating receipt item, there are 2 cases: Voided or Update the amount If Voided
			 * => cancelAccountTransaction for old receipt item If update amount =>
			 * cancelAccountTransaction for old receipt then addAccountTransaction for new receipt
			 * amount
			 */
			
			IncomeReceiptItem oldReceipt = dao.getIncomeReceiptItem(incomeReceiptItem.getId());
			
			AccountTransaction accTxn = null;
			
			if (incomeReceiptItem.isVoided()) {
				
				/** Cancel Account Transaction for old receipt **/
				cancelAccountTransaction(oldReceipt.getTxnNumber(), Calendar.getInstance().getTime());
				
			} else {
				
				/** Check if receipt amount were updated **/
				if (!oldReceipt.getAccount().equals(incomeReceiptItem.getAccount())) {
					
					/** Cancel Account Transaction for old receipt **/
					cancelAccountTransaction(oldReceipt.getTxnNumber(), Calendar.getInstance().getTime());
					
					/** Add transaction for new receipt amount value **/
					
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
	
	private void cancelAccountTransaction(String txnNumber, Date transactedOn) {
		AccountTransaction oldTxn = dao.getAccountTxn(txnNumber);
		if (oldTxn == null) {
			log.debug("Old Transaction = " + oldTxn);
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
	public List<IncomeBalance> findAccountBalance(Integer fiscalPeriodId) {
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
		
		IncomeBalance accBalance = dao.getAccountBalance(account, period);
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
	public IncomeBalance findAccountPeriod(Account account, Date date) {
		return dao.findAccountPeriod(account, date);
	}
	
	public void updateAccountAvailableBalance(Account account, Date receiptDate, BigDecimal amount) throws Exception {
		//		AccountBalance accBalance = dao.getLatestAccountBalance(account);
		//		if (accBalance == null) {
		//			throw new Exception("Can not find Account Balance with Account:" + account.getName() + " and Receipt Date: "
		//			        + receiptDate.toString());
		
		/*
		 * If Account Balance does not exist for this period, create new one
		 */
		FiscalPeriod period = getFiscalPeriodByDate(receiptDate);
		IncomeBalance balance = dao.getAccountBalance(account, period);
		if (balance == null) {
				balance = new IncomeBalance();
				balance.setAccount(account);
				balance.setCreatedBy(account.getCreatedBy());
				balance.setCreatedDate(account.getCreatedDate());
				balance.setStatus(BalanceStatus.ACTIVE);
				balance.setStartDate(period.getStartDate());
				balance.setEndDate(period.getEndDate());
				balance.setPeriod(period);
			//			dao.saveAccountBalance(balance);
		}
		
		balance.setAvailableBalance(balance.getAvailableBalance().add(amount));
		balance.setLedgerBalance(balance.getLedgerBalance().add(amount));
		
		balance.setUpdatedBy(Context.getAuthenticatedUser().getId());
		balance.setUpdatedDate(Calendar.getInstance().getTime());
		
		saveAccountBalance(balance);
	}
	
	private void increaseBalance(IncomeReceiptItem receipt) throws Exception {
		updateAccountAvailableBalance(receipt.getAccount(), receipt.getReceipt().getReceiptDate(), receipt.getAmount());
	}
	
	/*
	private void increaseBalance(BudgetItem budget) throws Exception {
		updateAccountAvailableBalance(budget.getAccount(), budget.getCreatedDate(), budget.getAmount());
	}
	
	
	private void decreaseBalance(BudgetItem budget) throws Exception {
		BigDecimal amount = budget.getAmount().negate();
		updateAccountAvailableBalance(budget.getAccount(), budget.getCreatedDate(), amount);
	}
	*/
	
	private void decreaseBalance(IncomeReceiptItem receipt) throws Exception {
		BigDecimal amount = receipt.getAmount().negate();
		updateAccountAvailableBalance(receipt.getAccount(), receipt.getReceipt().getReceiptDate(), amount);
	}
	/*
	private void updateAccountBalance(BudgetItem budget) throws Exception {
		updateAccountAvailableBalance(budget.getAccount(), budget.getCreatedDate(), budget.getAmount());
		addAccountTransaction(budget);
	}*/
	
	public void updateAccountBalance(Account account, Date receiptDate, BigDecimal amount, TransactionType type)
	    throws Exception {
		IncomeBalance accBalance = dao.getLatestAccountBalance(account);
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
	
	private AccountTransaction addAccountTransaction(IncomeReceiptItem receipt) {
		if (receipt == null) return null;
		return addAccountTransaction(receipt.getAccount(), receipt.getAmount(), receipt.getCreatedBy(),
		    receipt.getCreatedDate(), receipt.getTransactionDate());
	}
	
	private AccountTransaction addAccountTransaction(Payment payment){
		if (payment == null) return null;
		// since this is payment, the amount should be negative
		BigDecimal amount = payment.getActualPayment() != null ? payment.getActualPayment().negate() : new BigDecimal("0");
		return addAccountTransaction(payment.getAccount(), amount, payment.getCreatedBy(), payment.getCreatedDate(), payment.getPaymentDate());
	}
	
	private AccountTransaction cancelAccountTransaction(Payment payment){
		if (payment == null) return null;
		// since this is cancel payment, the amount should be positive
		return addAccountTransaction(payment.getAccount(), payment.getActualPayment(), payment.getUpdatedBy(), payment.getUpdatedDate(), payment.getPaymentDate());
	}
	
	private AccountTransaction addAccountTransaction(BudgetItem budget) {
		return addAccountTransaction(budget.getAccount(), budget.getAmount(), budget.getCreatedBy(),
		    budget.getCreatedDate(), budget.getCreatedDate());
	}
	
	private AccountTransaction cancelAccountTransaction(BudgetItem budget){
		if (budget == null) return null;
		return addAccountTransaction(budget.getAccount(), budget.getAmount().negate(), budget.getUpdatedBy(), budget.getUpdatedDate(), budget.getUpdatedDate());
	}
	
	private AccountTransaction addAccountTransaction(Account account, BigDecimal amount, int createdBy, Date createdDate,
	                                                 Date transactionDate) {
		if (account == null)
			return null;
		
		/*
		if (amount.compareTo(new BigDecimal("0")) == 0 ) {
			return null;
		}
		*/
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
		
		return dao.saveAccountTransaction(newTxn);
		
	}
	
	@Override
	public void voidIncomeReceiptItem(Integer id) throws Exception {
		log.debug("Begin void IncomeReceiptItem id = " + id);
		if (id == null) {
			log.error("Can not find Income Receipt Item with id = " + id);
			throw new Exception("Can not find Income Receipt Item with id = " + id);
		}
		IncomeReceiptItem item = dao.getIncomeReceiptItem(id);
		if (item == null) {
			log.error("Can not find Income Receipt Item with id = " + id);
			throw new Exception("Can not find Income Receipt Item with id = " + id);
		}
		Date curDate = Calendar.getInstance().getTime();
		item.setVoided(true);
		item.setVoidedBy(Context.getAuthenticatedUser().getId());
		item.setVoideddDate(curDate);
		dao.saveIncomeReceiptItem(item);
		
		AccountTransaction acctxn = dao.getAccountTxn(item.getTxnNumber());
		
		if (acctxn == null) {
			log.error("Can not find AccountTransaction of Income Receipt Item with id = " + id);
			throw new Exception("Can not find AccountTransaction of Income Receipt Item with id = " + id);
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
	public boolean isOverlapFiscalYear(Integer fiscalYearId, String from, String to) {
		Date dFrom = DateUtils.getDateFromStr(from);
		Date dTo = DateUtils.getDateFromStr(to);
		return isOverlapFiscalYear(fiscalYearId, dFrom, dTo);
		
	}
	
	@Override
	public boolean isOverlapFiscalYear(Integer fiscalYearId, Date from, Date to) {
		List<FiscalYear> list = dao.getOverlapFiscalYears(fiscalYearId, from, to);
		if (list != null && !list.isEmpty()) {
			for (FiscalYear year : list) {
				if (year.getId().equals(fiscalYearId) && year.getStartDate().compareTo(from) == 0
				        && year.getEndDate().compareTo(to) == 0) {
					continue;
				} else {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public FiscalYear getActiveFiscalYear() {
		return dao.getActiveFicalYear();
	}
	
	@Override
	public Budget saveBudget(Budget budget) throws Exception {
		
		if (budget.getId() == null) {
			
			budget.setCreatedBy(Context.getAuthenticatedUser().getId());
			budget.setCreatedDate(Calendar.getInstance().getTime());
			
			budget = dao.saveBudget(budget);
			/**
			 * Add new Budget => need to update account balance for each Budget Item Balance should
			 * be increased if (budget.getBudgetItems() != null &&
			 * !budget.getBudgetItems().isEmpty()) { for (BudgetItem item : budget.getBudgetItems())
			 * { AccountTransaction acctxn = addAccountTransaction(item);
			 * item.setTxnNumber(acctxn.getTxnNumber()); increaseBalance(item); } }
			 */
			
			
			
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
			
			for (BudgetItem item : budget.getBudgetItems()) {
				item.setRetired(true);
				item.setRetiredBy(Context.getAuthenticatedUser().getId());
				item.setRetiredDate(curDate);
				dao.saveBudgetItem(item);
				/*
				cancelAccountTransaction(item.getTxnNumber(),curDate );
				
				increaseBalance(item);
				*/
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
		
		if (!Context.getService(AccountingService.class).isEditableBudget(item) ) {
    		throw new Exception(" Can not edit this Budget because there are Payments linked");
    	} 
		
		
		Date curDate = Calendar.getInstance().getTime();
		item.setRetired(true);
		item.setRetiredBy(Context.getAuthenticatedUser().getId());
		item.setRetiredDate(curDate);
		
		//cancelAccountTransaction(item.getTxnNumber(),curDate );
		
		//decreaseBalance(item);
		saveBudgetItem(item);
	}
	
	@Override
	public BudgetItem saveBudgetItem(BudgetItem item) throws Exception {
		
		if (item == null) {
			return null;
		}
		Date curDate = Calendar.getInstance().getTime();
		
		FiscalPeriod period = dao.getPeriodByDate(item.getStartDate());
		
		if (period == null) {
			throw new Exception("Can not find period for date " + item.getStartDate());
		}
		
		ExpenseBalance balance = dao.getExpenseBalanceByAccountAndPeriod(item.getAccount(), period);
		if (balance == null) {
			
			/** Create new Expense Balance record if it does not exist for selected period **/
			
			balance = new ExpenseBalance();
			Account acc = item.getAccount();
			balance.setAccount(acc);
			balance.setCreatedBy(acc.getCreatedBy());
			balance.setCreatedDate(curDate);
			balance.setStatus(BalanceStatus.ACTIVE);
			balance.setStartDate(period.getStartDate());
			balance.setEndDate(period.getEndDate());
			balance.setPeriod(period);
		}
		
		if (item.getId() != null) {
			
			/** Update Buget Item **/
			
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
				// Retire
				persitedItem.setRetired(true);
				persitedItem.setRetiredBy(Context.getAuthenticatedUser().getId());
				persitedItem.setRetiredDate(curDate);
				
				/* 
				 * Subtract the AIE from cummulative AIE
				 * Subtract the AIE amount from available balance
				 */
				balance.setNewAIE(new BigDecimal("0"));
				balance.setCummulativeAIE(balance.getCummulativeAIE().subtract(persitedItem.getAmount()));
				balance.setAvailableBalance(balance.getAvailableBalance().subtract(persitedItem.getAmount()));
				cancelAccountTransaction(item);
			} else {
				// Update Budget Item
				persitedItem.setUpdatedBy(Context.getAuthenticatedUser().getId());
				persitedItem.setUpdatedDate(Calendar.getInstance().getTime());
				
				/*
				 * - Subtract the old AIE from cummulative AIE amount 
				 * - Add add the new AIE to cummulative AIE amount
				 */
				balance.setNewAIE(item.getAmount());
				balance.setCummulativeAIE(balance.getCummulativeAIE().subtract(persitedItem.getAmount()));
				balance.setCummulativeAIE(balance.getCummulativeAIE().add(item.getAmount()));
				
				cancelAccountTransaction(persitedItem);
				
				balance.setAvailableBalance(balance.getAvailableBalance().subtract(persitedItem.getAmount()));
				balance.setAvailableBalance(balance.getAvailableBalance().add(item.getAmount()));
				
				addAccountTransaction(item);
			}
			
			dao.saveExpenseBalance(balance);
			
			return dao.saveBudgetItem(persitedItem);
		} else {
			
			/** Create new Buget Item **/
			
			item.setCreatedBy(Context.getAuthenticatedUser().getId());
			item.setCreatedDate(curDate);
			
			item = dao.saveBudgetItem(item);
			
			balance.setNewAIE(item.getAmount());
			balance.setCummulativeAIE(balance.getCummulativeAIE().add(item.getAmount()));
			balance.setAvailableBalance(item.getAmount());
			balance.setLedgerBalance(item.getAmount());
			
			dao.saveExpenseBalance(balance);
			
			addAccountTransaction(item);
			
			
			return item;
		}
		
	}
	
	@Override
	public List<FiscalPeriod> getCurrentYearPeriods() {
		FiscalYear year = getActiveFiscalYear();
		if (year != null) {
			return year.getPeriods();
		} else {
			return null;
		}
	}
	
	@Override
	public Payee savePayee(Payee payee) {
		if (payee.getId() == null) {
			payee.setCreatedBy(Context.getAuthenticatedUser().getId());
			payee.setCreatedDate(Calendar.getInstance().getTime());
		} else {
			payee.setUpdatedBy(Context.getAuthenticatedUser().getId());
			payee.setUpdatedDate(Calendar.getInstance().getTime());
		}
		return dao.savePayee(payee);
	}
	
	@Override
	public void deletePayee(Integer id) {
		Payee payee = dao.getPayee(id);
		if (payee != null) {
			dao.deletePayee(payee);
			;
		}
	}
	
	@Override
	public Payee getPayee(Integer id) {
		return dao.getPayee(id);
	}
	
	@Override
	public List<Payee> listActivePayees() {
		return dao.listPayees(false);
	}
	
	@Override
	public List<Payee> listAllPayees() {
		return dao.listPayees(true);
	}
	
	@Override
	public Payment savePayment(Payment payment) throws Exception {
		
		if (payment.getId() == null) {
			payment.setCreatedBy(Context.getAuthenticatedUser().getId());
			payment.setCreatedDate(Calendar.getInstance().getTime());
		} else {
			payment.setUpdatedBy(Context.getAuthenticatedUser().getId());
			payment.setUpdatedDate(Calendar.getInstance().getTime());
		}
		
		
		
		payment = updateExpenseBalance(payment);
		
		return dao.savePayment(payment);
	}
	
	/**
	 * Update balance of expense account after add new payment
	 * 
	 * @param payment
	 * @throws Exception 
	 */
	
	private Payment updateExpenseBalance(Payment payment) throws Exception {
		
		FiscalPeriod period = dao.getPeriodByDate(payment.getPaymentDate());
		ExpenseBalance balance = null;
		if (period != null) {
			balance = dao.getExpenseBalanceByAccountAndPeriod(payment.getAccount(), period);
			if (balance == null) {
				//			balance = dao.getLatestExpenseBalance(payment.getAccount());
				Account acc = payment.getAccount();
				ExpenseBalance accBalance = new ExpenseBalance();
				accBalance.setAccount(acc);
				accBalance.setCreatedBy(acc.getCreatedBy());
				accBalance.setCreatedDate(acc.getCreatedDate());
				accBalance.setStatus(BalanceStatus.ACTIVE);
				accBalance.setStartDate(period.getStartDate());
				accBalance.setEndDate(period.getEndDate());
				accBalance.setPeriod(period);
				dao.saveExpenseBalance(accBalance);
			}
		}
		
		if (balance == null) {
			// There should always at least one account balance exist for an account!
			log.error("Expense Balance is null for account id : " + payment.getAccount().getId());
			throw new Exception("Expense Balance is null for account id : " + payment.getAccount().getId());
		}
		
		Payment persitedPayment = dao.getPayment(payment.getId());
		
		
		if (payment.getStatus().equals(PaymentStatus.COMMITTED)) {
			// add to total committted, then subtract the ledger balance 
			if (persitedPayment != null && persitedPayment.getStatus().equals(payment.getStatus()) 
					&& persitedPayment.getCommitmentAmount().compareTo(payment.getCommitmentAmount()) != 0) {
				// change commited amount
				// revert previous committed amount
				balance.setTotalCommitted(balance.getTotalCommitted().subtract(persitedPayment.getCommitmentAmount()));
				BigDecimal availableBalance = balance.getAvailableBalance().add(persitedPayment.getCommitmentAmount());
				balance.setAvailableBalance(availableBalance);
//				addAccountTransaction(payment);
				
			}
			
			if ( persitedPayment == null || persitedPayment.getStatus().equals(payment.getStatus()) 
					&& persitedPayment.getCommitmentAmount().compareTo(payment.getCommitmentAmount()) != 0 
					|| !persitedPayment.getStatus().equals(payment.getStatus()) ) {
				// update new committed amount
				balance.setTotalCommitted(balance.getTotalCommitted().add(payment.getCommitmentAmount()));
				BigDecimal availableBalance = balance.getAvailableBalance().subtract(payment.getCommitmentAmount());
				balance.setAvailableBalance(availableBalance);
			} 
			
		} else if (payment.getStatus().equals(PaymentStatus.PAID)) {
			
			if (persitedPayment != null && persitedPayment.getStatus().equals(payment.getStatus()) 
					&& persitedPayment.getActualPayment().compareTo(payment.getActualPayment()) != 0) {
				// Here user change the actual payment amount, we need to cancel the old amount first. then add the new amount
				// revert change
				balance.setCurrentPayment(balance.getCurrentPayment().subtract(persitedPayment.getActualPayment()));
				balance.setCummulativePayment(balance.getCummulativePayment().subtract(persitedPayment.getActualPayment()));
				BigDecimal ledgerBalance = balance.getLedgerBalance().add(persitedPayment.getActualPayment());
				balance.setLedgerBalance(ledgerBalance);
				
				//cancel account transaction
				AccountTransaction atxn = dao.getAccountTxn(persitedPayment.getTxnNumber());
				if ( atxn != null) {
					cancelAccountTransaction(atxn.getTxnNumber(), payment.getCreatedDate());
				}
			} 
			
			
			if ( persitedPayment == null 
					|| (persitedPayment.getStatus().equals(payment.getStatus()) && persitedPayment.getActualPayment().compareTo(payment.getActualPayment()) != 0 ) 
					|| !persitedPayment.getStatus().equals(payment.getStatus())) {
				// payment is made
				// add  payment amount  , then subtract available balance 
				balance.setCurrentPayment(balance.getCurrentPayment().add(payment.getActualPayment()));
				balance.setCummulativePayment(balance.getCummulativePayment().add(payment.getActualPayment()));
				BigDecimal ledgerBalance = balance.getCummulativeAIE().subtract(balance.getCummulativePayment());
				balance.setLedgerBalance(ledgerBalance);
				
				if (persitedPayment == null) {
					// Paid without committed -> have to update the availableBalance too
					balance.setAvailableBalance(ledgerBalance);
				}
				
				// add account transaction
				AccountTransaction atxn = addAccountTransaction(payment);
				if (atxn != null) {
					payment.setTxnNumber(atxn.getTxnNumber());
				}
				
				// reset commitment amount
				if ( persitedPayment != null  && payment.getCommitmentAmount() != null && payment.getCommitmentAmount().compareTo(new BigDecimal("0")) != 0) {
					balance.setTotalCommitted(balance.getTotalCommitted().subtract(persitedPayment.getCommitmentAmount()));
				}
				
			}
			
		}
		
		balance.setUpdatedBy(Context.getAuthenticatedUser().getId());
		
		balance.setUpdatedDate(Calendar.getInstance().getTime());
		
		dao.saveExpenseBalance(balance);
		return payment;
	}
	
	
	
	
	@Override
	public void deletePayment(Payment payment) {
		dao.deletePayment(payment);
	}
	
	@Override
	public Payment getPayment(Integer id) {
		return dao.getPayment(id);
	}
	
	@Override
	public List<Account> listAccount(AccountType accType, boolean includeDisabled) {
		return dao.getAccounts(accType, includeDisabled);
	}
	
	@Override
	public BudgetItem getBudgetItem(Integer accountId, Date date) {
		Account account = dao.getAccount(accountId);
		if (account != null) {
			return dao.getBudgetItem(date, account);
			
		} else {
			return null;
		}
		
	}
	
	@Override
	public boolean isBudgetItemOverlap(Integer accountId, Date startDate, Date endDate) {
		if (accountId != null) {
			Account account = dao.getAccount(accountId);
			if (account != null) {
				return dao.isBudgetItemOverlap(account, startDate, endDate);
			}
		}
		return true;
	}
	
	@Override
	public Budget getBudgetByName(String name) {
		return dao.getBudgetByName(name);
	}
	
	/**
	 * Recursive function to get all Concepts for billing
	 */
	
	private void getListConcepts(Set<Integer> conceptIds, Concept concept) {
		if (concept != null) {
			ConceptClass conceptClass = concept.getConceptClass();
			if (conceptClass.getName().equalsIgnoreCase("Test") || conceptClass.getName().equalsIgnoreCase("labset")
			        || conceptClass.getName().equalsIgnoreCase("Procedure")) {
				conceptIds.add(concept.getId());
			} else {
				Collection<ConceptAnswer> answers = concept.getAnswers();
				if (answers != null && answers.size() > 0) {
					for (ConceptAnswer answer : answers) {
						Concept c = answer.getAnswerConcept();
						getListConcepts(conceptIds, c);
					}
				}
			}
			
		}
	}
	
	@Override
	public void aggregateIncomeReceipt(Date date) throws Exception {
		Collection<Account> accounts = getAccounts(false);
		if (accounts != null && accounts.size() > 0) {
			
			// Check if the receipt for the same existed
			IncomeReceipt receipt = dao.getIncomeReceiptByReceiptNo(AccountingConstants.INCOME_RECEIPT_NO_PREFIX
			        + DateUtils.getStringFromDate(date));
			if (receipt == null) {
				receipt = new IncomeReceipt();
				receipt.setCreatedBy(1);
				receipt.setCreatedDate(date);
				receipt.setReceiptDate(date);
				receipt.setVoided(false);
				receipt.setStatus(GeneralStatus.ACTIVE);
				receipt.setReceiptNo(AccountingConstants.INCOME_RECEIPT_NO_PREFIX + DateUtils.getStringFromDate(date));
				receipt.setDescription("Income receipt auto generated");
				receipt = saveIncomeReceipt(receipt);
			} else {
				receipt.setUpdatedBy(1);
				receipt.setUpdatedDate(date);
			}
			boolean saveNewItem = false;
			
			// Calculate the total amount for each account
			for (Account acc : accounts) {
				if (acc.getConceptId() != null) {
					
					IncomeReceiptItem item = dao.getIncomeReceiptItemByAccountAndReceipt(acc, receipt);
					
					if (item != null) {
						// Receipt item for this account has been created -> continue with other accounts
						continue;
					}
					
					Concept concept = Context.getService(ConceptService.class).getConcept(acc.getConceptId());
					Set<Integer> conceptIds = new HashSet<Integer>();
					getListConcepts(conceptIds, concept);
					System.out.println("****************************************** get bill for account : " + acc.getName());
					System.out.println(conceptIds);
					BigDecimal amount = dao.getAggregateBill(conceptIds, date);
					System.out.println(amount);
					
					/*
					 * Create Income Receipt (CASH) for each account 
					 */
					item = new IncomeReceiptItem();
					
					item.setVoided(false);
					item.setAccount(acc);
					item.setAmount(amount == null ? new BigDecimal("0") : amount);
					item.setType(IncomeReceiptType.CASH);
					item.setCreatedDate(receipt.getCreatedDate());
					item.setCreatedBy(receipt.getCreatedBy());
					item.setTransactionDate(receipt.getReceiptDate());
					item.setReceipt(receipt);
					
					saveIncomeReceiptItem(item);
					saveNewItem = true;
				}
			}// end for loop 
			
			// Create income receipt for waive and voided 
			
			if (saveNewItem) {
				// Update receipt
				receipt = saveIncomeReceipt(receipt);
			}
		}
	}
	
	@Override
	public FiscalYear closeFiscalYear(Integer id) {
		FiscalYear year = dao.getFiscalYear(id);
		if (year == null) {
			return null;
		}
		log.debug("close fiscal year: "+year);
		year.setStatus(GeneralStatus.CLOSED);
		year.setUpdatedBy(Context.getAuthenticatedUser().getId());
		year.setUpdatedDate(Calendar.getInstance().getTime());
		return dao.saveFiscalYear(year);
	}
	
	public FiscalYear closeFiscalYear(Integer closeYearId, Integer nextYearId) {
		
		if (closeYearId == null) return null;
		
		FiscalYear closeYear = closeFiscalYear(closeYearId);
		Date curDate = Calendar.getInstance().getTime();
		
//		int countPeriods = closeYear.getPeriods().size();
		
//		if (createNewYear) {
//			//TODO create new fiscal year
//			FiscalYear newYear = new FiscalYear();
//			newYear.setCreatedBy(Context.getAuthenticatedUser().getId());
//			newYear.setCreatedDate(curDate);
//			
//		//	newYear = generatePeriods(newYear, countPeriods);
//			
//			dao.saveFiscalYear(newYear);
//			
//		} else {
			// TODO set next year active
			FiscalYear nextYear = dao.getFiscalYear(nextYearId);
			if (nextYear != null) {
				nextYear.setStatus(GeneralStatus.OPEN);
			}
			dao.saveFiscalYear(nextYear);
//		}
		
		return null;
	}
	
	
	@Override
	public FiscalPeriod closePeriod(Integer curPeriodId, Integer nextPeriodId, boolean resetBalance) {
		Date curDate = Calendar.getInstance().getTime();
		FiscalPeriod curPeriod = dao.getFiscalPeriod(curPeriodId);
		FiscalPeriod nextPeriod = null;
		if (nextPeriodId != null) {
			nextPeriod = dao.getFiscalPeriod(nextPeriodId);
		}
		if (curPeriod == null) {
			return null;
		}
		
		/*
		 * Close old account balance and create new one
		 */
		
		List<IncomeBalance> balances = dao.listAccountBalance(BalanceStatus.ACTIVE, curPeriod);
		if (balances != null && balances.size() > 0) {
			for (IncomeBalance balance : balances) {
				balance.setStatus(BalanceStatus.CLOSED);
				balance.setStartDate(curPeriod.getStartDate());
				balance.setEndDate(curPeriod.getEndDate());
				balance.setUpdatedBy(Context.getAuthenticatedUser().getId());
				balance.setUpdatedDate(curDate);
				balance.setClosingBalance(balance.getAvailableBalance());
				balance.setPeriod(curPeriod);
				balance = dao.saveAccountBalance(balance);
				if (nextPeriod != null) {
					/*
					 * This is not the last period 
					 */
					IncomeBalance newBalance = new IncomeBalance();
					newBalance.setPeriod(nextPeriod);
					newBalance.setCreatedBy(Context.getAuthenticatedUser().getId());
					newBalance.setCreatedDate(curDate);
					newBalance.setStartDate(nextPeriod.getStartDate());
					newBalance.setEndDate(nextPeriod.getEndDate());
					newBalance.setAccount(balance.getAccount());
					newBalance.setStatus(BalanceStatus.ACTIVE);
					if (!resetBalance) {
						newBalance.setAvailableBalance(balance.getClosingBalance());
						newBalance.setLedgerBalance(balance.getClosingBalance());
						newBalance.setOpeningBalance(balance.getClosingBalance());
					}
					dao.saveAccountBalance(newBalance);
				}
			}
		}
		
		List<ExpenseBalance> expBalances = listActiveExpenseBalance(curPeriod);
		if (expBalances != null && expBalances.size() > 0) {
			for (ExpenseBalance balance : expBalances) {
				balance.setStatus(BalanceStatus.CLOSED);
				balance.setUpdatedBy(Context.getAuthenticatedUser().getId());
				balance.setUpdatedDate(curDate);
				dao.saveExpenseBalance(balance);
				
				if (nextPeriod != null) {
					/*
					 * This is not the last period 
					 */
					ExpenseBalance newBalance = new ExpenseBalance();
					newBalance.setCreatedBy(Context.getAuthenticatedUser().getId());
					newBalance.setCreatedDate(curDate);
					newBalance.setPeriod(nextPeriod);
					newBalance.setStartDate(nextPeriod.getStartDate());
					newBalance.setEndDate(nextPeriod.getEndDate());
					newBalance.setAvailableBalance(balance.getAvailableBalance());
					newBalance.setCummulativeAIE(balance.getCummulativeAIE());
					newBalance.setCummulativePayment(balance.getCummulativePayment());
					newBalance.setAccount(balance.getAccount());
					newBalance.setStatus(BalanceStatus.ACTIVE);
					
					dao.saveExpenseBalance(newBalance);
				}
				
			}
		}
		curPeriod.setStatus(GeneralStatus.CLOSED);
		curPeriod.setUpdatedBy(Context.getAuthenticatedUser().getId());
		curPeriod.setUpdatedDate(curDate);
		return dao.saveFiscalPeriod(curPeriod);
	}
	
	@Override
	public List<IncomeBalance> listActiveAccountBalance() {
		return dao.listAccountBalance(BalanceStatus.ACTIVE);
	}
	
	@Override
	public List<ExpenseBalance> listActiveExpenseBalance() {
		return dao.listExpenseBalance(BalanceStatus.ACTIVE, null);
	}
	
	@Override
	public List<IncomeBalance> listActiveAccountBalance(FiscalPeriod period) {
		return dao.listAccountBalance(BalanceStatus.ACTIVE, period);
	}
	
	@Override
	public List<ExpenseBalance> listActiveExpenseBalance(FiscalPeriod period) {
		return dao.listExpenseBalance( period);
	}
	
	@Override
	public int countListPaymentsByAccount(Account account) {
		if (account != null) {
			return dao.countListPaymentsByAccount(account);
		} else {
			return 0;
		}
		
	}
	
	@Override
	public List<Payment> listPaymentsByAccount(Account account, Integer min, Integer max) {
		if (account != null) {
			return dao.listPaymentsByAccount(account, min, max);
		} else {
			return null;
		}
	}
	
	@Override
	public int countAllPayments() {
		return dao.countListPayments();
	}
	
	@Override
	public List<Payment> listAllPayments(int min, int max) {
		return dao.listPayments(min, max);
	}
	
	@Override
	public Account getAccountByAccountNumber(String accNo) {
		if (StringUtils.isNotBlank(accNo)) {
			return dao.getAccountByAccountNumber(accNo);
		} else {
			return null;
		}
	}
	
	@Override
	public Account getAccountByNameAndType(String name, AccountType type) {
		return dao.getAccountByNameAndType(name, type);
	}
	
	@Override
	public FiscalPeriod getFiscalPeriodByDate(Date date) {
		return dao.getPeriodByDate(date);
	}
	
	@Override
	public FiscalPeriod getFiscalPeriodByDate(String date) {
		Date d = DateUtils.getDateFromStr(date);
		return dao.getPeriodByDate(d);
	}
	
	@Override
	public IncomeReceipt getIncomeReceiptByReceiptNo(String receiptNo) {
		if (StringUtils.isNotBlank(receiptNo)) {
			return dao.getIncomeReceiptByReceiptNo(receiptNo);
		} else
			return null;
	}
	
	@Override
	public List<IncomeBalance> listActiveAccountBalanceByPeriodId(Integer periodId) {
		if (periodId == null) {
			return null;
		} else {
			FiscalPeriod period = dao.getFiscalPeriod(periodId);
			return listActiveAccountBalance(period);
		}
	}
	
	@Override
	public List<ExpenseBalance> listActiveExpenseBalanceByPeriodId(Integer periodId) {
		if (periodId == null) {
			return null;
		} else {
			FiscalPeriod period = dao.getFiscalPeriod(periodId);
			return listActiveExpenseBalance(period);
		}
	}
	
	@Override
    public Boolean isAllPeriodClosed(Integer fiscalYearId) {
		FiscalYear year = getFiscalYear(fiscalYearId);
		if (year == null) return null;
		
		List<FiscalPeriod> periods = year.getPeriods();
		if (periods != null) {
			for (FiscalPeriod period : periods) {
				if (!period.isClosed()) {
					return false;
				}
			}
			return true;
		}
		return null;
    }

	@Override
    public Collection<FiscalYear> getListFutureYear(Date startDate) {
	    return dao.getListFutureYear(startDate);
    }

	@Override
    public List<IncomeReceiptItem> getListIncomeReceiptItemByAccount(int accountId) {
	    Account account = dao.getAccount(accountId);
	    if ( account != null ) {
	    	return dao.getListIncomeReceiptItemByAccount(account);
	    } 
		return null;
    }

	@Override
    public int countListIncomeReceipt(boolean includeVoided) {
	    return dao.countListIncomeReceipt(includeVoided);
    }

	@Override
    public List<IncomeReceipt> getListIncomeReceipt(boolean includeVoided, int start, int end) {
	    return dao.getListIncomeReceipt(includeVoided, start, end);
    }

	@Override
    public List<Budget> getListBudgets(boolean includeRetired, int min, int max) {
	    return dao.getListBudgets(includeRetired, min, max);
    }

	@Override
    public int countListBudgets(boolean includeRetired) {
	    return dao.countListBudgets(includeRetired);
    }

	@Override
    public BankStatement saveBankStatement(BankStatement bs) {
		bs.setCreatedBy(Context.getAuthenticatedUser().getId());
		bs.setCreatedDate(Calendar.getInstance().getTime());
	    return dao.saveBankStatement(bs);
    }

	@Override
    public void deleteBankStatement(Integer id) {
	    dao.deleteBankStatement(id);
    }

	@Override
    public BankStatement getBankStatement(Integer id) {
	    return dao.getBankStatement(id);
    }

	@Override
    public List<BankStatement> getListBankStatements() {
	    return dao.getListBankStatements();
    }

	@Override
    public BankAccount saveBankAccount(BankAccount bankAccount) {
		bankAccount.setCreatedBy(Context.getAuthenticatedUser().getId());
		bankAccount.setCreatedDate(Calendar.getInstance().getTime());
	    return dao.saveBankAccount(bankAccount);
    }

	@Override
    public void deleteBankAccount(Integer id) {
		dao.deleteBankAccount(id);
    }

	@Override
    public BankAccount getBankAccount(Integer id) {
	    return dao.getBankAccount(id);
    }

	@Override
    public List<BankAccount> getListBankAccounts() {
	    return dao.getListBankAccounts();
    }

	@Override
    public ExpenseBalance findExpenseBalance(Integer accountId, Date date) {
		Account acc = dao.getAccount(accountId);
		if (acc != null) {
			return dao.findExpenseBalance(acc, date);
		}
	    return null;
    }
	
	/**
	 * 
	 * @param budgetItem
	 * @return true if there is no payment made in the same period of this budget item
	 */
	public boolean isEditableBudget(BudgetItem budgetItem) {
		List<Payment> payments = dao.listPaymentsByAccountPeriod(budgetItem.getAccount(), budgetItem.getStartDate(), budgetItem.getEndDate());
		if (payments != null && !payments.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
    public BudgetItem getBudgetItem(Integer id) {
	    return dao.getBudgetItem(id);
    }

}
