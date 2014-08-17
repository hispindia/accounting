package org.openmrs.module.accounting.web.controller.fiscalyear;

import java.util.Collection;
import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.FiscalPeriod;
import org.openmrs.module.accounting.api.model.FiscalYear;
import org.openmrs.module.accounting.api.model.GeneralStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/module/accounting/closeFiscalYear.htm")
public class CloseFiscalYearController {
	
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String view(@RequestParam(value = "id", required = false) Integer id,
	                   Model model) {
		
		AccountingService service = Context.getService(AccountingService.class);
		if(id != null) {
			FiscalYear fiscalYear = service.getFiscalYear(id);
			// get all open periods of this fiscalYear
			List<FiscalPeriod> periods = fiscalYear.getPeriods();
			for( FiscalPeriod period : periods) {
				if( period.getStatus().equals(GeneralStatus.ACTIVE)) {
					model.addAttribute("hasOpenPeriod", true);
					return "/module/accounting/fiscalyear/closeFiscalYear";
				}
			}
			
			// All Periods are closed, can proceed to close this FiscalYear
			model.addAttribute("fiscalYear",fiscalYear);
			
			Collection<FiscalYear> years = Context.getService(AccountingService.class).getListFiscalYear(GeneralStatus.INACTIVE);
			years.remove(fiscalYear);
			
			if (years.isEmpty()) {
				model.addAttribute("noNextYear",true);
			}
			
			model.addAttribute("listFiscalYear",years);
			
			return "/module/accounting/fiscalyear/closeFiscalYear";
		} else {
			
			return "/module/accounting/fiscalyear/closeFiscalYear";
		}
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String post(@RequestParam(value = "createNextYear", required = false) Boolean createNextYear,
	                   @RequestParam(value = "nextYearId", required = false) Integer nextYearId,
	                   @RequestParam(value = "closeYearId", required = false) Integer closeYearId) {
	
		AccountingService service = Context.getService(AccountingService.class);
		
		service.closeFiscalYear(closeYearId,nextYearId,createNextYear);
		
		return "/module/accounting/fiscalyear/list";
	
	}
}
