/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.accounting.api.db;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.AccountPeriod;
import org.openmrs.module.accounting.api.model.FiscalPeriod;
import org.openmrs.module.accounting.api.model.FiscalYear;
import org.openmrs.module.accounting.api.model.GeneralStatus;
import org.openmrs.module.accounting.api.model.IncomeReceipt;
import org.openmrs.module.accounting.api.model.IncomeReceiptItem;
import org.openmrs.module.hospitalcore.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * It is a default implementation of {@link AccountingDAO}.
 */
@Repository
public class AccountingDAO {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	SessionFactory sessionFactory;
	
	/**
	 * ACCOUNT
	 */
	@SuppressWarnings("unchecked")
    public Collection<Account> getAccounts(boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Account.class);
		if (!includeRetired)
			criteria.add(Restrictions.eq("retired", false));
		return criteria.list();
		
	}
	
	public Account getAccount(int id) {
		
		return (Account) sessionFactory.getCurrentSession().get(Account.class, id);
	}
	
	public Account saveAccount(Account account) throws DAOException {
			return (Account) sessionFactory.getCurrentSession().merge(account);
		}
	
	public void deleteAccount(Account account) {
		 sessionFactory.getCurrentSession().delete(account);;
	}
	
	@SuppressWarnings("unchecked")
    public Collection<Account> getListParrentAccount(){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Account.class);
		criteria.add(Restrictions.isNull("parentAccountId"));
		return criteria.list() ;
		
	}
	
	public Account getAccountByName(String name){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Account.class);
		criteria.add(Restrictions.eq("name", name));
		return  (Account) criteria.uniqueResult(); 
	}
	
	/**
	 * FISCAL YEAR
	 */
	
	public void deleteFiscalYear(FiscalYear fiscalYear) {
		 sessionFactory.getCurrentSession().delete(fiscalYear);;
	}
	
	public void deleteFiscalPeriod(FiscalPeriod fiscalPeriod){
		 sessionFactory.getCurrentSession().delete(fiscalPeriod);;
	}
	
	public FiscalYear saveFiscalYear(FiscalYear fy) throws DAOException{
		return (FiscalYear) sessionFactory.getCurrentSession().merge(fy);
	}
	
	public FiscalYear getFiscalYear(int id) {
		return (FiscalYear) sessionFactory.getCurrentSession().get(FiscalYear.class, id);
	}
	
	public FiscalYear getFiscalYearByName(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FiscalYear.class);
		criteria.add(Restrictions.eq("name", name));
		return  (FiscalYear) criteria.uniqueResult(); 
    }

    @SuppressWarnings("unchecked")
    public Collection<FiscalYear> getListFiscalYear(GeneralStatus status) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FiscalYear.class);
		if (status != null)
			criteria.add(Restrictions.eq("status", status));
		return criteria.list();
    }

	/**
	 * FISCAL PERIOD
	 */
	
	public FiscalPeriod saveFiscalPeriod(FiscalPeriod fp) throws DAOException {
		return (FiscalPeriod) sessionFactory.getCurrentSession().merge(fp);
	}
	
	public FiscalPeriod getFiscalPeriod(int id) {
		return (FiscalPeriod) sessionFactory.getCurrentSession().get(FiscalPeriod.class, id);
	}
	
	public AccountPeriod saveAccountPeriod(AccountPeriod ap){
		sessionFactory.getCurrentSession().saveOrUpdate(ap);
		return ap;
	}
	
	public AccountPeriod getAccountPeriod(int id){
		return (AccountPeriod) sessionFactory.getCurrentSession().get(AccountPeriod.class, id);
	}
	
	
    /**
     * INCOME RECEIPT
     */
	
    public IncomeReceipt saveIncomeReceipt(IncomeReceipt incomeReceipt)  throws DAOException{
		return (IncomeReceipt) sessionFactory.getCurrentSession().merge(incomeReceipt);
    }

	
    public IncomeReceipt getIncomeReceipt(Integer id) {
    	return (IncomeReceipt) sessionFactory.getCurrentSession().get(IncomeReceipt.class, id);
    }

	
    @SuppressWarnings("unchecked")
    public List<IncomeReceipt> getListIncomeReceipt(boolean includeVoided) {
    	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IncomeReceipt.class);
    	if (!includeVoided)
    		criteria.add(Restrictions.eq("voided", false));
    	return criteria.list();
    }

	
    @SuppressWarnings("unchecked")
    public List<IncomeReceipt> getListIncomeReceiptByDate(String startDate, String endDate, boolean includeVoided) {
    	Date dStartDate = DateUtils.getDateFromStr(startDate);
    	Date dEndDate = DateUtils.getDateFromStr(endDate);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(dEndDate);
    	cal.add(Calendar.DATE, 1);
    	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IncomeReceipt.class);
    	System.out.println("startDate "+dStartDate);
    	System.out.println("endDate "+cal.getTime());
    	
    	criteria.add(Restrictions.and(Restrictions.ge("receiptDate", dStartDate), Restrictions.lt("receiptDate", cal.getTime())));
    	
    	if (!includeVoided) criteria.add(Restrictions.eq("voided", false));
    		
    	
    	return criteria.list();
    	
    }

	
    public void delete(IncomeReceipt incomeReceipt) {
    	sessionFactory.getCurrentSession().delete(incomeReceipt);
    }

    /**
     * INCOME RECEIPT ITEM
     */
    
    public IncomeReceiptItem saveIncomeReceiptItem(IncomeReceiptItem incomeReceiptItem)  throws DAOException{
		return (IncomeReceiptItem) sessionFactory.getCurrentSession().merge(incomeReceiptItem);
    }

	
    public IncomeReceiptItem getIncomeReceiptItem(Integer id) {
    	return (IncomeReceiptItem) sessionFactory.getCurrentSession().get(IncomeReceiptItem.class, id);
    }

	
    @SuppressWarnings("unchecked")
    public List<IncomeReceiptItem> getListIncomeReceiptItem(boolean includeVoided) {
    	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IncomeReceiptItem.class);
    	criteria.add(Restrictions.eq("voided", includeVoided));
    	return criteria.list();
    }

	
    @SuppressWarnings("unchecked")
    public List<IncomeReceiptItem> getListIncomeReceiptItemByDate(String startDate, String endDate) {
    	Date dStartDate = DateUtils.getDateFromStr(startDate);
    	Date dEndDate = DateUtils.getDateFromStr(endDate);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(dEndDate);
    	cal.add(Calendar.DATE, 1);
    	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IncomeReceiptItem.class);
    	criteria.add(Restrictions.and(Restrictions.le("receiptDate", dStartDate), Restrictions.lt("receiptDate", cal.getTime())));
    	return criteria.list();
    }

	
    public void delete(IncomeReceiptItem incomeReceiptItem) {
    	sessionFactory.getCurrentSession().delete(incomeReceiptItem);
    }

	
    @SuppressWarnings("unchecked")
    public List<IncomeReceiptItem> getListIncomeReceiptItemByAccount(Account acc) {
    	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IncomeReceiptItem.class);
    	criteria.add(Restrictions.eq("account", acc));
    	return criteria.list();
    }
}
