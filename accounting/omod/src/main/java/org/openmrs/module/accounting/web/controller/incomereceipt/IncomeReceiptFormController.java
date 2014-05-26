package org.openmrs.module.accounting.web.controller.incomereceipt;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.IncomeReceipt;
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


@Controller
@RequestMapping("/module/accounting/incomereceipt.form")
public class IncomeReceiptFormController {
	Log log = LogFactory.getLog(getClass());
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(java.lang.Boolean.class, new CustomBooleanEditor("true", "false", true));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@ModelAttribute("incomeReceipt") IncomeReceipt incomeReceipt,
	                        @RequestParam(value = "id", required = false) Integer id, Model model) {
		AccountingService service = Context.getService(AccountingService.class);
		if (id != null) {
			incomeReceipt = service.getIncomeReceipt(id);
			model.addAttribute(incomeReceipt);
		} else {
			incomeReceipt = new IncomeReceipt();
			model.addAttribute(incomeReceipt);
		}
		
		Collection<Account> accounts = service.getAccounts(false);
		model.addAttribute("accounts", accounts);
		
		return "/module/accounting/incomereceipt/form";
	}

	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(IncomeReceipt incomeReceipt, BindingResult bindingResult,
	                       @RequestParam("jsonPeriods") String jsonPeriods, HttpServletRequest request) {
		
		new IncomeReceiptValidator().validate(incomeReceipt, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/module/accounting/incomereceipt/form";
		}
		
		log.error("id here : " + incomeReceipt.getId());
		incomeReceipt = Context.getService(AccountingService.class).saveIncomeReceipt(incomeReceipt);
//		
//		ObjectMapper mapper = new ObjectMapper();
//		JsonFactory f = new JsonFactory();
//		JsonParser jp;
//		try {
//			jp = f.createJsonParser(jsonPeriods);
//			jp.nextToken();
//			Date curDate = Calendar.getInstance().getTime();
//			int userId = Context.getAuthenticatedUser().getId();
//			while (jp.nextToken() == JsonToken.START_OBJECT) {
//				FiscalPeriod fp = mapper.readValue(jp, FiscalPeriod.class);
//				fp.setCreatedBy(userId);
//				fp.setCreatedDate(curDate);
//				fp.setFiscalYear(fy);
//				fp.setStatus(GeneralStatus.I);
//				Context.getService(AccountingService.class).saveFiscalPeriod(fp);
//			}
//		}
//		catch (JsonParseException e) {
//			e.printStackTrace();
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//		}
		
		
		return "redirect:/module/accounting/incomereceipt.list";
	}
}
