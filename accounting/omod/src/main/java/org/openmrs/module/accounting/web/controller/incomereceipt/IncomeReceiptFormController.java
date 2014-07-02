package org.openmrs.module.accounting.web.controller.incomereceipt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.AccountType;
import org.openmrs.module.accounting.api.model.IncomeReceipt;
import org.openmrs.module.accounting.api.model.IncomeReceiptType;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/module/accounting/incomereceipt.form")
public class IncomeReceiptFormController {
	Log log = LogFactory.getLog(getClass());
	
	@ModelAttribute("itemTypes")
	public IncomeReceiptType[] registerAccountTypes() {
		return IncomeReceiptType.values();
	}
	
	@ModelAttribute("accounts")
	public String registerAccounts() {
		Collection<Account> accounts = Context.getService(AccountingService.class).listAccount(AccountType.INCOME,false);
		if (accounts != null ) {
			return buildJSONAccounts(new ArrayList<Account>(accounts));
		} else {
			return "";
		}
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(java.lang.Boolean.class, new CustomBooleanEditor("true", "false", true));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@ModelAttribute("incomeReceipt") IncomeReceipt incomeReceipt,
	                        @RequestParam(value = "id", required = false) Integer id, Model model) {
		AccountingService service = Context.getService(AccountingService.class);
		if (id != null) {
			incomeReceipt = service.getIncomeReceipt(id);
			model.addAttribute(incomeReceipt);
		} else {
			incomeReceipt = new IncomeReceipt();
			model.addAttribute(incomeReceipt);
		}
		
		return "/module/accounting/incomereceipt/form";
	}

	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(IncomeReceipt incomeReceipt, BindingResult bindingResult,
	                       HttpServletRequest request) {
		
		new IncomeReceiptValidator().validate(incomeReceipt, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/module/accounting/incomereceipt/form";
		}
		
		if ( incomeReceipt.getId() == null ) {
			/** Return to form for adding receipt item **/
			incomeReceipt = Context.getService(AccountingService.class).saveIncomeReceipt(incomeReceipt);
			return "redirect:/module/accounting/incomereceipt.form?id="+incomeReceipt.getId();
		} else {
			return "redirect:/module/accounting/incomereceipt.list";
		}
		
	}
	
	
	private String buildJSONAccounts(List<Account> accounts) {
		if (accounts == null || accounts.size() == 0)  return null;
		StringBuffer s = new StringBuffer();
		for (Account acc : accounts) {
			s.append(acc.getName()+",");
		}
		s.deleteCharAt(s.length() - 1);
		return s.toString();
	}
	
	
	
}
