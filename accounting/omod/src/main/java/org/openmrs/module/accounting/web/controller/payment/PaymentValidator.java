package org.openmrs.module.accounting.web.controller.payment;

import org.openmrs.module.accounting.api.model.Payment;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class PaymentValidator implements Validator {

	@Override
    public boolean supports(Class<?> arg0) {
		return Payment.class.equals(arg0);
    }

	@Override
    public void validate(Object arg0, Errors arg1) {
	    // TODO Auto-generated method stub
	    
    }
	
	
}
