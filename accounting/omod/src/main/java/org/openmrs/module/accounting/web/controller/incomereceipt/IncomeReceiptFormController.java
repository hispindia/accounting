package org.openmrs.module.accounting.web.controller.incomereceipt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.GeneralStatus;
import org.openmrs.module.accounting.api.model.IncomeReceipt;
import org.openmrs.module.accounting.api.model.IncomeReceiptItem;
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
		if (accounts != null )
			model.addAttribute("accounts", buildJSONAccounts(new ArrayList<Account>(accounts)));
		
		return "/module/accounting/incomereceipt/form";
	}

	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(IncomeReceipt incomeReceipt, BindingResult bindingResult,
	                       @RequestParam("jsonReceiptItems") String jsonReceiptItems, HttpServletRequest request) {
		
		new IncomeReceiptValidator().validate(incomeReceipt, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/module/accounting/incomereceipt/form";
		}
		
		log.error("id here : " + incomeReceipt.getId());
		incomeReceipt = Context.getService(AccountingService.class).saveIncomeReceipt(incomeReceipt);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory f = new JsonFactory();
		JsonParser jp;
		try {
			jp = f.createJsonParser(jsonReceiptItems);
			jp.nextToken();
			Date curDate = Calendar.getInstance().getTime();
			int userId = Context.getAuthenticatedUser().getId();
			while (jp.nextToken() == JsonToken.START_OBJECT) {
				IncomeReceiptItem item = mapper.readValue(jp, IncomeReceiptItem.class);
				item.setCreatedBy(userId);
				item.setCreatedDate(curDate);
				item.setReceipt(incomeReceipt);
				item.setVoided(false);
				item.setAccount(Context.getService(AccountingService.class).getAccountByName(item.getAccountName()));
				Context.getService(AccountingService.class).saveIncomeReceiptItem(item);
			}
		}
		catch (JsonParseException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return "redirect:/module/accounting/incomereceipt.list";
	}
	
	
	private String buildJSONAccounts(List<Account> accounts) {
		if (accounts == null)  return null;
		StringBuffer s = new StringBuffer();
		for (Account acc : accounts) {
			s.append(acc.getName()+",");
		}
		s.deleteCharAt(s.length()-1 );
		return s.toString();
	}
}
