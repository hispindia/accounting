package org.openmrs.module.accounting.web.controller.budget;

import org.openmrs.module.accounting.api.model.Budget;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class BudgetValidator implements Validator {

	@Override
    public boolean supports(Class<?> arg0) {
	    return BudgetCommand.class.equals(arg0);
    }

	@Override
    public void validate(Object arg0, Errors arg1) {
	    
    }
	
}
