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

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.AccountTransaction;
import org.openmrs.module.accounting.api.model.AccountType;
import org.openmrs.module.accounting.api.model.BalanceStatus;
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
		criteria.addOrder(Order.desc("createdDate"));
		return criteria.list();
		
	}
	
	public Account getAccountByAccountNumber(String accNo){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Account.class);
		criteria.add(Restrictions.eq("accountNumber", accNo));
		return (Account) criteria.uniqueResult();
	}
	
	public List<Account> getAccounts(AccountType accountType, boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Account.class);
		if (!includeRetired)
			criteria.add(Restrictions.eq("retired", false));
		criteria.add(Restrictions.eq("accountType", accountType));
		return criteria.list();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Budget> getBudgets(boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Budget.class);
		if (!includeRetired)
			criteria.add(Restrictions.eq("retired", false));
		return criteria.list();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Budget> getListBudgets(boolean includeRetired, int min, int max) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Budget.class);
		if (!includeRetired)
			criteria.add(Restrictions.eq("retired", false));
		
		criteria.setFirstResult(min).setMaxResults(max);
		criteria.addOrder(Order.desc("endDate"));
		return criteria.list();
	}

	public int countListBudgets(boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Budget.class);
		Number rs = (Number) criteria.setProjection(Projections.rowCount())
				.uniqueResult();
		return rs != null ? rs.intValue() : 0;
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
	
	public Budget getBudgetByName(String name){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Budget.class);
		criteria.add(Restrictions.eq("name", name));
		return (Budget) criteria.uniqueResult();
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
	
	public Account getAccountByNameAndType(String name, AccountType type) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Account.class);
		criteria.add(Restrictions.eq("name", name));
		criteria.add(Restrictions.eq("accountType", type));
		criteria.add(Restrictions.eq("retired", false));
		return (Account) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<IncomeBalance> findAccountPeriods(FiscalPeriod period) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IncomeBalance.class);
		criteria.add(Restrictions.eq("fiscalPeriod", period));
		return criteria.list();
	}
	
	//TODO add fromData-toDate
	public IncomeBalance findAccountPeriod(Account account, Date date) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IncomeBalance.class);
		criteria.add(Restrictions.eq("account", account));
		criteria.add(Restrictions.and(Restrictions.le("startDate", date), Restrictions.ge("endDate", date)));
		return (IncomeBalance) criteria.uniqueResult();
	}
	
	public ExpenseBalance findExpenseBalance(Account account, Date date) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ExpenseBalance.class);
		criteria.add(Restrictions.eq("account", account));
		criteria.add(Restrictions.and(Restrictions.le("startDate", date), Restrictions.ge("endDate", date)));
		return (ExpenseBalance) criteria.uniqueResult();
	}
	
	/**
	 * return the only and only one active account balance of this account
	 * @param acc
	 * @return
	 */
	public IncomeBalance getLatestAccountBalance(Account acc) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IncomeBalance.class);
		criteria.add(Restrictions.eq("account", acc));
		criteria.add(Restrictions.eq("status", BalanceStatus.ACTIVE));
		return (IncomeBalance) criteria.uniqueResult();
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
	public List<IncomeBalance> listAccountBalance(BalanceStatus status) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IncomeBalance.class);
		if (status != null) {
			criteria.add(Restrictions.eq("status", status));
		}
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<IncomeBalance> listAccountBalance(BalanceStatus status, FiscalPeriod period) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IncomeBalance.class);
		if (status != null) {
			criteria.add(Restrictions.eq("status", status));
		}
		if (period != null) {
			criteria.add(Restrictions.eq("period", period));
		}
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ExpenseBalance> listExpenseBalance(BalanceStatus status, FiscalPeriod period) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ExpenseBalance.class);
	
		if (status != null) {
			criteria.add(Restrictions.eq("status", status));
		}
		
		if (period != null) {
			criteria.add(Restrictions.eq("period", period));
		}
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ExpenseBalance> listExpenseBalance(FiscalPeriod period) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ExpenseBalance.class);
	
		if (period != null) {
			criteria.add(Restrictions.eq("period", period));
		}
		
		return criteria.list();
	}
	
	
	public AccountTransaction getAccountTxn(String transactionNo) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AccountTransaction.class);
		criteria.add(Restrictions.eq("txnNumber", transactionNo));
		return (AccountTransaction) criteria.uniqueResult();
	}
	/*
	public List<AccountBudgetWrapper> getAccountBudget(Date date, Account account) {
		String str =  "select b.account.id, a.name, b.amount  from   BudgetItem as b   where a = b.account and a = :account  ";
		Query query = sessionFactory.getCurrentSession().createQuery(str);
		query.setParameter("account", account);
//		query.setParameter("date", date);
		List<Object[]>  rows = query.list();
		List<AccountBudgetWrapper> result = new ArrayList<AccountBudgetWrapper>();
		for (Object[] row : rows) {
			AccountBudgetWrapper ojb = new AccountBudgetWrapper();
			ojb.setId(NumberUtils.toInt(row[0].toString()));
			ojb.setName(row[1].toString());
			ojb.setAmount(new BigDecimal(row[2].toString()));
			System.out.println("############"+ojb);
			
		    result.add(ojb);
		}
		return result;
	}
	*/
	
	public BudgetItem getBudgetItem(Date date, Account account) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BudgetItem.class);
		criteria.add(Restrictions.eq("account", account));
		criteria.add(Restrictions.and(Restrictions.le("startDate", date), Restrictions.ge("endDate", date)));
		return (BudgetItem) criteria.uniqueResult();
	}
	
	public List<BudgetItem> getBudgetItem(Date date) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BudgetItem.class);
		criteria.add(Restrictions.and(Restrictions.le("startDate", date), Restrictions.ge("endDate", date)));
		return criteria.list();
	}
	
	/**
	 * FISCAL YEAR
	 */
	
	public void deleteFiscalYear(FiscalYear fiscalYear) {
		sessionFactory.getCurrentSession().delete(fiscalYear);
	}
	
	public void deleteFiscalPeriod(FiscalPeriod fiscalPeriod) {
		sessionFactory.getCurrentSession().delete(fiscalPeriod);
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
		criteria.add(Restrictions.ne("status", GeneralStatus.DELETED));
		return (FiscalYear) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<FiscalYear> getListFiscalYear(GeneralStatus status) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FiscalYear.class);
		if (status != null)
			criteria.add(Restrictions.eq("status", status));
		criteria.add(Restrictions.ne("status", GeneralStatus.DELETED));
		criteria.addOrder(Order.desc("endDate"));
		return criteria.list();
	}
	
	
	public List<FiscalYear> getListFutureYear(Date startDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FiscalYear.class);
		criteria.add(Restrictions.eq("status", GeneralStatus.OPEN))
//		.add(Restrictions.ne("status", GeneralStatus.DELETED))
		.add(Restrictions.gt("startDate", startDate));
		criteria.addOrder(Order.desc("endDate"));
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
	
	public IncomeBalance saveAccountBalance(IncomeBalance ap) {
		sessionFactory.getCurrentSession().saveOrUpdate(ap);
		return ap;
	}
	
	public IncomeBalance getAccountBalance(int id) {
		return (IncomeBalance) sessionFactory.getCurrentSession().get(IncomeBalance.class, id);
	}
	
	public IncomeBalance getAccountBalance(Account account, FiscalPeriod period) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IncomeBalance.class);
		criteria.add(Restrictions.eq("account", account));
		criteria.add(Restrictions.eq("period", period));
		return (IncomeBalance) criteria.uniqueResult();
	}
	
	public FiscalPeriod getPeriodByDate(Date date) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FiscalPeriod.class);
		criteria.add(Restrictions.and(Restrictions.le("startDate", date), Restrictions.ge("endDate", date)));
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
	
	public IncomeReceipt getIncomeReceiptByReceiptNo(String receiptNo){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IncomeReceipt.class);
		criteria.add(Restrictions.eq("receiptNo", receiptNo));
		return (IncomeReceipt) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<IncomeReceipt> getListIncomeReceipt(boolean includeVoided) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IncomeReceipt.class);
		if (!includeVoided)
			criteria.add(Restrictions.eq("voided", false));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<IncomeReceipt> getListIncomeReceipt(boolean includeVoided, int start, int end) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IncomeReceipt.class);
		if (!includeVoided)
			criteria.add(Restrictions.eq("voided", false));
		
		criteria.setFirstResult(start).setMaxResults(end);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public int countListIncomeReceipt(boolean includeVoided) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IncomeReceipt.class);
		if (!includeVoided)
			criteria.add(Restrictions.eq("voided", false));
		Number rs = (Number) criteria.setProjection(Projections.rowCount())
				.uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	@SuppressWarnings("unchecked")
	public List<IncomeReceipt> getListIncomeReceiptByAccount(Account account, boolean includeVoided) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IncomeReceipt.class);
		if (!includeVoided)
			criteria.add(Restrictions.eq("voided", false));
		criteria.add(Restrictions.eq("account", account));
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
	
	public IncomeReceiptItem getIncomeReceiptItemByAccountAndReceipt(Account acc, IncomeReceipt receipt){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IncomeReceiptItem.class);
		criteria.add(Restrictions.eq("account", acc));
		criteria.add(Restrictions.eq("receipt", receipt));
		return (IncomeReceiptItem) criteria.uniqueResult();
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
	 *   (StartA <= EndB)  and  (EndA >= StartB)
	 * @param from
	 * @param to
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public List<FiscalYear> getOverlapFiscalYears(Integer fiscalYearId, Date from, Date to) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FiscalYear.class);
		criteria.add(Restrictions.and(Restrictions.lt("startDate", to), Restrictions.gt("endDate",from)));
		criteria.add(Restrictions.ne("status", GeneralStatus.DELETED));
		return criteria.list();
		
	}
	
	public FiscalYear getActiveFicalYear() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FiscalYear.class);
		Date curDate = Calendar.getInstance().getTime();
		criteria.add(Restrictions.and(Restrictions.le("startDate", curDate), Restrictions.ge("endDate",curDate)));
		return (FiscalYear) criteria.uniqueResult();
	}
	
	
	/**
	 * PAYEE
	 */
	
	public Payee savePayee(Payee payee){
		return (Payee) sessionFactory.getCurrentSession().merge(payee);
	}
	
	public void deletePayee(Payee payee){
		sessionFactory.getCurrentSession().delete(payee);
	}
	
	public Payee getPayee(Integer id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Payee.class);
		criteria.add(Restrictions.eq("id",id));
		return (Payee) criteria.uniqueResult();
	}
	
	public List<Payee> listPayees(boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Payee.class);
		if (!includeRetired) {
			criteria.add(Restrictions.eq("retired",false));
		}
		return criteria.list();
	}
	
	/**
	 * PAYMENT
	 */
	
	public Payment savePayment(Payment payment){
		return (Payment) sessionFactory.getCurrentSession().merge(payment);
	}
	
	public void deletePayment(Payment payment){
		sessionFactory.getCurrentSession().delete(payment);
	}
	
	public Payment getPayment(Integer id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Payment.class);
		criteria.add(Restrictions.eq("id",id));
		return (Payment) criteria.uniqueResult();
	}
	
	public int countListPayments() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Payment.class);
		criteria.addOrder(Order.desc("paymentDate"));
		Number rs = (Number) criteria.setProjection(Projections.rowCount())
				.uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	@SuppressWarnings("unchecked")
    public List<Payment> listPayments(int min, int max) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Payment.class);
		
		criteria.setFirstResult(min).setMaxResults(max);
		criteria.addOrder(Order.desc("createdDate"));
		return criteria.list();
	}
	
	/*
	 *  check budgetitem start date and end date overlap
	 *  
	 */
	public boolean isBudgetItemOverlap(Account account, Date startDate, Date endDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BudgetItem.class);
		criteria.add(Restrictions.eq("account", account));
		criteria.add(Restrictions.and(Restrictions.lt("startDate", endDate), Restrictions.gt("endDate",startDate)));
		return criteria.list().isEmpty() ? false: true;
	}
	
	
	
	
	public void aggregateIncomeReceipt(Date date){
		
	}
		
	public BigDecimal getAggregateBill(Set<Integer> conceptIds, Date date){
		//TODO add date condition !!!
			if (conceptIds == null || conceptIds.size() == 0) {
				return null;
			}
			Date to = DateUtils.addDate(date, 1);
//			Date from = DateUtils.addDate(date, 0);
			
			String str = "select  sum(bill.actualAmount) from  PatientServiceBillItem as bill right join bill.service as service  "
					+ " where service.conceptId in  (:conceptIds) and bill.createdDate >= :from and bill.createdDate < :to";
			Query query = sessionFactory.getCurrentSession().createQuery(str);
			query.setParameterList("conceptIds", conceptIds);
			query.setParameter("from", date);
			query.setParameter("to", to);
			return  (BigDecimal) query.uniqueResult();
	}
	/*
			String name;
			BigDecimal amount;
			Integer conceptId;
			if (result != null && result.size() > 0) {
				for (Object o : result) {
					Object[] obj = (Object[]) o;
					conceptId = NumberUtils.toInt(obj[0].toString());
//					name = obj[1].toString();
					amount =  obj[1] != null ? new BigDecimal(obj[1].toString()) : new BigDecimal(0);
					results.put(conceptId,amount);
				}
			}
			System.out.println("=================================================================");
			System.out.println(results);
			System.out.println("=================================================================");
			return results;
			
	}
	*/
	
	
	
	public ExpenseBalance saveExpenseBalance(ExpenseBalance expense){
		return  (ExpenseBalance) sessionFactory.getCurrentSession().merge(expense);
	}
	
	public ExpenseBalance getExpenseBalance(int id){
		return (ExpenseBalance) sessionFactory.getCurrentSession().get(ExpenseBalance.class,id);
	}
	
	
	/**
	 * return the only and only one active account balance of this account
	 * @param acc
	 * @return
	 */
	public ExpenseBalance getLatestExpenseBalance(Account account) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ExpenseBalance.class);
		criteria.add(Restrictions.eq("account", account));
		criteria.add(Restrictions.eq("status", BalanceStatus.ACTIVE));
		return (ExpenseBalance) criteria.uniqueResult();
	}
	
	public ExpenseBalance getExpenseBalanceByAccountAndPeriod(Account account, FiscalPeriod period) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ExpenseBalance.class);
		criteria.add(Restrictions.eq("account", account));
		criteria.add(Restrictions.eq("period", period));
//		criteria.add(Restrictions.eq("status", BalanceStatus.ACTIVE));
		return (ExpenseBalance) criteria.uniqueResult();
	}
	
	
	public int countListPaymentsByAccount(Account account) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Payment.class);
		criteria.add(Restrictions.eq("account", account));
		Number rs = (Number) criteria.setProjection(Projections.rowCount())
				.uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	@SuppressWarnings("unchecked")
    public List<Payment> listPaymentsByAccount(Account account, Integer min, Integer max){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Payment.class);
		criteria.add(Restrictions.eq("account", account));
		criteria.setMaxResults(max).setFirstResult(min);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
    public List<Payment> listPaymentsByAccountPeriod(Account account,Date startDate, Date endDate){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Payment.class);
		criteria.add(Restrictions.eq("account", account));
		criteria.add(Restrictions.and(Restrictions.ge("paymentDate", startDate), Restrictions.le("paymentDate",endDate)));
		return criteria.list();
	}
	
	/**
	 * Aggregate all income receipt for given period
	 * The result format is: Map<accountId,'accountName|amount'>
	 * @param from
	 * @param to
	 * @return
	 */
	
	public Map<Integer,Object[]> aggregateIncomeReceiptItem(Date from, Date to) {
		String squery = "select ir.account.id, ir.account.name, sum(ir.amount) from IncomeReceiptItem  as ir "
				+" left join ir.receipt as  r  "
				+"where r.receiptDate >= :from and r.receiptDate <= :to "
				+" group by ir.account.name";
		
		
		Query query = sessionFactory.getCurrentSession().createQuery(squery);
		query.setParameter("from", from);
		query.setParameter("to", to);
		List result = query.list();
		Map<Integer, Object[]> mapResult = new HashMap<Integer, Object[]>();
		String accountName;
		BigDecimal amount;
		Integer accountId;
		String tmp;
		if (result != null && result.size() > 0) {
			for (Object o : result) {
				Object[] obj = (Object[]) o;
				accountId = NumberUtils.toInt(obj[0].toString());
				mapResult.put(accountId,obj);
			}
		}
		return mapResult;
	}
	
	public BankStatement saveBankStatement(BankStatement bs) {
	    return (BankStatement) sessionFactory.getCurrentSession().merge(bs);
    }

    public void deleteBankStatement(Integer id) {
		BankStatement bs = getBankStatement(id);
		if (bs != null) {
			bs.setVoided(true);
			bs.setVoidedBy(Context.getAuthenticatedUser().getId());
			bs.setVoideddDate(Calendar.getInstance().getTime());
		}
    }

	public BankStatement getBankStatement(Integer id) {
		return (BankStatement) sessionFactory.getCurrentSession().load(BankStatement.class, id);
	}

    public List<BankStatement> getListBankStatements() {
		Criteria criteria =  sessionFactory.getCurrentSession().createCriteria(BankStatement.class);
		criteria.add(Restrictions.eq("voided", false))
		.addOrder(Order.desc("dateFrom"));
		return criteria.list();
	}
    
    public BankAccount saveBankAccount(BankAccount bs) {
	    return (BankAccount) sessionFactory.getCurrentSession().merge(bs);
    }

    public void deleteBankAccount(Integer id) {
		BankAccount bs = getBankAccount(id);
		if (bs != null) {
			bs.setDeleted(true);
			bs.setUpdatedBy(Context.getAuthenticatedUser().getId());
			bs.setUpdatedDate(Calendar.getInstance().getTime());
			sessionFactory.getCurrentSession().merge(bs);
		}
    }

	public BankAccount getBankAccount(Integer id) {
		return (BankAccount) sessionFactory.getCurrentSession().load(BankAccount.class, id);
	}

    public List<BankAccount> getListBankAccounts() {
		Criteria criteria =  sessionFactory.getCurrentSession().createCriteria(BankAccount.class);
		criteria.add(Restrictions.eq("deleted", false))
		.addOrder(Order.asc("bankName"));
		return criteria.list();
	}
    
}

