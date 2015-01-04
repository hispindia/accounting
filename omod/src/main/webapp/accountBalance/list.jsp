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

<openmrs:require privilege="View Account Balance" otherwise="/login.htm"
	redirect="/module/account/accountBalance.htm" />

<spring:message var="pageTitle" code="accounting.account.balance"
	scope="page" />

<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../includes/nav.jsp"%>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/moduleResources/account/styles/paging.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/moduleResources/account/scripts/paging.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/moduleResources/account/scripts/jquery/jquery-1.4.2.min.js"></script>
<h2>
	<spring:message code="accounting.account.balance" />
</h2>

<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message
			code="${error.defaultMessage}" text="${error.defaultMessage}" /> </span><
</c:forEach>

<script>
	function search() {
		var accountType = jQuery("#comboAccountType").val();
		var period = jQuery("#comboPeriod").val();
		window.location.href = "accountBalance.htm?type=" + accountType+"&period="+period;
	}
</script>
<div class="box">
<table>
<tr><td>
Account Type:</td><td><select id="comboAccountType">
	<option value="INCOME"
		<c:if test="${type =='INCOME'}">selected="selected"</c:if>>INCOME</option>
	<option value="EXPENSE"
		<c:if test="${type =='EXPENSE'}">selected="selected"</c:if>>EXPENSE</option>
</select>
</td></tr>
<tr><td>


Fiscal Period:</td><td><select id="comboPeriod">
					<option value="">--Select Period---</option>
					<c:forEach items="${periods}" var="period">
						<option value="${period.id }" <c:if test="${periodId == period.id }">selected="selected"</c:if>>${period.name}</option>
					</c:forEach>
				</select>
				
</td></tr>
<tr><td colspan="2" align="left">
<input type="button" value="Search" onclick="search()">		
</td></tr>
</table>
</div>
<c:choose>
	<c:when test="${not empty accounts}">
		<c:choose>
		
			 <c:when	test="${type == 'INCOME'}">
					<!-- LIST INCOME ACCOUNTS -->	
					<span class="boxHeader"><spring:message
								code="accounting.account.list" /></span>
			<div class="box">
				<table cellpadding="5" cellspacing="0">
					<tr>
						<th>#</th>
						<th><spring:message code="general.name" /></th>
						<th><spring:message code="accounting.accountType" /></th>
						<th><spring:message code="accounting.openingBalance" /></th>
						<th><spring:message code="accounting.ledgerBalance" /></th>
						<th><spring:message code="accounting.availableBalance" /></th>
						<th><spring:message code="accounting.closingBalance" /></th>
						<th><spring:message code="accounting.status" /></th>
						<th><spring:message code="accounting.updatedDate" /></th>
						<th></th>
					</tr>
					<c:forEach items="${accounts}" var="account" varStatus="varStatus">
						<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
							<td><c:out
									value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }" />
							</td>
							<td><a
								href="javascript:window.location.href='account.form?id=${account.account.id}'">${account.account.name}</a>
							</td>
							<td>${account.account.accountType }</td>
							<td>${account.openingBalance}</td>
							<td>${account.ledgerBalance}</td>
							<td>${account.availableBalance}</td>
							<td>${account.closingBalance}</td>
							<td>${account.status}</td>
							<td><openmrs:formatDate date="${account.updatedDate}"
									type="textbox" format="dd/MM/yyyy hh:mm" /></td>
							<td></td>
						</tr>
					</c:forEach>
					<tr class="paging-container">
						<td colspan="7"><%@ include file="../paging.jsp"%></td>
					</tr>
				</table>
			</div>
	</c:when>
	<c:otherwise>
		<span class="boxHeader"><spring:message
					code="accounting.account.list" /></span>
		<!-- LIST EXPENSE ACCOUNTS -->
		<div class="box">
			<table cellpadding="5" cellspacing="0">
				<tr>
					<th>#</th>
					<th><spring:message code="general.name" /></th>
					<th><spring:message code="accounting.accountType" /></th>
					<th><spring:message code="accounting.newAIE" /></th>
					<th><spring:message code="accounting.cummulativeAIE" /></th>
					<th><spring:message code="accounting.committed" /></th>
					<th><spring:message code="accounting.currentPayment" /></th>
					<th><spring:message code="accounting.cummulativePayment" /></th>
					<th><spring:message code="accounting.ledgerBalance" /></th>
					<th><spring:message code="accounting.availableBalance" /></th>
				<!-- 	<th><spring:message code="accounting.status" /></th> -->
				<!--	<th><spring:message code="accounting.updatedDate" /></th>-->
					<th></th>
				</tr>
				<c:forEach items="${accounts}" var="account" varStatus="varStatus">
					<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
						<td><c:out
								value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }" />
						</td>
						<td><a
							href="javascript:window.location.href='account.form?id=${account.account.id}'">${account.account.name}</a>
						</td>
						<td>${account.account.accountType }</td>
						<td>${account.newAIE}</td>
						<td>${account.cummulativeAIE}</td>
						<td>${account.totalCommitted}</td>
						<td>${account.currentPayment}</td>
						<td>${account.cummulativePayment}</td>
						<td>${account.ledgerBalance}</td>
						<td>${account.availableBalance}</td>
					<!--	<td>${account.status}</td>-->
					<!--	<td><openmrs:formatDate date="${account.updatedDate}"
								type="textbox" format="dd/mm/yyyy hh:mm" /></td>-->
						<td></td>
					</tr>
				</c:forEach>
				<tr class="paging-container">
					<td colspan="7"><%@ include file="../paging.jsp"%></td>
				</tr>
			</table>
		</div>

	</c:otherwise>
</c:choose>

<script>
	function checkValue() {
		var form = jQuery("#form");
		if (jQuery("input[type='checkbox']:checked", form).length > 0)
			form.submit();
		else {
			alert("Please choose items for deleting");
			return false;
		}
	}
</script>
</c:when>
<c:otherwise>
No Account Balance found.
</c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/template/footer.jsp"%>
