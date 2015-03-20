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
<openmrs:require privilege="Add/Edit BankStatement" otherwise="/login.htm"
	redirect="/module/accounting/bankStatement.list" />

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
	<spring:message code="accounting.bankStatement.addedit" />
</h2>
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message	code="${error.defaultMessage}" text="${error.defaultMessage}" /> </span>
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
			<td><spring:message code="accounting.receiptType" /><em>*</em></td>
			<td><form:radiobutton path="receiptType" value="Banking" />Banking &nbsp;&nbsp;&nbsp;
				<form:radiobutton path="receiptType" value="NHIF Receipts" />NHIF Receipts &nbsp;&nbsp;&nbsp;
				<form:radiobutton path="receiptType" value="Other Debtor Receipts" />Other Debtor Receipts	</td>
				<form:errors path="receiptType" cssClass="error" />
			</td>
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

		<tr>
			<td valign="top"><spring:message code="accounting.dateFrom" /><em>*</em>
			</td>
			<td><spring:bind path="dateFrom">
				<input type="text" name="${status.expression}"
						value="${status.value}" size="35" onfocus="showCalendar(this)" id="dateFrom"/>
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				</spring:bind></td>
		</tr>
		
		<tr>
			<td valign="top"><spring:message code="accounting.dateTo" /><em>*</em>
			</td>
			<td><spring:bind path="dateTo">
				<input type="text" name="${status.expression}"
						value="${status.value}" size="35" onfocus="showCalendar(this)" id="dateTo"/>
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				</spring:bind></td>
		</tr>
		
		<tr>
			<td valign="top"><spring:message code="accounting.bankAccount" /><em>*</em>
			</td>
			<td>
				<form:select path="bankAccount">
							<form:option value="0" label="--Please Select--" />
							<form:options items="${bankAccounts}" itemLabel="accountName" itemValue="id"/>
						</form:select>
						<form:errors path="bankAccount" cssClass="error" />
				</td>
		</tr>
			<tr>
			<td valign="top"><spring:message code="accounting.checkNumber" />
			</td>
			<td><spring:bind path="checkNumber">
					<input type="text" name="${status.expression}"
						value="${status.value}" size="35" />
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				</spring:bind></td>
		</tr>
		<tr>
			<td valign="top"><spring:message code="accounting.amount" /><em>*</em>
			</td>
			<td><spring:bind path="amount">
					<input type="text" name="${status.expression}"
						value="${status.value}" size="35" />
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				</spring:bind></td>
		</tr>
		<tr>
			<td><spring:message code="accounting.bankstatement.type" /><em>*</em></td>
			<td><form:radiobutton path="type" value="CASH" />CASH
				<form:radiobutton path="type" value="EFT" />EFT</td>
			<form:errors path="type" cssClass="error" />
			</td>
		</tr>
	</table>
	<br />
	<input type="submit" value="<spring:message code="general.save"/>">
	<input type="button" value="<spring:message code="general.cancel"/>"
		onclick="javascript:window.location.href='bankStatement.list'">
</form:form>
<%@ include file="/WEB-INF/template/footer.jsp"%>