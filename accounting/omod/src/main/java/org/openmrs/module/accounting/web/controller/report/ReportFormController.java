package org.openmrs.module.accounting.web.controller.report;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ReportFormController {
	
	
	@RequestMapping(value = "module/accounting/report.form", method = RequestMethod.GET)
	public String viewReportForm() {
		
		return "module/accounting/report/reportForm";
	}
	
	@RequestMapping(value = "/module/accounting/downloadReport.form ", method = RequestMethod.GET)
	public void getFile( HttpServletResponse response) throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
	    HSSFSheet sheet = wb.createSheet("Balance Sheet");
	 // Create a row and put some cells in it. Rows are 0 based.
	    HSSFRow row = sheet.createRow(0);

	    // Create a cell and put a date value in it.  The first cell is not styled
	    // as a date.
	    HSSFCell cell = row.createCell(0);
	    cell.setCellValue(new Date());

	    // we style the second cell as a date (and time).  It is important to
	    // create a new cell style from the workbook otherwise you can end up
	    // modifying the built in style and effecting not only this cell but other cells.
//	    CellStyle cellStyle = wb.createCellStyle();
//	    cellStyle.setDataFormat(   createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
	    cell = row.createCell(1);
	    cell.setCellValue(new Date());
//	    cell.setCellStyle(cellStyle);

	    //you can also set date as java.util.Calendar
	    cell = row.createCell(2);
	    cell.setCellValue(Calendar.getInstance());
//	    cell.setCellStyle(cellStyle);
		
		ServletOutputStream outStream = response.getOutputStream();
	    
	    wb.write(outStream);
	    outStream.flush();
		
	}
}
