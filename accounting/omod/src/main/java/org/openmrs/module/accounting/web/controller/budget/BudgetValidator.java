package org.openmrs.module.accounting.web.controller.budget;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.Budget;
import org.openmrs.module.accounting.api.model.BudgetItem;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class BudgetValidator implements Validator {

	@Override
    public boolean supports(Class<?> arg0) {
	    return BudgetCommand.class.equals(arg0);
    }

	@Override
    public void validate(Object command, Errors error) {
		BudgetCommand com = (BudgetCommand) command;
		Budget budget = com.getBudget();
		AccountingService service = Context.getService(AccountingService.class);
		
		if (StringUtils.isBlank(budget.getName())) {
			error.reject("accounting.name.required");
		}
		
		if (budget.getStartDate() == null) {
			error.reject("accounting.startDate.required");
		}
		
		if (budget.getEndDate() == null) {
			error.reject("accounting.endDate.required");
		}
		
		Budget persitedBudget = service.getBudgetByName(budget.getName()); 
		if (budget.getId() == null) {
			if (persitedBudget != null) {
				error.reject("name.invalid","Name is already exist");
			}
		} else {
			// Update
			if (persitedBudget != null && persitedBudget.getId() != budget.getId()) {
				error.reject("name.invalid","Name is already exist");
			}
		}
		
		
		List<BudgetItem> items = com.getBudgetItems();
		if (items != null && !items.isEmpty()) {
			for (BudgetItem item : items ){
				if( service.isBudgetItemOverlap(item.getAccount().getId(), item.getStartDate(), item.getEndDate())) {
					error.reject("budgetItem.overlap","Budget Item period is overlap");
				}
			}
		}
	}
	
}
