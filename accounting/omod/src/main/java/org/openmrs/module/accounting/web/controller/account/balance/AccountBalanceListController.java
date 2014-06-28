package org.openmrs.module.accounting.web.controller.account.balance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.AccountBalance;
import org.openmrs.module.accounting.api.model.AccountType;
import org.openmrs.module.accounting.api.model.ExpenseBalance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/module/accounting/accountBalance.htm")
public class AccountBalanceListController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String get(@RequestParam(value="periodId", required=false) Integer fiscalPeriodId,
	                  @RequestParam(value="type",required=false) String type, 
	                  Model model,  HttpServletRequest request){
		
		AccountType accType;
		if (type != null) {
			accType = AccountType.valueOf(type);
		} else {
			accType = AccountType.INCOME;
			type = AccountType.INCOME.getName();
		}
		
		if (accType.equals(AccountType.INCOME)) {
			List<AccountBalance> accounts = Context.getService(AccountingService.class).listActiveAccountBalance();
			model.addAttribute("accounts",accounts);
		} else if (accType.equals(AccountType.EXPENSE)) {
			List<ExpenseBalance> accounts = Context.getService(AccountingService.class).listActiveExpenseBalance();
			model.addAttribute("accounts",accounts);
		}
		
		
		model.addAttribute("type",type);
		
		
		return "/module/accounting/accountBalance/list";
	}
}
