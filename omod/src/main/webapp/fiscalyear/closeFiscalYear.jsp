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
<form class="box" method="post">
<input type="hidden" name="closeYearId" value="${fiscalYear.id }"/>
<table>
	<tr>
		<td>Start Date</td>
		<td><openmrs:formatDate date="${fiscalYear.startDate}" type="textbox" /></td>
	</tr>
	<tr>
		<td>End Date</td>
		<td><openmrs:formatDate date="${fiscalYear.endDate}" type="textbox" /></td>
	</tr>
	<c:choose>
	<c:when test="${hasNextYear}">
	<tr>
		<td>Next Fiscal Year</td>
		<td>
			<select name="nextYearId">
				<option value="">--Select Next Year---</option>
				<c:forEach items="${listFiscalYear}" var="y">
					<option value="${y.id}" >${y.name}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
		<input type="submit" value="Submit"/>
	</c:when>
	<c:otherwise>
	<tr><td colspan="2">	<span class="error">Please create next Fiscal Year.</span></td></tr>
	</c:otherwise>
	</c:choose>

</table>



</form>



</c:otherwise>
</c:choose>
<br/>
<span class="boxHeader">List Periods</span>
<div class="box">
<table cellpadding="5" cellspacing="0">
	<tr>
		<th>#</th>
		<th><spring:message code="general.name" />
		</th>
		<th><spring:message code="accounting.startDate" />
		</th>
		<th><spring:message code="accounting.endDate" />
		</th>
		<th>Status</th>
		<th></th>
	</tr>
	<c:forEach items="${fiscalYear.periods}" var="period"
		varStatus="varStatus">
		<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
			<td><c:out
					value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }" />
			</td>
			<td>${period.name}</td>
			<td><openmrs:formatDate date="${period.startDate}" type="textbox" /></td>
			<td><openmrs:formatDate date="${period.endDate}" type="textbox" /></td>
			<td>${period.status }</td>
			
			<td>
				<c:if test="${!period.closed }">
				<input type="button" value="Close" onclick="closePeriod(${period.id})">
				<!--<input type="checkbox" name="ids"
				value="${period.id}" />
				-->
				</c:if>
			</td>
		</tr>
	</c:forEach>
	</form>
	<tr class="paging-container">
		<td colspan="7"><%@ include file="../paging.jsp"%></td>
	</tr>
</table>
</div>


<script>
			
function closePeriod(id) {
	if (confirm("Are you sure to close this period? You will not be able to add or edit any transaction within a closed period.")) {
		window.location.href = "closePeriod.htm?periodId="+id;
	}
}
</script>
<%@ include file="/WEB-INF/template/footer.jsp"%>