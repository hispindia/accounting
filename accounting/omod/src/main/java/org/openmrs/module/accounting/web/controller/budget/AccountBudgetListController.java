package org.openmrs.module.accounting.web.controller.budget;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/module/accounting/budget.list")
public class AccountBudgetListController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String get() {
		
		return "/module/accounting/budget/list";
	}
}
