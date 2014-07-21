/**
 *  Copyright 2009 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Billing module.
 *
 *  Billing module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Billing module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Billing module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/

package org.openmrs.module.accounting.web.controller.account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.IncomeBalance;
import org.openmrs.module.accounting.api.model.AccountType;
import org.openmrs.module.accounting.api.model.FiscalPeriod;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 *
 */
@Controller
@RequestMapping("/module/accounting/account.form")
@SessionAttributes("accountCommand")
public class AccountFormController {
	
	@ModelAttribute("periods")
	public List<FiscalPeriod> registerPeriods() {
		return Context.getService(AccountingService.class).getCurrentYearPeriods();
	}
	
	@ModelAttribute("listParents")
	public List<Account> registerListParents() {
		List<Account> listParents = new ArrayList<Account>(Context.getService(AccountingService.class)
		        .getListParrentAccount());
		Collections.sort(listParents, new Comparator<Account>() {
			
			public int compare(Account o1, Account o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		return listParents;
	}
	
	@ModelAttribute("accountTypes")
	public AccountType[] registerAccountTypes() {
		return AccountType.values();
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(java.lang.Boolean.class, new CustomBooleanEditor("true", "false", true));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView( @RequestParam(value = "id", required = false) Integer id, Model model) {
		if (id != null) {
			AccountingService service = Context.getService(AccountingService.class);
			Account account = service.getAccount(id);
			AccountCommand command = new AccountCommand();
			command.setAccount(account);
			model.addAttribute("accountCommand", command);
			/*
			 *  Disable edit fields :
			 *  - Account type
			 *  - start period
			 */
			
			model.addAttribute("disableEdit",true);
			
		} else {
			AccountCommand command = new AccountCommand();
			
			model.addAttribute("accountCommand", command);
		}
		
		return "/module/accounting/account/form";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("accountCommand") AccountCommand command, BindingResult bindingResult, Model model,
	                       HttpServletRequest request, SessionStatus status) {
		
		new AccountValidator().validate(command, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/module/accounting/account/form";
		}
		Context.getService(AccountingService.class).saveAccount(command.getAccount(), command.getPeriod());
		// Clean the session attribute after successful submit
		status.setComplete();
		return "redirect:/module/accounting/account.list";
	}
	
	
	
}
