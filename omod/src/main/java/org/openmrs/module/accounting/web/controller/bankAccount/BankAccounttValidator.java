package org.openmrs.module.accounting.web.controller.bankAccount;

import org.openmrs.module.accounting.api.model.BankAccount;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class BankAccounttValidator implements Validator{
	
	/**
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class clazz) {
		return BankAccount.class.equals(clazz);
	}


	@Override
    public void validate(Object arg0, Errors arg1) {
	    
    }
	
	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	
	
}
