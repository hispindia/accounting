package org.openmrs.module.accounting.api;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.AccountType;
import org.openmrs.module.accounting.api.model.BankAccount;
import org.openmrs.module.accounting.api.model.BankStatement;
import org.openmrs.module.accounting.api.model.Budget;
import org.openmrs.module.accounting.api.model.BudgetItem;
import org.openmrs.module.accounting.api.model.ExpenseBalance;
import org.openmrs.module.accounting.api.model.FiscalPeriod;
import org.openmrs.module.accounting.api.model.FiscalYear;
import org.openmrs.module.accounting.api.model.GeneralStatus;
import org.openmrs.module.accounting.api.model.IncomeBalance;
import org.openmrs.module.accounting.api.model.IncomeReceipt;
import org.openmrs.module.accounting.api.model.IncomeReceiptItem;
import org.openmrs.module.accounting.api.model.Payee;
import org.openmrs.module.accounting.api.model.Payment;
import org.openmrs.module.accounting.api.utils.AccountingConstants;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This service exposes module's core functionality. It is a Spring managed bean which is configured
 * in moduleApplicationContext.xml.
 * <p>
 * It can be accessed only via Context:<br>
 * <code>
 * Context.getService(AccountingService.class).someMethod();
 * </code>
 * 
 * @see org.openmrs.api.context.Context
 */
@Transactional
public interface AccountingService extends OpenmrsService {
	
	
	/**
	 * 	Account
	 */
	@Authorized({ AccountingConstants.PRIV_ADD_EDIT_ACCOUNT })
	public Account saveAccount(Account acc, Integer periodId);
	
	@Authorized({ AccountingConstants.PRIV_DELETE_ACCOUNT })
	public void deleteAccount(Account acc);
	
	@Transactional(readOnly = true)
	@Authorized({ AccountingConstants.PRIV_VIEW_ACCOUNT })
	public Collection<Account> getAccounts(boolean includeDisabled);
	
	@Transactional(readOnly = true)
	@Authorized({ AccountingConstants.PRIV_VIEW_ACCOUNT })
	public List<Account> listAccount(AccountType accType, boolean includeDisabled);
	
	@Authorized({ AccountingConstants.PRIV_VIEW_ACCOUNT })
	public Account getAccount(int id);
	
	@Transactional(readOnly = true)
	@Authorized({ AccountingConstants.PRIV_VIEW_ACCOUNT })
	public Account getAccountByName(String name);
	
	@Transactional(readOnly = true)
	@Authorized({ AccountingConstants.PRIV_VIEW_ACCOUNT })
	public Account getAccountByNameAndType(String name, AccountType type);
	
	@Transactional(readOnly = true)
	public Collection<Account> getListParrentAccount();
	
	@Transactional(readOnly = true)
	public List<IncomeBalance> findAccountBalance(Integer fiscalPeriodId);
	
	@Transactional(readOnly = true)
	public List<IncomeBalance> listActiveAccountBalance();
	
	@Transactional(readOnly = true)
	public List<IncomeBalance> listActiveAccountBalance(FiscalPeriod period);
	
	@Transactional(readOnly = true)
	public List<IncomeBalance> listActiveAccountBalanceByPeriodId(Integer periodId);
	
	@Transactional(readOnly = true)
	public Account getAccountByAccountNumber(String accNo);
	
	
	/**
	 * 
	 * @param account
	 * @param date
	 * @return should return only one account because there shouldnt be  overlap periods
	 */
	public IncomeBalance findAccountPeriod(Account account, Date date) ;
	
	/**
	 * Fiscal Year 
	 */
	public FiscalYear saveFiscalYear(FiscalYear fy) ;
	
	@Transactional(readOnly = true)
	public FiscalYear getFiscalYear(int id) ;
	
	@Transactional(readOnly = true)
	public FiscalYear getFiscalYearByName(String name);
	
	public FiscalPeriod saveFiscalPeriod(FiscalPeriod fp) ;
	
	@Transactional(readOnly = true)
	public FiscalPeriod getFiscalPeriod(int id) ;
	
	@Transactional(readOnly = true)
	public Collection<FiscalYear> getListFiscalYear(GeneralStatus status);
	
	@Transactional(readOnly = true)
	public Collection<FiscalYear> getListFutureYear(Date startDate);
	
	public void deleteFiscalYear(FiscalYear fiscalYear);
	
	@Transactional(readOnly = true)
	public boolean isOverlapFiscalYear(Integer fiscalYearId, String from, String to);
	
	@Transactional(readOnly = true)
	public boolean isOverlapFiscalYear(Integer fiscalYearId, Date from, Date to);
	
	@Transactional(readOnly = true)
	public FiscalYear getActiveFiscalYear();
	
	@Transactional(readOnly = true)
	public List<FiscalPeriod> getCurrentYearPeriods();
	
	public FiscalYear closeFiscalYear(Integer id);
	
	public FiscalYear closeFiscalYear(Integer closeYear, Integer nextYear) ;
	
	@Transactional(readOnly = true)
	public FiscalPeriod getFiscalPeriodByDate(Date date);
	
	@Transactional(readOnly = true)
	public FiscalPeriod getFiscalPeriodByDate(String date);
	
	@Transactional(readOnly = true)
	public Boolean isAllPeriodClosed(Integer fiscalYearId);
	
	
	
	/**
	 * 	Period
	 */
	public IncomeBalance saveAccountBalance(IncomeBalance ap);
	
	@Transactional(readOnly = true)
	public IncomeBalance getAccountBalance(int id);
	
	public void deletePeriod(FiscalPeriod period);
	
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public FiscalPeriod closePeriod(Integer curPeriodId, Integer nextPeriodId, boolean resetBalance);
	
	/**
	 * Module initialize
	 */
	public void initModule();
	
	/**
	 * INCOME RECEIPT
	 */
	
	public IncomeReceipt saveIncomeReceipt(IncomeReceipt incomeReceipt);
	
	public IncomeReceipt getIncomeReceipt(Integer id);
	
	public List<IncomeReceipt> getListIncomeReceipt(boolean includeVoided);
	
	public List<IncomeReceipt> getListIncomeReceipt(boolean includeVoided, int start, int end);
	
	public int countListIncomeReceipt(boolean includeVoided);
	
	public List<IncomeReceipt> getListIncomeReceiptByDate(String startDate, String endDate, boolean includeVoided);
	
	public void delete(IncomeReceipt incomeReceipt);
	
	public IncomeReceipt getIncomeReceiptByReceiptNo(String receiptNo);
	
	/**
	 * INCOME RECEIPT ITEM
	 * @throws Exception 
	 */
	
	public IncomeReceiptItem saveIncomeReceiptItem(IncomeReceiptItem incomeReceiptItem) throws Exception;
	
	public IncomeReceiptItem getIncomeReceiptItem(Integer id);
	
	public List<IncomeReceiptItem> getListIncomeReceiptItem(boolean includeVoided);
	
	public List<IncomeReceiptItem> getListIncomeReceiptItemByDate(String startDate, String endDate);
	
	public void delete(IncomeReceiptItem incomeReceiptItem);
	
	public List<IncomeReceiptItem> getListIncomeReceiptItemByAccount(Account acc);
	
	public List<IncomeReceiptItem> getListIncomeReceiptItemByAccount(int  accId);
	
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public void voidIncomeReceiptItem(Integer id) throws Exception;
	
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public void aggregateIncomeReceipt(Date curDate) throws Exception;
	
	/**
	 * BUDGET
	 */
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public Budget saveBudget(Budget budget) throws Exception;
	
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public BudgetItem saveBudgetItem(BudgetItem item) throws Exception;
	
	public Budget getBudget(Integer id);
	
	public Budget getBudgetByName(String name);
	
	public List<Budget> getBudgets(Boolean includeRetired);
	
	public List<Budget> getListBudgets(boolean includeRetired, int min, int max);
	
	public int countListBudgets(boolean includeRetired);
	
	public void deleteBudget(Budget budget);
	
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public void retireBudget(Integer id) throws Exception;
	
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public void retireBudgetItem(Integer id) throws Exception;
	
	public BudgetItem getBudgetItem(Integer accountId, Date date);
	
	public BudgetItem getBudgetItem(Integer id);
	
	public boolean isBudgetItemOverlap(Integer accountId, Date startDate, Date endDate) ;
	
	public boolean isEditableBudget(BudgetItem budgetItem) ;
	/**
	 * PAYEE
	 */
	
	public Payee savePayee(Payee payee);
	
	public void deletePayee(Integer id);
	
	public Payee getPayee(Integer id) ;
	
	public List<Payee> listActivePayees();
	
	public List<Payee> listAllPayees();
	
	/**
	 * PAYMENT 
	 * @throws Exception 
	 */
	
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.REQUIRED)
	public Payment savePayment(Payment payment) throws Exception;
	
	public void deletePayment(Payment payment);
	
	public Payment getPayment(Integer id);
	
	public int countAllPayments();
	
	public int countListPaymentsByAccount(Account account);
	
	public List<Payment> listPaymentsByAccount(Account account, Integer min, Integer max);
	
	public List<Payment> listAllPayments(int min, int max);
	
	public List<ExpenseBalance> listActiveExpenseBalance();
	
	public List<ExpenseBalance> listActiveExpenseBalance(FiscalPeriod period);
	
	public List<ExpenseBalance> listActiveExpenseBalanceByPeriodId(Integer periodId);
	
	public ExpenseBalance findExpenseBalance(Integer accountId, Date date);
	

	/**
	 * Bank Statement
	 * 
	 */
	
	public BankStatement saveBankStatement(BankStatement bankStatement);
	
	public void deleteBankStatement(Integer id);
	
	public BankStatement getBankStatement(Integer id) ;
	
	public List<BankStatement> getListBankStatements(); 
	
	/**
	 * Bank Account
	 */
	
	public BankAccount saveBankAccount(BankAccount bankAccount);
	
	public void deleteBankAccount(Integer id);
	
	public BankAccount getBankAccount(Integer id) ;
	
	public List<BankAccount> getListBankAccounts(); 
}
