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
	<spring:message code="accounting.fiscalperiod.close" />
</h2>

<script>
	function selectYear(this_) {
		var year  = jQuery(this_).val();
		jQuery("#periodContainer").load("getPeriods.htm?yearId="+year);
	}
</script>
<form method="post" class="box">
<table>
	<tr>
		<td>Close Period</td>
		<td>  ${period.name }</td>		
	</tr>
	
	
	<tr>
		<td>Next Period</td>
		<c:choose>
		<c:when test="${isLastPeriod}">
		Year: <select onclick="selectYear(this)">
				<c:forEach items="${listYears }" var="y">
						<option value="${y.id}" >${y.name }</option>
				</c:forEach>
				</select>
		Period: <div id="periodContainer"></div>
		</c:when>
		<c:otherwise>
		<td>
			
			 <select name="nextPeriodId">
				<option value="">--Select Next Period---</option>
				<c:forEach items="${listPeriods}" var="p">
					<option value="${p.id}" >${p.name }</option>
				</c:forEach>
			</select>
		</td>
		</c:otherwise>
		</c:choose>
	</tr>
	<tr>
		<td>Reset Balance to zero?</td>
		<td>
			<input type="radio" name="resetBalance" value="true">Yes
			<input type="radio" name="resetBalance" value="false" checked="checked">No
		</td>
	</tr>
</table>
<input type="submit" value="Submit" /> <input type="button" value="Cancel" onclick="window.location.href='period.list'" />
<br>
</form>
<br>
<span class="boxHeader">Income Accounts</span>
	<table cellpadding="5" cellspacing="0" class="box">
		<tr>
			<th>#</th>
			<th><spring:message code="general.name" /></th>
			<th><spring:message code="accounting.accountType" /></th>
			<th><spring:message code="accounting.openingBalance" /></th>
			<th><spring:message code="accounting.ledgerBalance" /></th>
			<th><spring:message code="accounting.availableBalance" /></th>
			<th><spring:message code="accounting.closingBalance" /></th>
			<th><spring:message code="accounting.updatedDate" /></th>
			<th></th>
		</tr>
		<c:forEach items="${accBalances}" var="account">
			<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
				<td><c:out
						value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }" />
				</td>
				<td>${account.account.name}</td>
				<td>${account.account.accountType }</td>
				<td>${account.openingBalance}</td>
				<td>${account.ledgerBalance}</td>
				<td>${account.availableBalance}</td>
				<td>${account.availableBalance}</td>
				<td><openmrs:formatDate date="${account.updatedDate}"
						type="textbox" format="dd/mm/yyyy hh:mm"/>
				</td>
				<td>
				</td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<span class="boxHeader">Expense Accounts</span>
	<table cellpadding="5" cellspacing="0" class="box">
		<tr>
			<th>#</th>
			<th><spring:message code="general.name" /></th>
			<th><spring:message code="accounting.accountType" /></th>
			<th><spring:message code="accounting.newAIE" /></th>
			<th><spring:message code="accounting.cummulativeAIE" /></th>
			<th><spring:message code="accounting.currentPayment" /></th>
			<th><spring:message code="accounting.cummulativePayment" /></th>
			<th><spring:message code="accounting.availableBalance" /></th>
			<th><spring:message code="accounting.status" /></th>
			<th><spring:message code="accounting.updatedDate" /></th>
			<th></th>
		</tr>
		<c:forEach items="${expBalances}" var="account" varStatus="varStatus">
			<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
				<td><c:out
						value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }" />
				</td>
				<td>${account.account.name}</td>
				<td>${account.account.accountType }</td>
				<td>${account.newAIE}</td>
				<td>${account.cummulativeAIE}</td>
				<td>${account.currentPayment}</td>
				<td>${account.cummulativePayment}</td>
				<td>${account.availableBalance}</td>
				<td>${account.status}</td>
				<td><openmrs:formatDate date="${account.updatedDate}"
						type="textbox" format="dd/mm/yyyy hh:mm" /></td>
				<td></td>
			</tr>
		</c:forEach>
		<tr class="paging-container">
			<td colspan="7"><%@ include file="../paging.jsp"%></td>
		</tr>
	</table>




<%@ include file="/WEB-INF/template/footer.jsp"%>