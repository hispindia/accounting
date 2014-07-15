/**
 *  Copyright 2009 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Accounting module.
 *
 *  Accounting module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Accounting module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Accounting module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/

package org.openmrs.module.accounting.web.controller.account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 */
@Controller
@RequestMapping("/module/accounting/account.list")
public class AccountListController {
	Log log = LogFactory.getLog(getClass());
    
    @RequestMapping(method=RequestMethod.POST)
    public String deleteCompanies(@RequestParam("ids") String[] ids,HttpServletRequest request){
    	AccountingService accountingService = Context.getService(AccountingService.class);
    	HttpSession httpSession = request.getSession();
		Integer id  = null;
		try{  
			if( ids != null && ids.length > 0 ){
				for(String sId : ids )
				{
					id = Integer.parseInt(sId);
					Account account = accountingService.getAccount(id);
					if( account!= null )
					{
						accountingService.deleteAccount(account);
					}
				}
			}
		}catch (Exception e) {
			httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
			"Can not delete account because it has link to other records ");
			log.error(e);
			e.printStackTrace();
			return "redirect:/module/accounting/account.list";
		}
		httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
		"Account.deleted");
    	
    	return "redirect:/module/accounting/account.list";
    }
	
    @RequestMapping(method=RequestMethod.GET)
	public String listTender(@RequestParam(value="pageSize",required=false)  Integer pageSize, 
	                         @RequestParam(value="currentPage",required=false)  Integer currentPage,
	                         Map<String, Object> model, HttpServletRequest request){
		
//		AccountingService accountingService = Context.getService(AccountingService.class);
    	List<Account> accounts = new ArrayList<Account>(Context.getService(AccountingService.class).getAccounts(true));
    	Collections.sort(accounts, new Comparator<Account>() {
            public int compare(Account o1, Account o2) {
	            return o1.getName().compareToIgnoreCase(o2.getName());
            }});
//		int total = accountingService.countListAmbulance();
//		
//		PagingUtil pagingUtil = new PagingUtil( RequestUtil.getCurrentLink(request) , pageSize, currentPage, total );
//		
//		List<Ambulance> ambulances = accountingService.listAmbulance(pagingUtil.getStartPos(), pagingUtil.getPageSize());
//		
		model.put("accounts", accounts );
//		
//		model.put("pagingUtil", pagingUtil);
//		
		return "/module/accounting/account/list";
	}
}
