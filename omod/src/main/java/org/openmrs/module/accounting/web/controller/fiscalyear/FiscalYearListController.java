package org.openmrs.module.accounting.web.controller.fiscalyear;

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
import org.openmrs.module.accounting.api.model.FiscalYear;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/module/accounting/fiscalyear.list")
public class FiscalYearListController {
	Log log = LogFactory.getLog(getClass());
	
	
	
	  @RequestMapping(method=RequestMethod.GET)
		public String listTender(@RequestParam(value="pageSize",required=false)  Integer pageSize, 
		                         @RequestParam(value="currentPage",required=false)  Integer currentPage,
		                         Map<String, Object> model, HttpServletRequest request){
			
	    	List<FiscalYear> fiscalYears = new ArrayList<FiscalYear>(Context.getService(AccountingService.class).getListFiscalYear(null));
	    	Collections.sort(fiscalYears, new Comparator<FiscalYear>() {
	            public int compare(FiscalYear o1, FiscalYear o2) {
		            return o1.getName().compareToIgnoreCase(o2.getName());
	            }});
//			int total = accountingService.countListAmbulance();
//			
//			PagingUtil pagingUtil = new PagingUtil( RequestUtil.getCurrentLink(request) , pageSize, currentPage, total );
//			
//			List<Ambulance> ambulances = accountingService.listAmbulance(pagingUtil.getStartPos(), pagingUtil.getPageSize());
//			
			model.put("fiscalYears", fiscalYears );
//			
//			model.put("pagingUtil", pagingUtil);
//			
			return "/module/accounting/fiscalyear/list";
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
						FiscalYear fiscalYear = accountingService.getFiscalYear(id);
						if( fiscalYear!= null )
						{
							accountingService.deleteFiscalYear(fiscalYear);
						}
					}
				}
			}catch (Exception e) {
				httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
				"Can not delete Fiscal Year because it has link to other reocords ");
				log.error(e);
				return "redirect:/module/accounting/fiscalyear.list";
			}
			httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
			"FiscalYear.deleted");
	    	
	    	return "redirect:/module/accounting/fiscalyear.list";
	    }
	
}
