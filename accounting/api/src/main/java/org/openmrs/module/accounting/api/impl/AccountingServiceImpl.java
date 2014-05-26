package org.openmrs.module.accounting.api.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.db.AccountingDAO;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.AccountPeriod;
import org.openmrs.module.accounting.api.model.FiscalPeriod;
import org.openmrs.module.accounting.api.model.FiscalYear;
import org.openmrs.module.accounting.api.model.GeneralStatus;
import org.openmrs.module.accounting.api.model.IncomeReceipt;
import org.openmrs.module.accounting.api.model.IncomeReceiptItem;
import org.openmrs.module.accounting.api.utils.DateUtils;
import org.openmrs.module.hospitalcore.BillingConstants;
import org.openmrs.module.hospitalcore.BillingService;
import org.openmrs.module.hospitalcore.model.BillableService;
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
		} else  {
			log.info("update  account: " + acc.getName());
			acc.setUpdatedBy(Context.getAuthenticatedUser().getId());
			acc.setUpdatedDate(Calendar.getInstance().getTime());
			if (acc.isRetired()) {
				acc.setRetiredDate(Calendar.getInstance().getTime());
				acc.setRetiredBy(Context.getAuthenticatedUser().getId());
			}
		}
		return dao.saveAccount(acc);
	}
	
	public void deleteAccount(Account acc) {
		dao.deleteAccount(acc);
	}
	
	public Collection<Account> getAccounts(Boolean includeDisabled) {
		return dao.getAccounts(includeDisabled);
	}
	
	public Account getAccount(int id) {
		return dao.getAccount(id);
	}
	
	public FiscalYear saveFiscalYear(FiscalYear fy) {
		if (fy.getId() == null){
			fy.setCreatedDate(Calendar.getInstance().getTime());
			fy.setCreatedBy(Context.getAuthenticatedUser().getId());
		} else {
			fy.setUpdatedBy(Context.getAuthenticatedUser().getId());
			fy.setUpdatedDate(Calendar.getInstance().getTime());
		}
		
		return dao.saveFiscalYear(fy);
	}
	
	public FiscalYear getFiscalYear(int id) {
		return dao.getFiscalYear(id);
	}
	
	public FiscalPeriod saveFiscalPeriod(FiscalPeriod fp) {
		
		fp.setEndDate(DateUtils.getEnd(fp.getEndDate()));
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
	
	public FiscalYear getFiscalYearByName(String name) {
		return dao.getFiscalYearByName(name);
	}
	
	public Collection<FiscalYear> getListFiscalYear(GeneralStatus status) {
		return dao.getListFiscalYear(status);
	}
	
	public void deleteFiscalYear(FiscalYear fiscalYear) {
		dao.deleteFiscalYear(fiscalYear);
		;
	}
	
	public void deletePeriod(FiscalPeriod period) {
		dao.deleteFiscalPeriod(period);
	}

	@Override
    public void initModule() {
		log.error("****************************************");
		log.error("INIT ACCOUNTING MODULE");
		log.error("****************************************");
		Integer rootServiceConceptId = Integer.valueOf(Context.getAdministrationService().getGlobalProperty(
		    BillingConstants.GLOBAL_PROPRETY_SERVICE_CONCEPT));
		Concept rootServiceconcept = Context.getConceptService().getConcept(
			rootServiceConceptId);
		Collection<ConceptAnswer> answers = rootServiceconcept.getAnswers();
		log.error(answers);
		
		for (ConceptAnswer ca: answers) {
			log.error(ca.getAnswerConcept().getName().getName());
			
		}
		
		
    }

	@Override
    public IncomeReceipt saveIncomeReceipt(IncomeReceipt incomeReceipt) {
		if (incomeReceipt.getId() == null){
			incomeReceipt.setCreatedBy(Context.getAuthenticatedUser().getId());
			incomeReceipt.setCreatedDate(Calendar.getInstance().getTime());
		}else {
			incomeReceipt.setUpdatedBy(Context.getAuthenticatedUser().getId());
			incomeReceipt.setUpdatedDate(Calendar.getInstance().getTime());
		}
		return dao.saveIncomeReceipt(incomeReceipt);
    }

	@Override
    public IncomeReceipt getIncomeReceipt(Integer id) {
	    return dao.getIncomeReceipt(id);
    }

	@Override
    public List<IncomeReceipt> getListIncomeReceipt(boolean includeVoided) {
	    return dao.getListIncomeReceipt(includeVoided);
    }

	@Override
    public List<IncomeReceipt> getListIncomeReceiptByDate(String startDate, String endDate, boolean includeVoided) {
	    return dao.getListIncomeReceiptByDate(startDate, endDate, includeVoided);
    }

	@Override
    public void delete(IncomeReceipt incomeReceipt) {
		dao.delete(incomeReceipt);
	}

	@Override
    public IncomeReceiptItem saveIncomeReceiptItem(IncomeReceiptItem incomeReceiptItem) {
		if (incomeReceiptItem.getId() == null){
			incomeReceiptItem.setCreatedBy(Context.getAuthenticatedUser().getId());
			incomeReceiptItem.setCreatedDate(Calendar.getInstance().getTime());
		} else {
			incomeReceiptItem.setUpdatedBy(Context.getAuthenticatedUser().getId());
			incomeReceiptItem.setUpdatedDate(Calendar.getInstance().getTime());
		}
	    return dao.saveIncomeReceiptItem(incomeReceiptItem);
    }

	@Override
    public IncomeReceiptItem getIncomeReceiptItem(Integer id) {
	    return dao.getIncomeReceiptItem(id);
    }

	@Override
    public List<IncomeReceiptItem> getListIncomeReceiptItem(boolean includeVoided) {
	    return dao.getListIncomeReceiptItem(includeVoided);
    }

	@Override
    public List<IncomeReceiptItem> getListIncomeReceiptItemByDate(String startDate, String endDate) {
	    return dao.getListIncomeReceiptItemByDate(startDate, endDate);
    }

	@Override
    public void delete(IncomeReceiptItem incomeReceiptItem) {
		dao.delete(incomeReceiptItem);
	}

	@Override
    public List<IncomeReceiptItem> getListIncomeReceiptItemByAccount(Account acc) {
	    return dao.getListIncomeReceiptItemByAccount(acc);
    }
}
