package org.openmrs.module.accounting.web.controller.bankStatement;

import org.openmrs.module.accounting.api.model.BankStatement;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class BankStatementValidator implements Validator{
	
	/**
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class clazz) {
		return BankStatement.class.equals(clazz);
	}


	@Override
    public void validate(Object arg0, Errors arg1) {
	    
    }
	
	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	
	
}
