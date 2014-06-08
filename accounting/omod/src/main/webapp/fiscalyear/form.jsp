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
<%@ include file="../includes/nav.jsp" %>
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/accounting/scripts/jquery/jquery.validate.min.js"></script>
<h2>
	<spring:message code="accounting.fiscalyear.addEdit" />
</h2>

<script>

	jQuery(document).ready(function(){
		jQuery("#mainForm").validate({
			rules:{
				name: "required",
				startDate : "required",
				endDate : "required",
				status: "required"
			}
		});
		
		// disable edit if fiscal year status is ACTIVE
		if (jQuery("#status").val() == "A") {

			jQuery("#fiscalYearName").attr("readonly", "true");
			jQuery("#fiscalYearStartDate").attr("onfocus", "");
			jQuery("#fiscalYearStartDate").attr("onfocus", "");
			jQuery("#fiscalYearEndDate").attr("readonly", "true");
			jQuery("#btnCreateQuarter").attr("disabled", "true");
			jQuery("#btnCreateMonthly").attr("disabled", "true");
		}
		
	});

	function createPeriods (type){
		if (!jQuery("#mainForm").valid()) {
			return;
		}
		
			var name, period;
			var pStartDate, pEndDate, nextStartDate;

			var yearStartDate = convertStringtToDate(jQuery("#fiscalYearStartDate").val());

			var yearEndDate =  convertStringtToDate(jQuery("#fiscalYearEndDate").val());
		
		if (yearStartDate == "" || yearEndDate == "" ) {
			alert("please enter Start Date and End Date");
			return;
		}
		
		jQuery("#tablePeriods tbody tr" ).remove();
		
	
		
		var arrPeriods = new Array();
		
		nextStartDate = new Date(yearStartDate.getTime());
		

		if ("quarterly" == type) {
			
			for (var i=0; i<4; i++) {
				
				pStartDate = new Date(nextStartDate.getTime());
				
				nextStartDate = new Date(nextStartDate.getFullYear(),nextStartDate.getMonth() + 3, 1, 0,0,0,0);  
		
				pEndDate = new Date(nextStartDate.getFullYear(),nextStartDate.getMonth(), nextStartDate.getDate() - 1, 23,59,59,999);
				
				name = "Quarter " + (Math.floor((pStartDate.getMonth() + 3) / 3)) + " - " + nextStartDate.getFullYear();
				
				if (pEndDate >= yearEndDate){
					pEndDate = new Date(yearEndDate.getTime());
					period = createPeriod(name, pStartDate, pEndDate);
					createPeriodRow(period);
					arrPeriods.push(period);
					break;
				} else {
					period = createPeriod(name, pStartDate, pEndDate);
					createPeriodRow(period);
					arrPeriods.push(period);
				}
			}
		} else if ("monthly" == type) {

			for (var i=0; i<12; i++) {
				
				pStartDate = new Date(nextStartDate.getTime());
				
				nextStartDate = new Date(nextStartDate.getFullYear(),nextStartDate.getMonth() + 1, 1, 0,0,0,0); 
		
				pEndDate = new Date(nextStartDate.getFullYear(),nextStartDate.getMonth(), nextStartDate.getDate() - 1, 23,59,59,999);
				
				name = "Month "  + ( pStartDate.getMonth()  + 1 ) + " - " + nextStartDate.getFullYear();
				
				if (pEndDate >= yearEndDate){
					pEndDate = new Date(yearEndDate.getTime());
					period = createPeriod(name, pStartDate, pEndDate);
					createPeriodRow(period);
					arrPeriods.push(period);
					break;
				} else {
					period = createPeriod(name, pStartDate, pEndDate);
					createPeriodRow(period);
					arrPeriods.push(period);
				}
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
<form method="post" class="box" id="mainForm">
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
			<td valing="top" colspan="2"><input type="button" id="btnCreateQuarter" value="Create Quaterly Periods" onclick="createPeriods('quarterly');"/> <input type="button" id="btnCreateMonthly" value="Create Monthly Periods" onclick="createPeriods('monthly');"/></td>
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