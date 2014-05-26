package org.openmrs.module.accounting.api;

import java.util.Collection;
import java.util.List;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.AccountPeriod;
import org.openmrs.module.accounting.api.model.FiscalPeriod;
import org.openmrs.module.accounting.api.model.FiscalYear;
import org.openmrs.module.accounting.api.model.GeneralStatus;
import org.openmrs.module.accounting.api.model.IncomeReceipt;
import org.openmrs.module.accounting.api.model.IncomeReceiptItem;
import org.openmrs.module.accounting.api.utils.AccountingConstants;
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
	public Account saveAccount(Account acc);
	
	@Authorized({ AccountingConstants.PRIV_DELETE_ACCOUNT })
	public void deleteAccount(Account acc);
	
	@Transactional(readOnly = true)
	@Authorized({ AccountingConstants.PRIV_VIEW_ACCOUNT })
	public Collection<Account> getAccounts(Boolean includeDisabled);
	
	@Authorized({ AccountingConstants.PRIV_VIEW_ACCOUNT })
	public Account getAccount(int id);
	
	@Transactional(readOnly = true)
	@Authorized({ AccountingConstants.PRIV_VIEW_ACCOUNT })
	public Account getAccountByName(String name);
	
	@Transactional(readOnly = true)
	public Collection<Account> getListParrentAccount();
	
	
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
	
	public void deleteFiscalYear(FiscalYear fiscalYear);

	/**
	 * 	Period
	 */
	public AccountPeriod saveAccountPeriod(AccountPeriod ap);
	
	@Transactional(readOnly = true)
	public AccountPeriod getAccountPeriod(int id);
	
	public void deletePeriod(FiscalPeriod period);
	
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
	
	public List<IncomeReceipt> getListIncomeReceiptByDate(String startDate, String endDate, boolean includeVoided);
	
	public void delete(IncomeReceipt incomeReceipt);
	
	
	/**
	 * INCOME RECEIPT ITEM
	 */
	
	public IncomeReceiptItem saveIncomeReceiptItem(IncomeReceiptItem incomeReceiptItem);
	
	public IncomeReceiptItem getIncomeReceiptItem(Integer id);
	
	public List<IncomeReceiptItem> getListIncomeReceiptItem(boolean includeVoided);
	
	public List<IncomeReceiptItem> getListIncomeReceiptItemByDate(String startDate, String endDate);
	
	public void delete(IncomeReceiptItem incomeReceiptItem);
	
	public List<IncomeReceiptItem> getListIncomeReceiptItemByAccount(Account acc);
	
}
