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

<openmrs:require privilege="View Income Receipt" otherwise="/login.htm"
	redirect="/module/account/main.form" />

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
	<spring:message code="accounting.incomeReceipt.manage" />
</h2>

<br /><br />
Filter by Account: <select onchange="selectAccount(this)">
 <option>--Select Account--</option>
 <c:forEach items="${accounts }" var="account">
 	<option value="${account.id }" <c:if test="${selectedAccount == account.id }">selected="selected"</c:if> >${account.name }</option>
 </c:forEach>
</select>

<script type="text/javascript">
	function selectAccount(this_) {
		window.location.href = "incomeReceiptItem.list?accountId="+jQuery(this_).val();
	}
</script>
<br /><br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message
			code="${error.defaultMessage}" text="${error.defaultMessage}" />
	</span>
</c:forEach>
<br />
<input type="button"
	value="<spring:message code='accounting.incomereceipt.add'/>"
	onclick="javascript:window.location.href='incomereceipt.form'" />
	<br />
	<br />
<c:choose>
	<c:when test="${not empty incomeReceipts}">
		<form method="post" onsubmit="return false" id="form">
					
			<span class="boxHeader"><spring:message
					code="accounting.incomeReceipt.list" />
			</span>
			<div class="box">
				<table cellpadding="5" cellspacing="0">
					<tr>
						<th>#</th>
						<th>Receipt No</th>
						<th><spring:message code="accounting.receipt.receiptDate" /></th>
						<th></th>
					</tr>
					<c:forEach items="${incomeReceipts}" var="incomeReceipt"
						varStatus="varStatus">
						<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
							<td><c:out
									value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }" />
							</td>
							<td><a href="javascript:window.location.href='incomereceipt.form?id=${incomeReceipt.receipt.id}'"> ${incomeReceipt.receipt.receiptNo }</a></td>
							<td><openmrs:formatDate date="${incomeReceipt.transactionDate}" type="textbox" /></td>
							<td>
							</td>
						</tr>
					</c:forEach>
					</form>
					<tr class="paging-container">
						<td colspan="7"><%@ include file="../paging.jsp"%></td>
					</tr>
				</table>
			</div>
			
	</c:when>
	<c:otherwise>
No Income Receipt found.
</c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/template/footer.jsp"%>
