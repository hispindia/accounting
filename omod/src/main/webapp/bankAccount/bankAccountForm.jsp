<%--
 *  Copyright 2009 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Accounting module.
 *
 *  Accounting module is free software: you can redistribute it and/or modify
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
<openmrs:require privilege="Add/Edit BankAccount" otherwise="/login.htm"
	redirect="/module/accounting/bankAccount.list" />

<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../includes/nav.jsp"%>
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />


	<p>
<b><a href="bankStatement.list">List Bank Statement</a></b>&nbsp; | &nbsp;
<b><a href="bankStatement.form">Add Bank Statement</a></b>&nbsp; | &nbsp;
<b><a href="bankAccount.list">List Bank Account</a></b>&nbsp; | &nbsp;
<b><a href="bankAccount.form">Add Bank Account</a></b>
</p>
	
<h2>
	<spring:message code="accounting.bankAccount.addedit" />
</h2>

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
<form:form commandName="command" method="post" cssClass="box">
	<table>
		
			<tr>
			<td valign="top"><spring:message code="accounting.accountName" />
			</td>
			<td><spring:bind path="accountName">
					<input type="text" name="${status.expression}"
						value="${status.value}" size="35" />
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				</spring:bind></td>
		</tr>
		
		
			<tr>
			<td valign="top"><spring:message code="accounting.accountNumber" />
			</td>
			<td><spring:bind path="accountNumber">
					<input type="text" name="${status.expression}"
						value="${status.value}" size="35" />
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				</spring:bind></td>
		</tr>
		
		<tr>
			<td valign="top"><spring:message code="accounting.bankName" />
			</td>
			<td><spring:bind path="bankName">
					<input type="text" name="${status.expression}"
						value="${status.value}" size="35" />
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				</spring:bind></td>
		</tr>
		
		<tr>
			<td valign="top"><spring:message code="accounting.bankCode" />
			</td>
			<td><spring:bind path="bankCode">
					<input type="text" name="${status.expression}"
						value="${status.value}" size="35" />
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				</spring:bind></td>
		</tr>
		
		<tr>
			<td valign="top"><spring:message code="accounting.bankBranch" />
			</td>
			<td><spring:bind path="bankBranch">
					<input type="text" name="${status.expression}"
						value="${status.value}" size="35" />
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				</spring:bind></td>
		</tr>
		
		<tr>
			<td valign="top"><spring:message code="general.description" />
			</td>
			<td><spring:bind path="description">
					<input type="text" name="${status.expression}"
						value="${status.value}" size="35" />
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				</spring:bind></td>
		</tr>

		

	</table>
	<br />
	<input type="submit" value="<spring:message code="general.save"/>">
	<input type="button" value="<spring:message code="general.cancel"/>"
		onclick="javascript:window.location.href='bankAccount.list'">
</form:form>
<%@ include file="/WEB-INF/template/footer.jsp"%>