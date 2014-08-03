/**
 *  Copyright 2010 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Patient-queue module.
 *
 *  Patient-queue module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Patient-queue module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Patient-queue module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/


package org.openmrs.module.accounting.api.job;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.utils.DateUtils;
import org.openmrs.scheduler.tasks.AbstractTask;

public class IncomeReceiptPopulateJob  extends AbstractTask {
	Log log = LogFactory.getLog(getClass());
	@Override
	public void execute() {
		log.info("**********Excute IncomeReceiptPopulateJob****************");
		try {
			Context.openSession();
			/*if (!Context.isAuthenticated()) 
			{
				authenticate();
			}*/
			// do something
			
			
			AccountingService accountService = Context.getService(AccountingService.class);
			//Date curDate = DateUtils.getDateFromStr("12/3/2014");
			Date curDate = Calendar.getInstance().getTime();
			curDate = DateUtils.getDatePart(curDate);
			accountService.aggregateIncomeReceipt(curDate);
			
			Context.closeSession();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e);
		}
		log.info("**********Finished Excute IncomeReceiptPopulateJob****************");
	}
	
	@Override
	protected void authenticate() {
		// TODO Auto-generated method stub
		super.authenticate();
	}

	@Override
	public void shutdown() {
		log.info("Shutdown IncomeReceiptPopulateJob");
		super.shutdown();
	}

}
