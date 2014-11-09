package org.openmrs.module.accounting.web.controller.payment;

import java.math.BigDecimal;

import org.openmrs.module.accounting.api.model.Payment;
import org.openmrs.module.accounting.api.model.PaymentStatus;
import org.openmrs.module.accounting.api.utils.DateUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class PaymentValidator implements Validator {

	@Override
    public boolean supports(Class<?> arg0) {
		return Payment.class.equals(arg0);
    }

	@Override
    public void validate(Object arg0, Errors error) {
	    // TODO Auto-generated method stub
	    Payment payment = (Payment) arg0;

	    if (payment.getAccount() == null) {
	    	error.reject("accounting.account.required");
	    }
	    
	    if (payment.getPaymentDate() == null) {
	    	error.reject("accounting.paymentDate.required");
	    } else  if (DateUtils.isFutureDate(payment.getPaymentDate())) {
	    	error.reject("accounting.paymentDate.future");
	    }
	    
	    if (payment.getPayableAmount() == null) {
	    	error.reject("accounting.payable.required");
	    }
	    
	    if (payment.getPayee() == null) {
	    	error.reject("accounting.payee.required");
	    }
	    if (payment.getStatus().equals(PaymentStatus.PAID)) {
	    	if( payment.getActualPayment() == null || payment.getActualPayment().compareTo(new BigDecimal("0") ) == 0  ) {
	    		error.reject("accounting.payment.actualPayment.required");
	    	}
	    }
    }
	
	
}
