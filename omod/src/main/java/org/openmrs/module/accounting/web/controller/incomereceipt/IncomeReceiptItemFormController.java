package org.openmrs.module.accounting.web.controller.incomereceipt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.AccountType;
import org.openmrs.module.accounting.api.model.IncomeReceipt;
import org.openmrs.module.accounting.api.model.IncomeReceiptItem;
import org.openmrs.module.accounting.api.model.IncomeReceiptType;
import org.openmrs.web.WebConstants;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("module/accounting/incomeReceiptItem.form")
@SessionAttributes("incomeReceiptItem")
public class IncomeReceiptItemFormController {
	Log log = LogFactory.getLog(getClass());
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Account.class, new AccountNamePropertySupport());
	}
	
	@ModelAttribute("itemTypes")
	public IncomeReceiptType[] registerAccountTypes() {
		return IncomeReceiptType.values();
	}
	
	@ModelAttribute("accounts")
	public String registerAccounts() {
		Collection<Account> accounts = Context.getService(AccountingService.class).listAccount(AccountType.INCOME,false);
		if (accounts != null ) {
			return buildJSONAccounts(new ArrayList<Account>(accounts));
		} else {
			return "";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String get( @RequestParam(value="id",required=false) Integer id,
	                   @RequestParam(value="receiptId",required=true) Integer incomeReceiptId,
	                   Model model, HttpServletRequest request )  {
		if (id != null) {
			IncomeReceiptItem receiptItem = Context.getService(AccountingService.class).getIncomeReceiptItem(id);
			receiptItem.setAccountName(receiptItem.getAccount().getName());
			model.addAttribute("incomeReceiptItem", receiptItem);
		}else {
			IncomeReceiptItem receiptItem = new IncomeReceiptItem();
			model.addAttribute("incomeReceiptItem", receiptItem);
		}
		
		request.getSession().setAttribute(WebConstants.OPENMRS_HEADER_USE_MINIMAL,"true");
		
		model.addAttribute("incomeReceiptId",incomeReceiptId);
		
		return "/module/accounting/incomeReceiptItem/form";
	}
	
	@RequestMapping(method = RequestMethod.POST,params={"receiptItemId","action"})
	@ResponseBody
	public String postDelete(@RequestParam(value="receiptItemId",required=true) Integer incomeReceiptId,
	                   @RequestParam("action") String action,
	                   HttpServletRequest request, SessionStatus status) {
		if ( "delete".equals(action)) {
			try {
	            Context.getService(AccountingService.class).voidIncomeReceiptItem(incomeReceiptId);
	            return "success";
            }
            catch (Exception e) {
	            e.printStackTrace();
	            return e.getMessage();
            } 
			
		}
		return "Invalid Action";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String post(@ModelAttribute("incomeReceiptItem") IncomeReceiptItem receiptItem, 
	                   BindingResult bindingResult,
	                   @RequestParam(value="incomeReceiptId",required=true) Integer incomeReceiptId,
	                   Model model, HttpServletRequest request, SessionStatus status) {
		if (receiptItem == null) {
			bindingResult.reject("Can not find Income Receipt Item");
			return "/module/accounting/incomeReceiptItem/form";
		}
		new IncomeReceiptItemValidator().validate(receiptItem, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("error",bindingResult.getAllErrors());
			return "/module/accounting/incomeReceiptItem/form";
		}
		
		
//		receiptItem.setAccount(Context.getService(AccountingService.class).getAccountByName(receiptItem.getAccount().getName()));
		IncomeReceipt receipt = Context.getService(AccountingService.class).getIncomeReceipt(incomeReceiptId);
		receiptItem.setReceipt(receipt);
		try {
	        receiptItem = Context.getService(AccountingService.class).saveIncomeReceiptItem(receiptItem);
        }
        catch (Exception e) {
        	 log.error(e);
             request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR,e.getMessage());
        }
		
		// Clean the session attribute after successful submit
		status.setComplete();
		
		return "module/accounting/incomeReceiptItem/success";
	}
	
	private String buildJSONAccounts(List<Account> accounts) {
		if (accounts == null || accounts.size() == 0)  return null;
		StringBuffer s = new StringBuffer();
		for (Account acc : accounts) {
			s.append(acc.getName()+",");
		}
		s.deleteCharAt(s.length()-1 );
		return s.toString();
	}
	
	
}
