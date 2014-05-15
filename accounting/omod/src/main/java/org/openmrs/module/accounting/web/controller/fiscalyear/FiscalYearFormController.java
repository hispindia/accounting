package org.openmrs.module.accounting.web.controller.fiscalyear;

import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.AccountType;
import org.openmrs.module.accounting.api.model.FiscalYear;
import org.openmrs.module.accounting.api.model.GeneralStatus;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/module/accounting/fiscalyear.form")
public class FiscalYearFormController {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(java.lang.Boolean.class, new CustomBooleanEditor("true", "false", true));
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String firstView(@ModelAttribute("fiscalyear") FiscalYear fiscalYear,
	                      @RequestParam(value = "id", required = false) Integer id, Model model){
		if (id != null) {
			fiscalYear = Context.getService(AccountingService.class).getFiscalYear(id);
			model.addAttribute(fiscalYear);
		}else{
			fiscalYear = new FiscalYear();
			model.addAttribute(fiscalYear);
		}
		model.addAttribute("status", GeneralStatus.values());
		return "/module/accounting/fiscalyear/form";
	}
}
