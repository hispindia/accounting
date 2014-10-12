package org.openmrs.module.accounting.web.controller.incomereceipt;

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
import org.openmrs.module.accounting.api.model.AccountType;
import org.openmrs.module.accounting.api.model.IncomeReceipt;
import org.openmrs.module.hospitalcore.util.PagingUtil;
import org.openmrs.module.hospitalcore.util.RequestUtil;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/module/accounting/incomereceipt.list")
public class IncomeReceiptListController {
	Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(method=RequestMethod.GET)
	public String fistView(@RequestParam(value="pageSize",required=false)  Integer pageSize, 
	                         @RequestParam(value="currentPage",required=false)  Integer currentPage,
	                         @RequestParam(value="accountId",required=false)  Integer accountId,
	                         Map<String, Object> model, HttpServletRequest request){
		AccountingService accountingService = Context.getService(AccountingService.class);
		

		int total = accountingService.countListIncomeReceipt(true);
		
		PagingUtil pagingUtil = new PagingUtil( RequestUtil.getCurrentLink(request) , pageSize, currentPage, total );
		
    	List<IncomeReceipt> incomeReceipts  =  accountingService.getListIncomeReceipt(true,pagingUtil.getStartPos(), pagingUtil.getPageSize());
    	if ( incomeReceipts != null ) {
    		Collections.sort(incomeReceipts, new Comparator<IncomeReceipt>() {
                public int compare(IncomeReceipt o1, IncomeReceipt o2) {
    	            return o1.getReceiptDate().compareTo(o2.getReceiptDate());
                }});
    		model.put("incomeReceipts", incomeReceipts );
    		model.put("pagingUtil", pagingUtil);
    	}

    	List<Account> accounts = accountingService.listAccount(AccountType.INCOME, false);
		model.put("accounts", accounts);
		return "/module/accounting/incomereceipt/list";
	}
	
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
						IncomeReceipt incomeReceipt = accountingService.getIncomeReceipt(id);
						if( incomeReceipt!= null )
						{
							accountingService.delete(incomeReceipt);
						}
					}
				}
			}catch (Exception e) {
				httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
				"Can not delete Income Receipt because it has link to other reocords ");
				log.error(e);
				return "redirect:/module/accounting/incomereceipt.list";
			}
			httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
			"IncomeReceipt.deleted");
	    	
	    	return "redirect:/module/accounting/incomereceipt.list";
	    }
}
