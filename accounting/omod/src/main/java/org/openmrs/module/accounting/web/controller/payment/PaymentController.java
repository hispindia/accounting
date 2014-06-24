package org.openmrs.module.accounting.web.controller.payment;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.AccountType;
import org.openmrs.module.accounting.api.model.BudgetItem;
import org.openmrs.module.accounting.api.model.Payee;
import org.openmrs.module.accounting.api.model.Payment;
import org.openmrs.module.accounting.api.model.PaymentStatus;
import org.openmrs.module.accounting.api.utils.DateUtils;
import org.openmrs.module.accounting.web.controller.budget.AccountPropertySupport;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PaymentController {
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(java.lang.Boolean.class, new CustomBooleanEditor("true", "false", true));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"),true));
		binder.registerCustomEditor(Account.class, new AccountPropertySupport());
		binder.registerCustomEditor(Payee.class, new PayeePropertySupport());
	}
	
	@ModelAttribute("listPayees")
	public String registerPayee() {
		List<Payee> listPayees = Context.getService(AccountingService.class).listActivePayees();
		if (listPayees != null ) {
			return buildJSON(listPayees);
		} else {
			return "";
		}
	}
	
	
	@ModelAttribute("accounts")
	public String registerAccounts() {
		List<Account> accounts = Context.getService(AccountingService.class).listAccount(AccountType.EXPENSE,false);
		if (accounts != null ) {
			return buildJSONAccounts(accounts);
		} else {
			return "";
		}
	}
	
	
	
	@ModelAttribute("paymentStatues")
	public PaymentStatus[] registerPaymentStatus(){
		return PaymentStatus.values();
	}
	
	@RequestMapping("/module/accounting/payment.list")
	public String showPaymentList(Model model){
		List<Payment> payments = Context.getService(AccountingService.class).listActivePayments();
		model.addAttribute("payments",payments);
		
		return "/module/accounting/payment/listPayment";
	}
	
	// show add payment from
	@RequestMapping(value="/module/accounting/payment.form", method=RequestMethod.GET)
	public String showPaymentForm(@RequestParam(value="id", required=false) Integer id, @ModelAttribute("payment") Payment payment, BindingResult bindingResult) {
		if (id == null) {
			payment = new Payment();
		} else {
			payment = Context.getService(AccountingService.class).getPayment(id);
		}
		
		
		return "/module/accounting/payment/paymentForm";
	}
	
	
	
	@RequestMapping(value="/module/accounting/payment.form", method=RequestMethod.POST)
	public String postPaymentForm(@ModelAttribute("payment") Payment payment, BindingResult bindingResult) {
		
		new PaymentValidator().validate(payment, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/module/accounting/payment/paymentForm";
		}
		Context.getService(AccountingService.class).savePayment(payment);
		return "redirect:/module/accounting/payment.list";
	}
	
	@RequestMapping(value="/module/accounting/getPaymentAIE.htm", method=RequestMethod.GET)
	@ResponseBody
	public String getPaymentAIE(@RequestParam("accountId") Integer accountId, @RequestParam("paymentDate") String paymentDate) {
		Date date = DateUtils.getDateFromStr(paymentDate);
		BudgetItem item = Context.getService(AccountingService.class).getBudgetItem(accountId, date);
		if (item != null) {
			return item.getAmount().toString();
		} else {
			return "NA";
		}
	}
	private String buildJSON(List<Payee> payees) {
		
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		try {
            mapper.writeValue(writer, payees);
        }
        catch (JsonGenerationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return writer.toString();
		
	}
	
	private String buildJSONAccounts(List<Account> accounts) {
		
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		try {
            mapper.writeValue(writer, accounts);
        }
        catch (JsonGenerationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return writer.toString();
		
	}
	
	
	
}
