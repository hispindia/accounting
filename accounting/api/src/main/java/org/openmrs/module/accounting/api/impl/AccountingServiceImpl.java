package org.openmrs.module.accounting.api.impl;

import java.util.Calendar;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.db.AccountingDAO;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.AccountPeriod;
import org.openmrs.module.accounting.api.model.FiscalPeriod;
import org.openmrs.module.accounting.api.model.FiscalYear;
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
		if (acc.getId() == 0) {
			log.info("Create new account: " + acc.getName());
			acc.setCreatedDate(Calendar.getInstance().getTime());
			acc.setCreatedBy(Context.getAuthenticatedUser().getId());
		} else {
			log.info("update  account: " + acc.getName());
			
			acc.setUpdatedBy(Context.getAuthenticatedUser().getId());
			acc.setUpdatedDate(Calendar.getInstance().getTime());
		}
		return dao.saveAccount(acc);
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
		return dao.saveFiscalYear(fy);
	}
	
	public FiscalYear getFiscalYear(int id) {
		return dao.getFiscalYear(id);
	}
	
	public FiscalPeriod saveFiscalPeriod(FiscalPeriod fp) {
		return dao.saveFiscalPeriod(fp);
	}
	
	public FiscalPeriod getFiscalPeriod(int id) {
		return dao.getFiscalPeriod(id);
	}
	
	public AccountPeriod saveAccountPeriod(AccountPeriod ap) {
		return dao.saveAccountPeriod(ap);
	}
	
	public AccountPeriod getAccountPeriod(int id) {
		return dao.getAccountPeriod(id);
	}
	
	public Collection<Account> getListParrentAccount() {
		return dao.getListParrentAccount();
	}

    public Account getAccountByName(String name) {
	    return dao.getAccountByName(name);
    }
}
