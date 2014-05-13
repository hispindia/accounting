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
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.AccountType;
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

/**
 *
 */
@Controller
@RequestMapping("/module/accounting/account.form")
public class AccountFormController {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(java.lang.Boolean.class, new CustomBooleanEditor("true", "false", true));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@ModelAttribute("account") Account account,
	                        @RequestParam(value = "id", required = false) Integer id, Model model) {
		if (id != null) {
			account = Context.getService(AccountingService.class).getAccount(id);
			model.addAttribute(account);
		}else{
			account = new Account();
			model.addAttribute(account);
		}
		model.addAttribute("accountTypes", AccountType.values());
	
		
		List<Account> listParents = new ArrayList<Account>(Context.getService(AccountingService.class).getListParrentAccount());
    	Collections.sort(listParents, new Comparator<Account>() {
            public int compare(Account o1, Account o2) {
	            return o1.getName().compareToIgnoreCase(o2.getName());
            }});
		
		model.addAttribute("listParents", listParents);
		return "/module/accounting/account/form";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(Account account, BindingResult bindingResult, HttpServletRequest request) {
		
		new AccountValidator().validate(account, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/module/accounting/account/form";
		}
		Context.getService(AccountingService.class).saveAccount(account);
		return "redirect:/module/accounting/account.list";
	}
	
}
