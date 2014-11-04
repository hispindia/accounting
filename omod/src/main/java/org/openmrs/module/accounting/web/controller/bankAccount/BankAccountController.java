package org.openmrs.module.accounting.web.controller.bankAccount;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.BankAccount;
import org.openmrs.module.accounting.api.model.Budget;
import org.openmrs.module.accounting.web.controller.budget.AccountPropertySupport;
import org.openmrs.module.accounting.web.controller.budget.BudgetPropertySupport;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@SessionAttributes("command")
public class BankAccountController {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(java.lang.Boolean.class, new CustomBooleanEditor("true", "false", true));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"),true));
		binder.registerCustomEditor(Account.class, new AccountPropertySupport());
		binder.registerCustomEditor(Budget.class, new BudgetPropertySupport());
	}
	

	@RequestMapping(value="/module/accounting/bankAccount.form",method=RequestMethod.GET)
	public String get(@RequestParam(value="id", required=false) Integer id, Model model) {
		BankAccount command = null;
		if (id == null) {
			command = new BankAccount();
		} else {
			command = Context.getService(AccountingService.class).getBankAccount(id);
		}
		model.addAttribute("command",command);
		
		model.addAttribute("bankAccounts",Context.getService(AccountingService.class).getListBankAccounts());
		
		return "/module/accounting/bankAccount/bankAccountForm";
	} 
	
	@RequestMapping(value="/module/accounting/bankAccount.form",method=RequestMethod.POST)
	public String post(@ModelAttribute("command") BankAccount bs, BindingResult bindingResult) {
		
		new BankAccounttValidator().validate(bs, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/module/accounting/bankAccount/bankAccountForm";
		}
		AccountingService as = Context.getService(AccountingService.class);
		as.saveBankAccount(bs);
		
		
		return "redirect:/module/accounting/bankAccount.list";
	}
	
	@RequestMapping(value="/module/accounting/bankAccount.list",method=RequestMethod.GET)
	public String list(Model model) {

		List<BankAccount> listAccounts = Context.getService(AccountingService.class).getListBankAccounts();
		
		model.addAttribute("listAccounts",listAccounts);
		
		return "/module/accounting/bankAccount/bankAccountList";
	}
	
	
}
