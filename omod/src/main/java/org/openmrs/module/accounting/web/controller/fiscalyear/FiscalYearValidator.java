/**
 *  Copyright 2009 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Billing module.
 *
 *  Billing module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Billing module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Billing module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/

package org.openmrs.module.accounting.web.controller.fiscalyear;

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.model.FiscalYear;
import org.openmrs.module.accounting.api.model.GeneralStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 */
public class FiscalYearValidator implements Validator {
	
	/**
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
    public boolean supports(Class clazz) {
		return FiscalYear.class.equals(clazz);
	}
	
	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors error) {
		FiscalYear fiscalYear = (FiscalYear) obj;
		AccountingService accountingService = (AccountingService) Context.getService(AccountingService.class);
		
		if (StringUtils.isBlank(fiscalYear.getName())) {
			error.reject("accounting.name.required");
		} 
		
		if (fiscalYear.getStatus() == null) {
			error.reject("accounting.type.required");
		}
		
		if (fiscalYear.getStartDate() == null) {
			error.reject("accounting.startDate.required");
		}
		
		if (fiscalYear.getEndDate() == null) {
			error.reject("accounting.endDate.required");
		}
		
		if (accountingService.isOverlapFiscalYear(fiscalYear.getId(), fiscalYear.getStartDate(), fiscalYear.getEndDate())) {
			error.reject("accounting.overlap");
		}
		
		if (fiscalYear.getId() != null) {
			// UPDATE
		/* remove this because I removed ACTIVE, there will be only OPEN,CLOSED and DELETED
			if (fiscalYear.getStatus().equals(GeneralStatus.ACTIVE)) {
				FiscalYear year = accountingService.getActiveFiscalYear() ; 
				if (year != null && !fiscalYear.getId().equals(year.getId())) {
					// Only one active fiscal year allow
					error.reject("accounting.active.exisited");
				}
			}
			*/
			FiscalYear year = accountingService.getFiscalYearByName(fiscalYear.getName());
			if (year != null && !year.getId().equals(fiscalYear.getId())){
				error.reject("accounting.name.exisited");
			}
		
		} else {
			// ADD NEW
			/* as above
			if (fiscalYear.getStatus().equals(GeneralStatus.ACTIVE)) {
				FiscalYear year = accountingService.getActiveFiscalYear() ; 
				if (year != null ) {
					// Only one active fiscal year allow
					error.reject("accounting.active.exisited");
				}
			}*/
		}
	}
}
