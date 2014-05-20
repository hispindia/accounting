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

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.AccountPeriod;
import org.openmrs.module.accounting.api.model.FiscalPeriod;
import org.openmrs.module.accounting.api.model.FiscalYear;
import org.openmrs.module.accounting.api.model.GeneralStatus;
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
	
	
	public void deleteFiscalYear(FiscalYear fiscalYear) {
		 sessionFactory.getCurrentSession().delete(fiscalYear);;
	}
	
	public void deleteAccount(Account account) {
		 sessionFactory.getCurrentSession().delete(account);;
	}
	
	public void deleteFiscalPeriod(FiscalPeriod fiscalPeriod){
		 sessionFactory.getCurrentSession().delete(fiscalPeriod);;
	}
	
	
	@SuppressWarnings("unchecked")
    public Collection<Account> getAccounts(Boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Account.class);
		if (includeRetired != null)
			criteria.add(Restrictions.eq("retired", includeRetired));
		return criteria.list();
		
	}
	
	public Account getAccount(int id) {
		
		return (Account) sessionFactory.getCurrentSession().load(Account.class, id);
	}
	
	public Account saveAccount(Account account) {
		sessionFactory.getCurrentSession().saveOrUpdate(account);
		return account;
	}
	
	public FiscalYear saveFiscalYear(FiscalYear fy) {
		sessionFactory.getCurrentSession().saveOrUpdate(fy);
		return fy;
	}
	
	public FiscalYear getFiscalYear(int id) {
		return (FiscalYear) sessionFactory.getCurrentSession().load(FiscalYear.class, id);
	}
	
	public FiscalPeriod saveFiscalPeriod(FiscalPeriod fp) {
		sessionFactory.getCurrentSession().saveOrUpdate(fp);
		return fp;
	}
	
	public FiscalPeriod getFiscalPeriod(int id) {
		return (FiscalPeriod) sessionFactory.getCurrentSession().load(FiscalPeriod.class, id);
	}
	
	public AccountPeriod saveAccountPeriod(AccountPeriod ap){
		sessionFactory.getCurrentSession().saveOrUpdate(ap);
		return ap;
	}
	
	public AccountPeriod getAccountPeriod(int id){
		return (AccountPeriod) sessionFactory.getCurrentSession().load(AccountPeriod.class, id);
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
}
