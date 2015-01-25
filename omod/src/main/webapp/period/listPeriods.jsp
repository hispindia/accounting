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

<openmrs:require privilege="View Period" otherwise="/login.htm"
	redirect="/module/account/period.list" />

<spring:message var="pageTitle" code="accounting.account.manage"
	scope="page" />

<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../includes/nav.jsp" %>

<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/moduleResources/accounting/styles/paging.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/moduleResources/accounting/scripts/paging.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/moduleResources/accounting/scripts/jquery/jquery-1.4.2.min.js"></script>
<h2>
	<spring:message code="accounting.period.manage" />
</h2>

<br />
<script>
	function selectYear(this_) {
		var year = jQuery(this_).val();
		
		window.location.href = "period.list?yearId="+year;
	}

</script>

<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message
			code="${error.defaultMessage}" text="${error.defaultMessage}" />
	</span><
</c:forEach>
<select onChange="selectYear(this)" >
		<option value="">---Select Fiscal Year---</option>
		<c:forEach items="${listYears}" var="year">
			<option value="${year.id}">${year.name }</option>
		</c:forEach>
</select>

<br />
<br />
<c:choose>
	<c:when test="${not empty periods}">
		<form method="post" onsubmit="return false" id="form">
			
		<!-- 	<input type="button" onclick="checkValue()"
				value="<spring:message code='accounting.account.deleteselected'/>" /> -->
			<span class="boxHeader"><spring:message
					code="accounting.period.list" />
			</span>
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
					<c:forEach items="${periods}" var="period"
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
								<c:if test="${period.open  && period.closable}">
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
function checkValue()
{
	var form = jQuery("#form");
	if( jQuery("input[type='checkbox']:checked",form).length > 0 ) 
		form.submit();
	else{
		alert("Please choose items for deleting");
		return false;
	}
}</script>
	</c:when>
	<c:otherwise>
No Period found.
</c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/template/footer.jsp"%>
