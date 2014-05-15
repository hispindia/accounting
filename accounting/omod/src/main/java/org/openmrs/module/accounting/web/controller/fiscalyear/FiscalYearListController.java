package org.openmrs.module.accounting.web.controller.fiscalyear;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.Account;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/module/accounting/fiscalyear.list")
public class FiscalYearListController {
	
	  @RequestMapping(method=RequestMethod.GET)
		public String listTender(@RequestParam(value="pageSize",required=false)  Integer pageSize, 
		                         @RequestParam(value="currentPage",required=false)  Integer currentPage,
		                         Map<String, Object> model, HttpServletRequest request){
			
//			AccountingService accountingService = Context.getService(AccountingService.class);
//	    	List<Account> accounts = new ArrayList<Account>(Context.getService(AccountingService.class).getAccounts(false));
//	    	Collections.sort(accounts, new Comparator<Account>() {
//	            public int compare(Account o1, Account o2) {
//		            return o1.getName().compareToIgnoreCase(o2.getName());
//	            }});
//			int total = accountingService.countListAmbulance();
//			
//			PagingUtil pagingUtil = new PagingUtil( RequestUtil.getCurrentLink(request) , pageSize, currentPage, total );
//			
//			List<Ambulance> ambulances = accountingService.listAmbulance(pagingUtil.getStartPos(), pagingUtil.getPageSize());
//			
//			model.put("accounts", accounts );
//			
//			model.put("pagingUtil", pagingUtil);
//			
			return "/module/accounting/fiscalyear/list";
		}
	
}
