<%--
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
--%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require privilege="Add/Edit Account" otherwise="/login.htm"
	redirect="/module/accounting/main.form" />

<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />
<h2>
	<spring:message code="accounting.fiscalyear.manage" />
</h2>

<script>

	function createPeriods (type){
		
		jQuery("#tablePeriods tbody tr" ).remove();
		
		var name, period;
		var pStartDate, pEndDate;
		var sStartDate =  jQuery("#fiscalYearStartDate").val();
		var sEndDate = jQuery("#fiscalYearEndDate").val();
		
		var dStartDate = convertStringtToDate(sStartDate);
		
		var dEndDate =  convertStringtToDate(sEndDate);
		
		var arrPeriods = new Array();
		
		if ("quarterly" == type) {
			
			for (var i=0; i<4; i++) {
			
				name = "Quarter " + (i+1) + " - " + dStartDate.getFullYear();
				pStartDate = new Date(dStartDate.getTime());
				dStartDate.setMonth(dStartDate.getMonth() + 3); // next start date 
				dEndDate = new Date(dStartDate.getTime());
				dEndDate.setDate(dEndDate.getDate() - 1)//	current end date
				
//				alert(pStartDate + " ---" + dEndDate) ;// create period with startDate and endDate
				
				period = createPeriod(name, pStartDate, dEndDate);
				createPeriodRow(period);
				arrPeriods.push(period);
			}
		} else if ("monthly" == type) {
			for (var i=0; i<12; i++) {
				name = "Month "  + (i+1) + " - " + dStartDate.getFullYear();
				pStartDate = new Date(dStartDate.getTime());
				dStartDate.setMonth(dStartDate.getMonth() + 1); // next start date 
				dEndDate = new Date(dStartDate.getTime());
				dEndDate.setDate(dEndDate.getDate() - 1)//	current end date
				
//				alert(pStartDate + " ---" + dEndDate) ;// create period with startDate and endDate		
				period = createPeriod(name, pStartDate, dEndDate);
				createPeriodRow(period);
				arrPeriods.push(period);
			}
		}
		var jsonPeriods = JSON.stringify(arrPeriods);
		jQuery("#jsonPeriods").val(jsonPeriods);
	}
	
	function createPeriod (name, startDate, endDate) {
		var oPeriod = new Object();
		oPeriod.name = name;
		oPeriod.startDate = startDate;
		oPeriod.endDate = endDate;
		return oPeriod;
	}
	
	function convertStringtToDate (date) {
		var arr = date.split("/");
		var year = arr[2]; var month = arr[1]; var date = arr[0];
		
		return new Date(year,month-1,date);
		
	}
	
	function formatDate (date){
		return date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear();
	}
	
	function createPeriodRow (period){
			var row = "<tr><td>"+period.name+"</td><td>"+formatDate(period.startDate)+"</td><td>"+formatDate(period.endDate)+"</td></tr>";
		jQuery("#tablePeriods tbody").append(row);
	}
	
</script>
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message
			code="${error.defaultMessage}" text="${error.defaultMessage}" /> </span>
</c:forEach>
<spring:bind path="fiscalYear">
	<c:if test="${not empty  status.errorMessages}">
		<div class="error">
			<ul>
				<c:forEach items="${status.errorMessages}" var="error">
					<li>${error}</li>
				</c:forEach>
			</ul>
		</div>
	</c:if>
</spring:bind>
<form method="post" class="box">
<input type="hidden" id="jsonPeriods" name="jsonPeriods" />
	<table>
		<tr>
			<td><spring:message code="general.name" /></td>
			<td><spring:bind path="fiscalYear.name">
					<input type="text" name="${status.expression}"
						value="${status.value}" size="35" id="fiscalYearName"/>
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				</spring:bind></td>
		</tr>
		<tr>
			<td valign="top"><spring:message code="accounting.startDate" />
			</td>
			<td><spring:bind path="fiscalYear.startDate">
				<input type="text" name="${status.expression}"
						value="${status.value}" size="35" onfocus="showCalendar(this)" id="fiscalYearStartDate"/>
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				</spring:bind></td>
				
				
		</tr>
		<tr>
			<td valign="top"><spring:message code="accounting.endDate" />
			</td>
			<td><spring:bind path="fiscalYear.endDate">
				<input type="text" name="${status.expression}"
						value="${status.value}" size="35" onfocus="showCalendar(this)" id = "fiscalYearEndDate"/>
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				</spring:bind></td>
				
				
		</tr>
		<tr>
			<td valign="top"><spring:message code="accounting.status" />
			</td>
			<td><form:select path="fiscalYear.status">
					<form:option value="" label="--Please Select--"/>
					<form:options items="${statuses}" itemLabel="name" />
				</form:select> <form:errors path="fiscalYear.status"  cssClass="error" /></td>
		</tr>
		<tr>
			<td valing="top" colspan="2"><input type="button" value="Create Quaterly Periods" onclick="createPeriods('quarterly');"/> <input type="button" value="Create Monthly Periods" onclick="createPeriods('monthly');"/></td>
	</table>
	<br /> <input type="submit"
		value="<spring:message code="general.save"/>"> <input
		type="button" value="<spring:message code="general.cancel"/>"
		onclick="javascript:window.location.href='fiscalyear.list'">
	
	<!---------------------------------->
	<!-- TABLE TO SHOW FISCAL PERIODS -->	
	<!---------------------------------->
	<span></span>
	<table id="tablePeriods" class="box">
		<thead><td>Period Name</td><td>Start Date</td>	<td>End Date</td></thead>
		<tbody>
			<c:if test="${ not empty fiscalYear.periods  }">
				<c:forEach items="${fiscalYear.periods }" var="p">
					<tr>
						<td>${p.name }</td>
						<td><openmrs:formatDate date="${p.startDate}" type="textbox" /></td>
						<td><openmrs:formatDate date="${p.endDate}" type="textbox" /></td>
					</tr>
				</c:forEach>
			</c:if>
			
		</tbody>
	</table>
	
	
</form>
<%@ include file="/WEB-INF/template/footer.jsp"%>