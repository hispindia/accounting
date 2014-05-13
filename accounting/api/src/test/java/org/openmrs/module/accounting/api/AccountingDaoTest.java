package org.openmrs.module.accounting.api;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.db.AccountingDAO;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.AccountPeriod;
import org.openmrs.module.accounting.api.model.FiscalPeriod;
import org.openmrs.module.accounting.api.model.FiscalYear;
import org.openmrs.module.accounting.api.model.GeneralStatus;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountingDaoTest extends BaseModuleContextSensitiveTest {
	
	
	@Override
	public Boolean useInMemoryDatabase(){
		return false;
	}
	
	@Before
    public void Authenticate()
    {
        Context.authenticate("admin", "Hisp@1234");    
    }
	
	@Autowired
	AccountingDAO dao;
	
	@Test
	public void shouldSaveAccount() throws Exception {
		Account acc = new Account("Account 1");
		dao.saveAccount(acc);
		Context.flushSession();
		Context.clearSession();
		
		Account persitedAccount = dao.getAccount(acc.getId());
		Assert.assertNotNull(persitedAccount);
		
	}
	
	@Test
	public void shouldSaveFiscalYear() throws Exception {
		FiscalYear fy = new FiscalYear("Fiscal Year 1",Calendar.getInstance().getTime(),1);
		dao.saveFiscalYear(fy);
		Context.flushSession();
		Context.clearSession();
		
		FiscalYear persitedFiscalYear = dao.getFiscalYear(fy.getId());
		Assert.assertNotNull(persitedFiscalYear);
	}
	
	@Test
	public void shouldSaveFiscalPeriod() throws Exception {
		FiscalYear fy = new FiscalYear("Fiscal Year 1",Calendar.getInstance().getTime(),1);
		dao.saveFiscalYear(fy);
		Context.flushSession();
		Context.clearSession();
		
		FiscalYear persitedFiscalYear = dao.getFiscalYear(fy.getId());
		Assert.assertNotNull(persitedFiscalYear);
		
		FiscalPeriod fp = new FiscalPeriod("Q1", Calendar.getInstance().getTime(), 1);
		fp.setFiscalYear(fy);
		dao.saveFiscalPeriod(fp);
		Context.flushSession();
		Context.clearSession();
		FiscalPeriod persitedFiscalPeriod = dao.getFiscalPeriod(fp.getId());
		Assert.assertNotNull(persitedFiscalPeriod);
	}
	
	@Test 
	public void shouldSaveAccountPeriod() throws Exception{
		/**
		 * Create Account
		 */
		Account acc = new Account("Account 1");
		dao.saveAccount(acc);
		Context.flushSession();
		Context.clearSession();
		
		Account persitedAccount = dao.getAccount(acc.getId());
		Assert.assertNotNull(persitedAccount);
		
		/**
		 * Create Fiscal Year
		 */
		FiscalYear fy = new FiscalYear("Fiscal Year 1",Calendar.getInstance().getTime(),1);
		dao.saveFiscalYear(fy);
		Context.flushSession();
		Context.clearSession();
		
		FiscalYear persitedFiscalYear = dao.getFiscalYear(fy.getId());
		Assert.assertNotNull(persitedFiscalYear);
		
		
		/**
		 * Create FiscalPeriod
		 */
		FiscalPeriod fp = new FiscalPeriod("Q1", Calendar.getInstance().getTime(), 1);
		fp.setFiscalYear(fy);
		dao.saveFiscalPeriod(fp);
		Context.flushSession();
		Context.clearSession();
		FiscalPeriod persitedFiscalPeriod = dao.getFiscalPeriod(fp.getId());
		Assert.assertNotNull(persitedFiscalPeriod);
		
		/**
		 * Create Account Period
		 */
		AccountPeriod ap = new AccountPeriod();
		ap.setAccount(acc);
		ap.setCreatedBy(1);
		ap.setCreatedDate(Calendar.getInstance().getTime());
		ap.setPeriod(fp);
		ap.setStatus(GeneralStatus.A);
		dao.saveAccountPeriod(ap);
		
		Context.flushSession();
		Context.clearSession();
		
		Assert.assertNotNull(ap);
		AccountPeriod persitedAP = dao.getAccountPeriod(ap.getId());
		Assert.assertNotNull(persitedAP);
		
	}
	
	
}
