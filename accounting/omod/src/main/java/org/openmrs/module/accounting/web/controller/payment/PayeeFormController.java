package org.openmrs.module.accounting.web.controller.payment;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.Payee;
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
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/module/accounting/payee.form")
public class PayeeFormController {

	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(java.lang.Boolean.class, new CustomBooleanEditor("true", "false", true));
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView( @RequestParam(value = "id", required = false) Integer id, Model model) {
		if (id != null) {
			Payee payee = Context.getService(AccountingService.class).getPayee(id);
			model.addAttribute("payee", payee);
		} else {
			Payee payee = new Payee();
			model.addAttribute("payee", payee);
		}
		
		return "/module/accounting/payment/payeeForm";
	}
	
	
	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("payee") Payee payee, BindingResult bindingResult, Model model,
	                       HttpServletRequest request, SessionStatus status) {
		
		new PayeeValidator().validate(payee, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/module/accounting/payment/payeeform";
		}
		Context.getService(AccountingService.class).savePayee(payee);
		// Clean the session attribute after successful submit
		status.setComplete();
		return "/module/accounting/payment/successPayee";
	}
}
