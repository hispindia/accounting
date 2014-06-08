package org.openmrs.module.accounting.web.controller.account.balance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.AccountBalance;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/module/accounting/accountBalance.htm")
public class AccountBalanceListController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String get(@RequestParam(value="periodId", required=false) Integer fiscalPeriodId, Model model,  HttpServletRequest request){
		
		
		
		List<AccountBalance> accounts = Context.getService(AccountingService.class).listActiveAccountBalance();
		
		model.addAttribute("accounts",accounts);
		return "/module/accounting/accountBalance/list";
	}
}
