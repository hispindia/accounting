package org.openmrs.module.accounting.utils;


public class ExportConstant {
	public static final String ITEM_CODE = "Item Code";
	public static final Integer ITEM_CODE_INDEX = 0;
	public static final String DESCRIPTION = "Description";
	public static final Integer DESCRIPTION_INDEX = 1;
	public static final String NEW_AIE = "New AIE";
	public static final Integer NEW_AIE_INDEX = 2;
	public static final String CUMMULATIVE_AIE = "Cummulative AIE";
	public static final Integer CUMMULATIVE_AIE_INDEX = 3;
	public static final String CURRENT_PAYMENT = "Payment made this month";
	public static final Integer CURRENT_PAYMENT_INDEX = 4;
	public static final String CUMMULATIVE_PAYMENT = "Cummulative Payments this F/Y";
	public static final Integer CCUMMULATIVE_PAYMENT_INDEX = 5;
	public static final String AVAILABLE_BALANCE = "Available Balance";
	public static final Integer AVAILABLE_BALANCE_INDEX = 6;
	public static final  String[] headers = {ExportConstant.ITEM_CODE, 
		ExportConstant.DESCRIPTION, 
		ExportConstant.NEW_AIE, 
		ExportConstant.CUMMULATIVE_AIE, 
		ExportConstant.CURRENT_PAYMENT, 
		ExportConstant.CUMMULATIVE_PAYMENT,
		ExportConstant.AVAILABLE_BALANCE} ;
}
