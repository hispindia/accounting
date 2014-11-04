package org.openmrs.module.accounting.web.controller.bankStatement;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.module.accounting.api.model.BankAccount;


public class BankAccountPropertySupport extends PropertyEditorSupport{
	
	// Converts a String to a Account (when submitting form)
    @Override
    public void setAsText(String text) {
        BankAccount acc = new BankAccount();
        acc.setId(NumberUtils.createInteger(text));
        this.setValue(acc);
    }

    // Converts a Account to a String (when displaying form)
    @Override
    public String getAsText() {
    	BankAccount c = (BankAccount) this.getValue();
        return c != null ? c.getId().toString() : "";
    }
	
}
