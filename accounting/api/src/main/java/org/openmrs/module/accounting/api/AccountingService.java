package org.openmrs.module.accounting.api;

import java.util.Collection;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.AccountPeriod;
import org.openmrs.module.accounting.api.model.FiscalPeriod;
import org.openmrs.module.accounting.api.model.FiscalYear;
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
	
	@Authorized({ AccountingConstants.PRIV_ADD_EDIT_ACCOUNT })
	public Account saveAccount(Account acc);
	
	@Authorized({ AccountingConstants.PRIV_DELETE_ACCOUNT })
	public void deleteAccount(Account acc);
	
	@Transactional(readOnly = true)
	@Authorized({ AccountingConstants.PRIV_VIEW_ACCOUNT })
	public Collection<Account> getAccounts(boolean includeDisabled);
	
	@Authorized({ AccountingConstants.PRIV_VIEW_ACCOUNT })
	public Account getAccount(int id);
	
	public FiscalYear saveFiscalYear(FiscalYear fy) ;
	
	@Transactional(readOnly = true)
	public FiscalYear getFiscalYear(int id) ;
	
	
	public FiscalPeriod saveFiscalPeriod(FiscalPeriod fp) ;
	
	@Transactional(readOnly = true)
	public FiscalPeriod getFiscalPeriod(int id) ;
	
	
	public AccountPeriod saveAccountPeriod(AccountPeriod ap);
	
	@Transactional(readOnly = true)
	public AccountPeriod getAccountPeriod(int id);
	
	@Transactional(readOnly = true)
	public Collection<Account> getListParrentAccount();
	
}
