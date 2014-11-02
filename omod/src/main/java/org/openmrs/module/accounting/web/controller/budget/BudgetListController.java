package org.openmrs.module.accounting.web.controller.budget;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.Budget;
import org.openmrs.module.hospitalcore.util.PagingUtil;
import org.openmrs.module.hospitalcore.util.RequestUtil;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/module/accounting/budget.list")
public class BudgetListController {
	
Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(method=RequestMethod.GET)
	public String listTender(@RequestParam(value="pageSize",required=false)  Integer pageSize, 
	                         @RequestParam(value="currentPage",required=false)  Integer currentPage,
	                         Map<String, Object> model, HttpServletRequest request){
		
		AccountingService accountingService = Context.getService(AccountingService.class);
		int total = accountingService.countListBudgets(false);
		PagingUtil pagingUtil = new PagingUtil( RequestUtil.getCurrentLink(request) , pageSize, currentPage, total );
		List<Budget> budgets = accountingService.getListBudgets(false, pagingUtil.getStartPos(), pagingUtil.getPageSize());
		model.put("budgets", budgets );
		model.put("pagingUtil", pagingUtil);
		return "/module/accounting/budget/list";
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
					Budget budget = accountingService.getBudget(id);
					if( budget!= null )
					{
						accountingService.deleteBudget(budget);
					}
				}
			}
		}catch (Exception e) {
			httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
			"Can not delete Budget because it has link to other records ");
			log.error(e);
			return "redirect:/module/accounting/budget.list";
		}
		httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
		"Budget.deleted");
    	
    	return "redirect:/module/accounting/budget.list";
    }
}
