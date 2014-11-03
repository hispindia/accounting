package org.openmrs.module.accounting.web.controller.bankStatement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.BankStatement;
import org.openmrs.module.accounting.api.model.Budget;
import org.openmrs.module.accounting.web.controller.budget.AccountPropertySupport;
import org.openmrs.module.accounting.web.controller.budget.BudgetPropertySupport;
import org.openmrs.module.accounting.web.controller.budget.BudgetValidator;
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
public class BankStatementController {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(java.lang.Boolean.class, new CustomBooleanEditor("true", "false", true));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"),true));
		binder.registerCustomEditor(Account.class, new AccountPropertySupport());
		binder.registerCustomEditor(Budget.class, new BudgetPropertySupport());
	}
	

	@RequestMapping(value="/module/accounting/bankStatement.form",method=RequestMethod.GET)
	public String get(@RequestParam(value="id", required=false) Integer id, Model model) {
		BankStatement command = null;
		if (id == null) {
			command = new BankStatement();
		} else {
			command = Context.getService(AccountingService.class).getBankStatement(id);
		}
		model.addAttribute("command",command);
		
		return "/module/accounting/bankStatement/bankStatementForm";
	} 
	
	@RequestMapping(value="/module/accounting/bankStatement.form",method=RequestMethod.POST)
	public String post(@ModelAttribute("command") BankStatement bs, BindingResult bindingResult) {
		
		new BankStatementValidator().validate(bs, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/module/accounting/bankStatement/bankStatementForm";
		}
		AccountingService as = Context.getService(AccountingService.class);
		as.saveBankStatement(bs);
		
		
		return "redirect:/module/accounting/bankStatement.list";
	}
	
	@RequestMapping(value="/module/accounting/bankStatement.list",method=RequestMethod.GET)
	public String list(Model model) {

		List<BankStatement> listStatements = Context.getService(AccountingService.class).getListBankStatements();
		
		model.addAttribute("listStatements",listStatements);
		
		return "/module/accounting/bankStatement/bankStatementList";
	}
	
	
}
