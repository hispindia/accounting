package org.openmrs.module.accounting.web.controller.incomereceipt;

import java.beans.PropertyEditorSupport;

import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.Account;


public class AccountNamePropertySupport extends PropertyEditorSupport{
	
	// Converts a String to a Account (when submitting form)
    @Override
    public void setAsText(String text) {
    	Account acc = Context.getService(AccountingService.class).getAccountByName(text);
        this.setValue(acc);
    }

    // Converts a Account to a String (when displaying form)
    @Override
    public String getAsText() {
    	Account c = (Account) this.getValue();
        return c != null ? c.getName() : "";
    }
	
}
