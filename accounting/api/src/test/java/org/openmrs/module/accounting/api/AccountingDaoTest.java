package org.openmrs.module.accounting.api;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.db.AccountingDAO;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.AccountBalance;
import org.openmrs.module.accounting.api.model.BalanceStatus;
import org.openmrs.module.accounting.api.model.AccountType;
import org.openmrs.module.accounting.api.model.FiscalPeriod;
import org.openmrs.module.accounting.api.model.FiscalYear;
import org.openmrs.module.accounting.api.model.GeneralStatus;
import org.openmrs.module.accounting.api.model.IncomeReceipt;
import org.openmrs.module.hospitalcore.util.DateUtils;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountingDaoTest extends BaseModuleContextSensitiveTest {
	
	
	
	
	@Autowired
	AccountingDAO dao;
	
	@Test
	public void shouldSaveAccount() throws Exception {
		Account acc = new Account("Account 1");
		acc.setAccountType(AccountType.B); 
		acc.setCreatedDate(Calendar.getInstance().getTime());
		acc = dao.saveAccount(acc);
		Context.flushSession();
		Context.clearSession();
		
		Account persitedAccount = dao.getAccount(acc.getId());
		Assert.assertNotNull(persitedAccount);
		
	}
	
	@Test
	public void shouldSaveFiscalYear() throws Exception {
		FiscalYear fy = new FiscalYear("Fiscal Year 1",Calendar.getInstance().getTime(),1);
		fy = dao.saveFiscalYear(fy);
		Context.flushSession();
		Context.clearSession();
		
		FiscalYear persitedFiscalYear = dao.getFiscalYear(fy.getId());
		Assert.assertNotNull(persitedFiscalYear);
	}
	
	@Test
	public void shouldSaveFiscalPeriod() throws Exception {
		FiscalYear fy = new FiscalYear("Fiscal Year 1",Calendar.getInstance().getTime(),1);
		fy = dao.saveFiscalYear(fy);
		Context.flushSession();
		Context.clearSession();
		
		FiscalYear persitedFiscalYear = dao.getFiscalYear(fy.getId());
		Assert.assertNotNull(persitedFiscalYear);
		
		FiscalPeriod fp = new FiscalPeriod("Q1", Calendar.getInstance().getTime(), 1);
		fp.setFiscalYear(fy);
		fp = dao.saveFiscalPeriod(fp);
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
		acc.setAccountType(AccountType.B);
		acc.setCreatedDate(Calendar.getInstance().getTime());
		
		acc = dao.saveAccount(acc);
		Context.flushSession();
		Context.clearSession();
		
		Account persitedAccount = dao.getAccount(acc.getId());
		Assert.assertNotNull(persitedAccount);
		
		/**
		 * Create Fiscal Year
		 */
		FiscalYear fy = new FiscalYear("Fiscal Year 1",Calendar.getInstance().getTime(),1);
		fy = dao.saveFiscalYear(fy);
		Context.flushSession();
		Context.clearSession();
		
		FiscalYear persitedFiscalYear = dao.getFiscalYear(fy.getId());
		Assert.assertNotNull(persitedFiscalYear);
		
		
		/**
		 * Create FiscalPeriod
		 */
		FiscalPeriod fp = new FiscalPeriod("Q1", Calendar.getInstance().getTime(), 1);
		fp.setFiscalYear(fy);
		fp = dao.saveFiscalPeriod(fp);
		Context.flushSession();
		Context.clearSession();
		FiscalPeriod persitedFiscalPeriod = dao.getFiscalPeriod(fp.getId());
		Assert.assertNotNull(persitedFiscalPeriod);
		
		/**
		 * Create Account Period
		 */
		AccountBalance ap = new AccountBalance();
		ap.setAccount(acc);
		ap.setCreatedBy(1);
		ap.setCreatedDate(Calendar.getInstance().getTime());
		ap.setPeriod(fp);
		ap.setStatus(BalanceStatus.ACTIVE);
		ap = dao.saveAccountBalance(ap);
		
		Context.flushSession();
		Context.clearSession();
		
		Assert.assertNotNull(ap);
		AccountBalance persitedAP = dao.getAccountPeriod(ap.getId());
		Assert.assertNotNull(persitedAP);
		
	}
	
	@Test
	public void shouldGetIncomeReceipt() throws Exception{
		
		Date curDate = Calendar.getInstance().getTime();
		
		IncomeReceipt receipt = new IncomeReceipt();
		receipt.setCreatedDate(curDate);
		receipt.setCreatedBy(1);
		receipt.setDescription("testing");
		receipt.setReceiptDate(DateUtils.getDateFromStr("21/05/2014"));
		receipt = dao.saveIncomeReceipt(receipt);
		Context.flushSession();
		Context.clearSession();
		
		IncomeReceipt persitedReceipt = dao.getIncomeReceipt(receipt.getId());
		Assert.assertNotNull(persitedReceipt);
		
		IncomeReceipt receipt2 = new IncomeReceipt();
		receipt2.setCreatedDate(curDate);
		receipt2.setCreatedBy(1);
		receipt2.setDescription("testing");
		receipt2.setReceiptDate(DateUtils.getDateFromStr("22/05/2014"));
		receipt2 = dao.saveIncomeReceipt(receipt2);
		
		Context.flushSession();
		Context.clearSession();
		
		IncomeReceipt persitedReceipt2 = dao.getIncomeReceipt(receipt2.getId());
		Assert.assertNotNull(persitedReceipt2);
		
		List<IncomeReceipt> list = dao.getListIncomeReceiptByDate("21/05/2014", "22/05/2014", true);
		List<IncomeReceipt> list2 = dao.getListIncomeReceipt(true);
		Assert.assertNotNull(list);
		Assert.assertNotNull(list2);
		Assert.assertEquals(2,list.size());
		Assert.assertEquals(2,list2.size());
		
	}
	
}
