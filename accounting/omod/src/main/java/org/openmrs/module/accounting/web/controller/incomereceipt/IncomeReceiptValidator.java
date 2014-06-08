package org.openmrs.module.accounting.web.controller.incomereceipt;

import org.openmrs.module.accounting.api.model.IncomeReceipt;
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
		
		
		
		if (fiscalYear.getReceiptDate() == null) {
			error.reject("accounting.receipt.receiptDate");
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
