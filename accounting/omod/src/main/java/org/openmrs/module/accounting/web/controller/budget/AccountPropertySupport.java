package org.openmrs.module.accounting.web.controller.budget;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.module.accounting.api.model.Account;


public class AccountPropertySupport extends PropertyEditorSupport{
	
	// Converts a String to a Account (when submitting form)
    @Override
    public void setAsText(String text) {
        Account acc = new Account();
        acc.setId(NumberUtils.createInteger(text));
        this.setValue(acc);
    }

    // Converts a Account to a String (when displaying form)
    @Override
    public String getAsText() {
    	Account c = (Account) this.getValue();
        return c != null ? c.getId().toString() : "";
    }
	
}
