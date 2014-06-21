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
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.DAOException;
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
import org.openmrs.module.accounting.api.utils.DateUtils;
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
	
	@SuppressWarnings("unchecked")
	public List<Budget> getBudgets(boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Budget.class);
		if (!includeRetired)
			criteria.add(Restrictions.eq("retired", false));
		return criteria.list();
		
	}
	
	public Budget getBudget(int id) {
		return (Budget) sessionFactory.getCurrentSession().get(Budget.class, id);
	}
	
	public Account getAccount(int id) {
		
		return (Account) sessionFactory.getCurrentSession().get(Account.class, id);
	}
	
	public BudgetItem getBudgetItem(int id) {
		
		return (BudgetItem) sessionFactory.getCurrentSession().get(BudgetItem.class, id);
	}
	
	public Account saveAccount(Account account) throws DAOException {
		return (Account) sessionFactory.getCurrentSession().merge(account);
	}
	
	public Budget saveBudget(Budget budget) {
		return (Budget) sessionFactory.getCurrentSession().merge(budget);
	}
	
	public BudgetItem saveBudgetItem(BudgetItem item) {
		return (BudgetItem) sessionFactory.getCurrentSession().merge(item);
	}
	
	
	public void deleteBudget(Budget budget) {
		sessionFactory.getCurrentSession().delete(budget);
	}
	
	public void deleteBudgetItem(BudgetItem item) {
		sessionFactory.getCurrentSession().delete(item);
	}
	
	public void deleteAccount(Account account) {
		sessionFactory.getCurrentSession().delete(account);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Account> getListParrentAccount() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Account.class);
		criteria.add(Restrictions.isNull("parentAccountId"));
		return criteria.list();
		
	}
	
	public Account getAccountByName(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Account.class);
		criteria.add(Restrictions.eq("name", name));
		return (Account) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<AccountBalance> findAccountPeriods(FiscalPeriod period) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccountBalance.class);
		criteria.add(Restrictions.eq("fiscalPeriod", period));
		return criteria.list();
	}
	
	//TODO add fromData-toDate
	public AccountBalance findAccountPeriod(Account account, Date date) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccountBalance.class);
		criteria.add(Restrictions.eq("account", account));
		criteria.add(Restrictions.and(Restrictions.ge("startDate", date), Restrictions.le("endDate", date)));
		return (AccountBalance) criteria.uniqueResult();
	}
	
	public AccountBalance getLatestAccountBalance(Account acc) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccountBalance.class);
		criteria.add(Restrictions.eq("account", acc));
		criteria.add(Restrictions.eq("status", BalanceStatus.ACTIVE));
		criteria.setMaxResults(1);
		return (AccountBalance) criteria.uniqueResult();
	}
	
	public AccountTransaction saveAccountTransaction(AccountTransaction accTxn) {
		return (AccountTransaction) sessionFactory.getCurrentSession().merge(accTxn);
	}
	
	@SuppressWarnings("unchecked")
	public List<AccountTransaction> findAccountTransaction(Account acc, String fromDate, String toDate, String status) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccountTransaction.class);
		criteria.add(Restrictions.eq("account", acc))
		        .add(Restrictions.ge("transactionDate", DateUtils.getDateFromStr(fromDate)))
		        .add(Restrictions.le("transactionDate", DateUtils.addDate(DateUtils.getDateFromStr(toDate), 1)));
		return criteria.list();
	}
	
	public AccountTransaction getLatestTransaction(Account acc) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccountTransaction.class);
		criteria.add(Restrictions.eq("account", acc)).addOrder(Order.desc("transactionDate")).setMaxResults(1);
		return (AccountTransaction) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<AccountBalance> listAccountBalance(BalanceStatus status) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccountBalance.class);
		if (status != null) {
			criteria.add(Restrictions.eq("status", status));
		}
		return criteria.list();
	}
	
	
	public AccountTransaction getAccountTxn(String transactionNo) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccountTransaction.class);
		criteria.add(Restrictions.eq("txnNumber", transactionNo));
		return (AccountTransaction) criteria.uniqueResult();
	}
	/**
	 * FISCAL YEAR
	 */
	
	public void deleteFiscalYear(FiscalYear fiscalYear) {
		sessionFactory.getCurrentSession().delete(fiscalYear);
		;
	}
	
	public void deleteFiscalPeriod(FiscalPeriod fiscalPeriod) {
		sessionFactory.getCurrentSession().delete(fiscalPeriod);
		;
	}
	
	public FiscalYear saveFiscalYear(FiscalYear fy) throws DAOException {
		return (FiscalYear) sessionFactory.getCurrentSession().merge(fy);
	}
	
	public FiscalYear getFiscalYear(int id) {
		return (FiscalYear) sessionFactory.getCurrentSession().get(FiscalYear.class, id);
	}
	
	public FiscalYear getFiscalYearByName(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FiscalYear.class);
		criteria.add(Restrictions.eq("name", name));
		return (FiscalYear) criteria.uniqueResult();
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
	
	public AccountBalance saveAccountBalance(AccountBalance ap) {
		sessionFactory.getCurrentSession().saveOrUpdate(ap);
		return ap;
	}
	
	public AccountBalance getAccountPeriod(int id) {
		return (AccountBalance) sessionFactory.getCurrentSession().get(AccountBalance.class, id);
	}
	
	public AccountBalance getAccountPeriod(Account account, FiscalPeriod period) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccountBalance.class);
		criteria.add(Restrictions.eq("account", account));
		criteria.add(Restrictions.eq("fiscalPeriod", period));
		return (AccountBalance) criteria.uniqueResult();
	}
	
	public FiscalPeriod getPeriodByDate(Date date) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FiscalPeriod.class);
		criteria.add(Restrictions.and(Restrictions.ge("startDate", date), Restrictions.le("endDate", date)));
		return (FiscalPeriod) criteria.uniqueResult();
	}
	
	/**
	 * INCOME RECEIPT
	 */
	
	public IncomeReceipt saveIncomeReceipt(IncomeReceipt incomeReceipt) throws DAOException {
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
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IncomeReceipt.class);
		
		criteria.add(Restrictions.and(Restrictions.ge("receiptDate", dStartDate),
		    Restrictions.lt("receiptDate", DateUtils.addDate(dEndDate, 1))));
		
		if (!includeVoided)
			criteria.add(Restrictions.eq("voided", false));
		
		return criteria.list();
		
	}
	
	public void delete(IncomeReceipt incomeReceipt) {
		sessionFactory.getCurrentSession().delete(incomeReceipt);
	}
	
	/**
	 * INCOME RECEIPT ITEM
	 */
	
	public IncomeReceiptItem saveIncomeReceiptItem(IncomeReceiptItem incomeReceiptItem) throws DAOException {
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
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IncomeReceiptItem.class);
		criteria.add(Restrictions.and(Restrictions.le("receiptDate", dStartDate),
		    						  Restrictions.lt("receiptDate", DateUtils.addDate(dEndDate, 1))));
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
	
	/**
	 * Check if given date range is overlap with existing fiscal years
	 * @param from
	 * @param to
	 * @return
	 */
	public boolean isOverlapFiscalYear(Integer id, Date from, Date to) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FiscalYear.class);
		criteria.add(Restrictions.and(Restrictions.lt("startDate", to), Restrictions.gt("endDate",from)));
		criteria.add(Restrictions.ne("status", GeneralStatus.INACTIVE));
		if (id != null) {
			criteria.add(Restrictions.ne("id", id));
		}
		return criteria.list().isEmpty() ? false: true;
		
	}
	
	public FiscalYear getActiveFicalYear() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FiscalYear.class);
		criteria.add(Restrictions.eq("status", GeneralStatus.ACTIVE));
		return (FiscalYear) criteria.uniqueResult();
	}
	
	
}
