package org.openmrs.module.accounting.web.controller.incomereceipt;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.AccountType;
import org.openmrs.module.accounting.api.model.IncomeReceiptItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/module/accounting/incomeReceiptItem.list")
public class IncomeReceiptItemListController {
	Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(method=RequestMethod.GET)
	public String fistView(@RequestParam(value="pageSize",required=false)  Integer pageSize, 
	                         @RequestParam(value="currentPage",required=false)  Integer currentPage,
	                         @RequestParam(value="accountId",required=false)  Integer accountId,
	                         Map<String, Object> model, HttpServletRequest request){
		
    	
		model.put("selectedAccount", accountId);
		
    	AccountingService accountingService = Context.getService(AccountingService.class);
    	List<Account> accounts = accountingService.listAccount(AccountType.INCOME, false);
		model.put("accounts", accounts);
    	if (accountId == null) {
    		return "/module/accounting/incomereceipt/list";
    	}
    	
    	List<IncomeReceiptItem> incomeReceipts  =  accountingService.getListIncomeReceiptItemByAccount(accountId);
    	if ( incomeReceipts != null ) {
    		Collections.sort(incomeReceipts, new Comparator<IncomeReceiptItem>() {
                public int compare(IncomeReceiptItem o1, IncomeReceiptItem o2) {
    	            return o1.getTransactionDate().compareTo(o2.getTransactionDate());
                }});
    		model.put("incomeReceipts", incomeReceipts );
    	}
    	
//		int total = accountingService.countListAmbulance();
//		
//		PagingUtil pagingUtil = new PagingUtil( RequestUtil.getCurrentLink(request) , pageSize, currentPage, total );
//		
//		List<Ambulance> ambulances = accountingService.listAmbulance(pagingUtil.getStartPos(), pagingUtil.getPageSize());
//		
		
//		
//		model.put("pagingUtil", pagingUtil);
//		
    	
		
		return "/module/accounting/incomereceipt/listReceiptItem";
	}
	
}
