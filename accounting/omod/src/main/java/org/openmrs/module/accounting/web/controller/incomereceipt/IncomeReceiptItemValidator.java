package org.openmrs.module.accounting.web.controller.incomereceipt;

import org.openmrs.module.accounting.api.model.IncomeReceiptItem;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class IncomeReceiptItemValidator implements Validator{

	@Override
    public boolean supports(Class<?> clazz) {
	    return IncomeReceiptItem.class.equals(clazz);
    }

	@Override
    public void validate(Object obj, Errors e) {
		  ValidationUtils.rejectIfEmpty(e, "amount", "amount.empty");
		  ValidationUtils.rejectIfEmpty(e, "type", "type.empty");
		  ValidationUtils.rejectIfEmpty(e, "accountName", "accountName.empty");
		  IncomeReceiptItem receiptItem = (IncomeReceiptItem) obj;
		  
		  
		
    }
	
}
