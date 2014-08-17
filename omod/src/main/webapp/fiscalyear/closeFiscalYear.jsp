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
<openmrs:require privilege="Add/Edit Account" otherwise="/login.htm"
	redirect="/module/accounting/main.form" />

<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../includes/nav.jsp" %>
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/accounting/scripts/jquery/jquery.validate.min.js"></script>
<h2>
	<spring:message code="accounting.fiscalyear.close" />
</h2>
<c:choose>
<c:when test="${hasOpenPeriod}">
	Please close all Fiscal Periods of this year before proceed.
</c:when>
<c:otherwise>
<form class="box">
<input type="hidden" name="closeYearId" value="${fiscalYear.id }"/>
<table>
	<tr>
		<td>Start Date</td>
		<td><openmrs:formatDate date="${fiscalYear.startDate}" type="textbox" /></td>
	</tr>
	<tr>
		<td>End Date</td>
		<td><openmrs:formatDate date="${fiscalYear.startDate}" type="textbox" /></td>
	</tr>
	<c:choose>
	<c:when test="${hasNextYear}">
	<tr>
		<td>Next Fiscal Year</td>
		<td>
			<select name="nextFiscalYearId">
				<option value="">--Select Next Year---</option>
				<c:forEach items="${listFiscalYear}" var="y">
					<option value="${y.id}" >${y.name}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	</c:when>
	<c:otherwise>
		<tr>
			<td>Auto create next year</td>
			<td>Yes <input type="radio" name="createNextYear" value="y"> 
				No  <input type="radio" name="createNextYear" value="n">
			</td>
		</tr>
	</c:otherwise>
	</c:choose>
	<input type="submit" value="Submit"/>
</table>



</form>

</c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/template/footer.jsp"%>