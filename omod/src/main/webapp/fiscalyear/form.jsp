<%--
 *  Copyright 2009 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Billing module.
 *
 *  Billing module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Accounting module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Billing module.  If not, see <http://www.gnu.org/licenses/>.
 *
--%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require privilege="Add/Edit Fiscal Year" otherwise="/login.htm"
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
		
		var quarterNo = 0;
		
		var monthName = "";

		if ("quarterly" == type) {
			
			for (var i=0; i<4; i++) {
				
				pStartDate = new Date(nextStartDate.getTime());
				
				nextStartDate = new Date(nextStartDate.getFullYear(),nextStartDate.getMonth() + 3, 1, 0,0,0,0);  
		
				pEndDate = new Date(nextStartDate.getFullYear(),nextStartDate.getMonth(), nextStartDate.getDate() - 1, 23,59,59,999);
				
				quarterNo = i+1;
				
				//quarterNo = (Math.floor((pStartDate.getMonth() + 3) / 3)) ;
				
				name = "Quarter " + quarterNo + " - " + pStartDate.getFullYear();
				
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
			var months = [];
			months[1] = "January";
			months[2] = "February";
			months[3] = 'March';
			months[4] = 'April';
			months[5] = 'May';
			months[6] = 'June';
			months[7] = 'July';
			months[8] = 'August';
			months[9] = 'September';
			months[10] = 'October';
			months[11] = 'November';
			months[12] = 'December';
			

			for (var i=0; i<12; i++) {
				
				pStartDate = new Date(nextStartDate.getTime());
				
				nextStartDate = new Date(nextStartDate.getFullYear(),nextStartDate.getMonth() + 1, 1, 0,0,0,0); 
		
				pEndDate = new Date(nextStartDate.getFullYear(),nextStartDate.getMonth(), nextStartDate.getDate() - 1, 23,59,59,999);
				
				//monthName = "Month "  + ( pStartDate.getMonth()  + 1 );
				
				monthName = months[pEndDate.getMonth() + 1];
				
				name = monthName + " - " + pEndDate.getFullYear();
				
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
		var table = document.getElementById("tablePeriods");
		var rowCount = table.rows.length;
		rowCount = rowCount - 1 // count start from 0, also need to minus the Add Item link
		var row = "<tr>"
			+"<td><input type='hidden' name='periods["+rowCount+"].name' value='"+period.name+"'/>"			+period.name+"</td>"
			+"<td><input type='hidden' name='periods["+rowCount+"].startDate' value='"+formatDate(period.startDate)+"'/>"	+formatDate(period.startDate)+"</td>"
			+"<td><input type='hidden' name='periods["+rowCount+"].endDate' value='"+formatDate(period.endDate)+"'/>"	+formatDate(period.endDate)+"</td>"
			+"</tr>";
		jQuery("#tablePeriods tbody").append(row);
		
	}
	
</script>
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message
			code="${error.defaultMessage}" text="${error.defaultMessage}" /> </span>
</c:forEach>
<spring:bind path="command">
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
<form:form commandName="command"  method="post" cssClass="box" id="mainForm">
<input type="hidden" id="jsonPeriods" name="jsonPeriods" />
<spring:bind path="fiscalYear.id">
<input type="hidden" name="${status.expression}" value="${status.value}"/>
</spring:bind>
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
					<form:options items="${listStatus}" itemLabel="name" />
				</form:select> <form:errors path="fiscalYear.status"  cssClass="error" /></td>
		</tr>
		<tr>
			<td valing="top" colspan="2"><input type="button" id="btnCreateQuarter" value="Create Quaterly Periods" onclick="createPeriods('quarterly');"/> <input type="button" id="btnCreateMonthly" value="Create Monthly Periods" onclick="createPeriods('monthly');"/></td>
	</table>
	<br /> <input type="submit"  <c:if test="${editable==false}"> disabled="disabled"</c:if>
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
			<c:if test="${ not empty command.fiscalYear.periods  }">
				<c:forEach items="${command.fiscalYear.periods }" var="p" varStatus="varStatus">
					<tr>
						<td><input type='hidden' name='periods[${varStatus.index}].name' value='${p.name}'/>${p.name }</td>
						<td><input type='hidden' name='periods[${varStatus.index}].startDate' value='<openmrs:formatDate date="${p.startDate}" type="textbox" />'/><openmrs:formatDate date="${p.startDate}" type="textbox" /></td>
						<td><input type='hidden' name='periods[${varStatus.index}].endDate' value='<openmrs:formatDate date="${p.endDate}" type="textbox" />'/><openmrs:formatDate date="${p.endDate}" type="textbox" /></td>
					</tr>
				</c:forEach>
			</c:if>
			
		</tbody>
	</table>
	
	
</form:form>
<%@ include file="/WEB-INF/template/footer.jsp"%>