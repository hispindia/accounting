package org.openmrs.module.accounting.web.controller.incomereceipt;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.accounting.api.model.IncomeReceiptItem;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class IncomeReceiptItemValidator implements Validator{

	@Override
    public boolean supports(Class<?> clazz) {
	    return IncomeReceiptItem.class.equals(clazz);
    }

	@Override
    public void validate(Object obj, Errors e) {
		IncomeReceiptItem item = (IncomeReceiptItem) obj;
		if (StringUtils.isBlank(item.getAccountName()) || item.getAccount() == null) {
			e.reject("accounting.incomeReceiptItem.account.required");
		} 
		
    }
	
}
