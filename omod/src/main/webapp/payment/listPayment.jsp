<%--
 *  Copyright 2009 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Accounting module.
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

<openmrs:require privilege="View Payment" otherwise="/login.htm"
	redirect="/module/account/accountBalance.htm" />

<spring:message var="pageTitle" code="accounting.account.payment"
	scope="page" />

<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../includes/nav.jsp" %>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/moduleResources/account/styles/paging.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/moduleResources/account/scripts/paging.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/moduleResources/account/scripts/jquery/jquery-1.4.2.min.js"></script>
<h2>
	<spring:message code="accounting.account.payment" />
</h2>

<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message
			code="${error.defaultMessage}" text="${error.defaultMessage}" />
	</span><
</c:forEach>

<input type="button"
	value="<spring:message code='accounting.payment.add'/>"
	onclick="javascript:window.location.href='payment.form'" />

<br /><br />

<input type="hidden" id="pageSize" value="${pagingUtil.pageSize }"/>
<input type="hidden" id="curPage" value="${pagingUtil.currentPage }"/>

Select Account : <select onchange="selectAccount(this)">
	<option value="">---Select All---</option>
	<c:forEach items="${accounts}" var="account">
		<option value="${account.id}"  <c:if test="${accountId == account.id}">selected="true"</c:if> >${account.name}</option>
	</c:forEach>
</select>
<script>
function selectAccount(this_) {
	var accountId = jQuery(this_).val();
	var pageSize = jQuery("#pageSize").val();
	var curPage = jQuery("#curPage").val();
	window.location.href = "payment.list?accountId="+accountId+"&pageSize="+pageSize;
}
</script>
<br />
<c:choose>
	<c:when test="${not empty payments}">
			<span class="boxHeader"><spring:message
					code="accounting.payment.list" />
			</span>
			<div class="box">
				<table cellpadding="5" cellspacing="0">
					<tr>
						<th>#</th>
						<th><spring:message code="accounting.account" /></th>
						<th><spring:message code="accounting.payee" /></th>
						<th><spring:message code="accounting.payment.date" /></th>
						<th>Payable Amount</th>
						<th>Status</th>
						<th></th>
					</tr>
					<c:forEach items="${payments}" var="payment"
						varStatus="varStatus">
						<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } ' 
							<c:if test="${payment.status == 'DELETED'}"> style="text-decoration:line-through;"</c:if> >
							<td><c:out
									value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }" />
							</td>
							<td><a href="payment.form?id=${payment.id}">${payment.account.name }</td>
							<td>${payment.payee.name }</td>
							<td><openmrs:formatDate type="textbox" date="${payment.paymentDate }"/></td>
							<td>${payment.payableAmount }</td>
							<td>${payment.status}</td>
						</tr>
					</c:forEach>
					<tr class="paging-container">
						<td colspan="7"><%@ include file="../paging.jsp"%></td>
					</tr>
				</table>
			</div>
			<script>
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
No Payment found.
</c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/template/footer.jsp"%>
