package org.openmrs.module.accounting.web.controller.fiscalyear;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.FiscalPeriod;
import org.openmrs.module.accounting.api.model.FiscalYear;
import org.openmrs.module.accounting.api.model.GeneralStatus;
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

@Controller
@RequestMapping("/module/accounting/fiscalyear.form")
@SessionAttributes("fiscalYear")
public class FiscalYearFormController {
	
	Log log = LogFactory.getLog(getClass());
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(java.lang.Boolean.class, new CustomBooleanEditor("true", "false", true));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@RequestParam(value = "id", required = false) Integer id, Model model) {
		FiscalYear fiscalYear;
		if (id != null) {
			fiscalYear = Context.getService(AccountingService.class).getFiscalYear(id);
			
		} else {
			fiscalYear = new FiscalYear();
		}
		model.addAttribute("fiscalYear", fiscalYear);
		return "/module/accounting/fiscalyear/form";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("fiscalYear") FiscalYear fiscalYear, BindingResult bindingResult,
	                       @RequestParam("jsonPeriods") String jsonPeriods, HttpServletRequest request, SessionStatus status) {
		
		new FiscalYearValidator().validate(fiscalYear, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/module/accounting/fiscalyear/form";
		}
		
		FiscalYear fy = Context.getService(AccountingService.class).saveFiscalYear(fiscalYear);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory f = new JsonFactory();
		JsonParser jp;
		try {
			jp = f.createJsonParser(jsonPeriods);
			jp.nextToken();
			Date curDate = Calendar.getInstance().getTime();
			int userId = Context.getAuthenticatedUser().getId();
			while (jp.nextToken() == JsonToken.START_OBJECT) {
				FiscalPeriod fp = mapper.readValue(jp, FiscalPeriod.class);
				fp.setCreatedBy(userId);
				fp.setCreatedDate(curDate);
				fp.setFiscalYear(fy);
				fp.setStatus(GeneralStatus.INACTIVE);
				Context.getService(AccountingService.class).saveFiscalPeriod(fp);
			}
		}
		catch (JsonParseException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		status.setComplete();
		return "redirect:/module/accounting/fiscalyear.list";
	}
	
	@ModelAttribute("status")
	public GeneralStatus[] registerStatuses() {
		return GeneralStatus.values();
	}
}
