package org.openmrs.module.accounting.web.controller.incomereceipt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.IncomeReceiptItem;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("module/accounting/incomeReceiptItem.form")
@SessionAttributes("incomeReceiptItem")
public class IncomeReceiptItemEditController {
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String get( @RequestParam("id") Integer id, Model model, HttpServletRequest request )  {
		if (id != null) {
			IncomeReceiptItem receiptItem = Context.getService(AccountingService.class).getIncomeReceiptItem(id);
			receiptItem.setAccountName(receiptItem.getAccount().getName());
			model.addAttribute("incomeReceiptItem", receiptItem);
		} 
		
		Collection<Account> accounts = Context.getService(AccountingService.class).getAccounts(false);
		if (accounts != null )
			model.addAttribute("accounts", buildJSONAccounts(new ArrayList<Account>(accounts)));
		request.getSession().setAttribute(WebConstants.OPENMRS_HEADER_USE_MINIMAL,"true");
		return "/module/accounting/incomeReceiptItem/form";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String post(@ModelAttribute("incomeReceiptItem") IncomeReceiptItem receiptItem, BindingResult bindingResult) {
		if (receiptItem == null) {
			bindingResult.reject("Can not find Income Receipt Item");
			return "/module/accounting/incomeReceiptItem/form";
		}
		new IncomeReceiptItemValidator().validate(receiptItem, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/module/accounting/incomeReceiptItem/form";
		}
		
		receiptItem.setAccount(Context.getService(AccountingService.class).getAccountByName(receiptItem.getAccountName()));
		
		receiptItem = Context.getService(AccountingService.class).saveIncomeReceiptItem(receiptItem);
		
		
		return "module/accounting/incomeReceiptItem/success";
	}
	private String buildJSONAccounts(List<Account> accounts) {
		if (accounts == null)  return null;
		StringBuffer s = new StringBuffer();
		for (Account acc : accounts) {
			s.append(acc.getName()+",");
		}
		s.deleteCharAt(s.length()-1 );
		return s.toString();
	}
}
