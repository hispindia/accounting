package org.openmrs.module.accounting.web.controller.account.balance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.IncomeBalance;
import org.openmrs.module.accounting.api.model.AccountType;
import org.openmrs.module.accounting.api.model.ExpenseBalance;
import org.openmrs.module.accounting.api.model.FiscalPeriod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/module/accounting/accountBalance.htm")
public class AccountBalanceListController {
	
	
	@ModelAttribute("periods")
	public List<FiscalPeriod> registerPeriods() {
		return Context.getService(AccountingService.class).getCurrentYearPeriods();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String get(@RequestParam(value="period", required=false) Integer fiscalPeriodId,
	                  @RequestParam(value="type",required=false) String type,
	                  Model model,  HttpServletRequest request){
		
		AccountType accType;
		if (type != null) {
			accType = AccountType.valueOf(type);
		} else {
			accType = AccountType.INCOME;
			type = AccountType.INCOME.getName();
		}
		
		if (accType.equals(AccountType.INCOME)) {
			List<IncomeBalance> accounts = Context.getService(AccountingService.class).listActiveAccountBalanceByPeriodId(fiscalPeriodId);
			model.addAttribute("accounts",accounts);
		} else if (accType.equals(AccountType.EXPENSE)) {
			List<ExpenseBalance> accounts = Context.getService(AccountingService.class).listActiveExpenseBalanceByPeriodId(fiscalPeriodId);
			model.addAttribute("accounts",accounts);
		}
		
		model.addAttribute("type",type);
		model.addAttribute("periodId",fiscalPeriodId);
		
		
		
		//Get the workbook instance for XLS file 
//		HSSFWorkbook workbook = new HSSFWorkbook();

		
		//Get first sheet from the workbook
//		HSSFSheet sheet = workbook.createSheet("Sheet 1");

		//Get iterator to all the rows in current sheet

		//Get iterator to all cells of current row
		
		return "/module/accounting/accountBalance/list";
	}
	
	
	
	
}
