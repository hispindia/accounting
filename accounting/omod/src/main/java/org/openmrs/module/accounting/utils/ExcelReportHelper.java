package org.openmrs.module.accounting.utils;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openmrs.module.accounting.api.model.ExpenseBalance;


public class ExcelReportHelper {
	
	
	public static void generateExcel(List<ExpenseBalance> balances, ServletOutputStream outStream) throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
	    HSSFSheet sheet = wb.createSheet("Monthly payment and Commitment Summary");
	    
	    // Header
	    HSSFRow row = sheet.createRow(0);

	    // Create a cell and put a date value in it.  The first cell is not styled
	    // as a date.
	   
	    HSSFCell cell = null;
	    for (int i=0; i< ExportConstant.headers.length; i++) {
	    	cell = row.createCell(i);
		    cell.setCellValue(ExportConstant.headers[i]);
	    }
	    ExpenseBalance  balance = null;
	    for(int i=1; i<= balances.size();i++) {
	    	balance = balances.get(i-1);
	    	row = sheet.createRow(i);
	    	cell = row.createCell(ExportConstant.ITEM_CODE_INDEX);
	    	cell.setCellValue(balance.getAccount().getAccountNumber());
	    	
	    	cell = row.createCell(ExportConstant.DESCRIPTION_INDEX);
	    	cell.setCellValue(balance.getAccount().getName());
	    	
	    	cell = row.createCell(ExportConstant.NEW_AIE_INDEX);
	    	cell.setCellValue(balance.getNewAIE().doubleValue());
	    	
	    	cell = row.createCell(ExportConstant.CUMMULATIVE_AIE_INDEX);
	    	cell.setCellValue(balance.getCummulativeAIE().doubleValue());
	    	
	    	cell = row.createCell(ExportConstant.CURRENT_PAYMENT_INDEX);
	    	cell.setCellValue(balance.getCurrentPayment().doubleValue());
	    	
	    	cell = row.createCell(ExportConstant.CCUMMULATIVE_PAYMENT_INDEX);
	    	cell.setCellValue(balance.getCummulativePayment().doubleValue());
	    	
	    	cell = row.createCell(ExportConstant.AVAILABLE_BALANCE_INDEX);
	    	cell.setCellValue(balance.getAvailableBalance().doubleValue());
	    }

	    // we style the second cell as a date (and time).  It is important to
	    // create a new cell style from the workbook otherwise you can end up
	    // modifying the built in style and effecting not only this cell but other cells.
//	    CellStyle cellStyle = wb.createCellStyle();
//	    cellStyle.setDataFormat(   createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
//	    cell = row.createCell(1);
//	    cell.setCellValue(new Date());
//	    cell.setCellStyle(cellStyle);

	    //you can also set date as java.util.Calendar
//	    cell = row.createCell(2);
//	    cell.setCellValue(Calendar.getInstance());
//	    cell.setCellStyle(cellStyle);
		
	    
	    wb.write(outStream);
	    outStream.flush();
		
	}
}
