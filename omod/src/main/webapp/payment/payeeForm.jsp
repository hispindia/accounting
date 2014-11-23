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
<openmrs:require privilege="Add/Edit Payee" otherwise="/login.htm"
	redirect="/module/accounting/account.list" />

<h2>
	<spring:message code="accounting.payee.addedit" />
</h2>

<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message
			code="${error.defaultMessage}" text="${error.defaultMessage}" /> </span>
</c:forEach>
<spring:bind path="payee">
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
<form:form commandName="payee" method="post" cssClass="box">
	<table>
		<tr>
			<td><spring:message code="general.name"/></td>
			<td>
				<form:input path="name"/>
			</td>
			<td><form:errors path="name"  cssClass="error" /></td>
		</tr>
			<tr>
			<td><spring:message code="general.description"/></td>
			<td>
				<form:input path="description"/>
			</td>
			<td><form:errors path="description"  cssClass="error" /></td>
		</tr>
		<tr>
			<td><spring:message code="general.retired" /></td>
			<td>
				<form:radiobutton path="retired" value="false" />NO 
				<form:radiobutton path="retired" value="true" />YES
			</td>
			<td><form:errors path="retired" cssClass="error" /></td>
		</tr>
	</table>
	<br /> <input type="submit"
		value="<spring:message code="general.save"/>"> <input
		type="button" value="<spring:message code="general.cancel"/>"
		onclick="cancel()">
</form:form>


<script>
	function cancel() {
		self.parent.tb_remove();
	}
</script>