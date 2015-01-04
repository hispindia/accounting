/**
 *  Copyright 2009 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Billing module.
 *
 *  Billing module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Billing module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Billing module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/

package org.openmrs.module.accounting.web.controller.account;

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.validator.ValidateUtil;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 */
public class AccountValidator implements Validator {
	
	/**
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class clazz) {
		return Account.class.equals(clazz);
	}
	
	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object cmd, Errors error) {
    	AccountCommand command= (AccountCommand) cmd;
    	Account account = command.getAccount();
    	AccountingService accountingService = Context.getService(AccountingService.class);
    	
    	if (StringUtils.isBlank(account.getName())) {
    		error.reject("accounting.name.required");
    	} 
    	
    	if (account.getAccountType() == null){
    		error.reject("accounting.type.required");
    	}
    	
    	
    	if (StringUtils.isBlank(account.getAccountNumber())) {
    		error.reject("accounting.accountNumber.required");
    	}
    	
    	if (account.getId() != null) {
    		// UPDATE
    		Account acc = accountingService.getAccountByAccountNumber(account.getAccountNumber());
    		if (acc != null && acc.getId() != account.getId()) {
    			error.reject("accounting.accountNumber.existed");
    		}
    		
    		acc = accountingService.getAccountByNameAndType(account.getName(),account.getAccountType());
    		if (acc != null && acc.getId() != account.getId()) {
    			error.reject("accounting.name.existed");
    		}
    		
    	} else {
    		// ADD NEW
    		/* Dont need this
    		if (command.getPeriod() == null){
        		error.reject("accounting.period.required");
        	}
    		*/
    		// CREATE NEW
    		Account acc = accountingService.getAccountByNameAndType(account.getName(),account.getAccountType());
    		if (acc != null){
    			error.reject("accounting.name.existed");
    		}
    		
    	}
    	
    	
    }
}
