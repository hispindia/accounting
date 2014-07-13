package org.openmrs.module.accounting.web.controller.incomereceipt;

import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.FiscalPeriod;
import org.openmrs.module.accounting.api.model.IncomeReceipt;
import org.openmrs.module.accounting.api.utils.DateUtils;
import org.springframework.validation.Errors;


public class IncomeReceiptValidator {
	/**
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
    public boolean supports(Class clazz) {
		return IncomeReceipt.class.equals(clazz);
	}
	
	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object command, Errors error) {
		IncomeReceipt fiscalYear = (IncomeReceipt) command;
		
		
		AccountingService service = Context.getService(AccountingService.class); 
		if (fiscalYear.getReceiptDate() == null) {
			error.reject("accounting.receipt.receiptDate");
		} else 	if (DateUtils.isFutureDate(fiscalYear.getReceiptDate())) {
			error.reject("accounting.receipt.receiptDate.future");
		}
		
		FiscalPeriod period = service.getFiscalPeriodByDate(fiscalYear.getReceiptDate());
		if (period == null){
			error.reject("accounting.receipt.period.notexisted");
		}
		
		//		Integer companyId = account.getAccountId();
		//		if (companyId == null) {
		//			if (billingService.getAccountByName(account.getName())!= null) {
		//				error.reject("billing.name.existed");
		//			}
		//		} else {
		//			Account dbStore = billingService.getAccountById(companyId);
		//			if (!dbStore.getName().equalsIgnoreCase(account.getName())) {
		//				if (billingService.getAccountByName(account.getName()) != null) {
		//					error.reject("billing.name.existed");
		//				}
		//			}
		//		}
	}
}
