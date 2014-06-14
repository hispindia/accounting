package org.openmrs.module.accounting.web.controller.budget;

import org.openmrs.module.accounting.api.model.BudgetItem;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class BudgetItemValidator implements Validator{

	@Override
    public boolean supports(Class<?> arg0) {
	    return arg0.isInstance(BudgetItem.class);
    }

	@Override
    public void validate(Object arg0, Errors arg1) {
	    
    }
	
}
