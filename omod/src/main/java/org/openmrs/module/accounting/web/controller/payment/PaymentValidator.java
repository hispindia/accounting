package org.openmrs.module.accounting.web.controller.payment;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.ExpenseBalance;
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
	    } else if (payment.getPayableAmount().compareTo(new BigDecimal("0")) < 0 ){
	    	error.reject("accounting.payable.invalid");
	    }
	    
	    if (payment.getPayee() == null) {
	    	error.reject("accounting.payee.required");
	    }
	    Date date = payment.getPaymentDate();
	  	ExpenseBalance balance = Context.getService(AccountingService.class).findExpenseBalance(payment.getAccount().getId(), date);
	  	if ( balance == null || balance.getAvailableBalance() == null || balance.getAvailableBalance().compareTo(new BigDecimal("0")) < 0) {
	  		error.reject("accounting.payment.budget.required");
	  	} else {
		  	if (payment.getStatus().equals(PaymentStatus.COMMITTED)) {
		  		if (payment.getCommitmentAmount() == null || payment.getCommitmentAmount().compareTo(new BigDecimal("0")) <= 0) {
		  			error.reject("accounting.payment.commitmentAmount.invalid");
		  		} else if (payment.getCommitmentAmount().compareTo(balance.getAvailableBalance()) > 0) {
		  			error.reject("accounting.payment.budget.notEnough");
		  		}
		  	}
		    if (payment.getStatus().equals(PaymentStatus.PAID)) {
		    	if(  payment.getActualPayment() == null || payment.getActualPayment().compareTo(new BigDecimal("0") ) <= 0  ) {
		    		error.reject("accounting.payment.actualPayment.invalid");
		    	}else if (payment.getActualPayment().compareTo(balance.getLedgerBalance()) > 0) {
		    		error.reject("accounting.payment.budget.notEnough");
		    	}
		    }
	  	}
    }
	
	
}
