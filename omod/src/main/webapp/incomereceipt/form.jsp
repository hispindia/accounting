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
<openmrs:require privilege="Add/Edit Income Receipt" otherwise="/login.htm"
	redirect="/module/accounting/main.form" />

<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../includes/nav.jsp" %>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/moduleResources/accounting/scripts/jquery/css/thickbox.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/accounting/scripts/jquery/jquery.ui.autocomplete.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/accounting/scripts/jquery/jquery.thickbox.js"></script>
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />

<h2>
	<spring:message code="accounting.incomereceipt.manage" />
</h2>
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message
			code="${error.defaultMessage}" text="${error.defaultMessage}" /> </span>
</c:forEach>
<spring:bind path="incomeReceipt">
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
<form method="post" class="box" id="mainForm">
<input type="hidden" id="jsonReceiptItems" name="jsonReceiptItems" />
<input type="hidden" id="accounts"  value="${accounts}" />
<input type="hidden" id="incomeReceiptId" value="${incomeReceipt.id}"/>
	<table>
		<tr>
			<td><spring:message code="accounting.receiptNumber"/></td>
			<td><form:input path="incomeReceipt.receiptNo" size="35"></form:input></td>
			<td><form:errors path="incomeReceipt.receiptNo"  cssClass="error" /></td>
		</tr>
		<tr>
			<td><spring:message code="general.description" /></td>
			<td><form:textarea path="incomeReceipt.description" rows="3" cols="35"></form:textarea></td>
			<td><form:errors path="incomeReceipt.description"  cssClass="error" /></td>
		</tr>
		<tr>
			<td valign="top"><spring:message code="accounting.receipt.receiptDate" />
			</td>
			<td><spring:bind path="incomeReceipt.receiptDate">
				<input type="text" name="${status.expression}"
						value="${status.value}" size="35" onfocus="showCalendar(this)" id="receiptDate"/>
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				</spring:bind></td>
		</tr>
		<tr>
			<td valign="top"><spring:message code="accounting.voided" />
			</td>
			<td><form:radiobutton path="incomeReceipt.voided" value="false"/>NO &nbsp;&nbsp;
				<form:radiobutton path="incomeReceipt.voided" value="true"/>YES		</td>
				<td><form:errors path="incomeReceipt.voided"  cssClass="error" /></td>
		</tr>
		<tr>
	</table>
	<br /> <input type="submit"		value="<spring:message code="general.save"/> " onclick="submitForm()"> <input
		type="button" value="<spring:message code="general.cancel"/>"
		onclick="javascript:window.location.href='incomereceipt.list'">
	
<!---------------------------------->
<!-- SHOW RECEIPT ITEM -->	
<!---------------------------------->
	
<script>
jQuery(document).ready(function(){
	var receiptId = jQuery("#incomeReceiptId").val();
	if (!receiptId) {
		// Disable add receipt item
		jQuery("#receiptItemBox").hide();
	}
});

function addItem() {
	var receiptId = jQuery("#incomeReceiptId").val();
	//resetItemForm();
	//tb_show("Add Receipt Item","#TB_inline?height=150&width=300&inlineId=divItemForm&modal=true",null);
	tb_show("Edit Receipt Item","incomeReceiptItem.form?receiptId="+receiptId+"&keepThis=true&TB_iframe=true&height=250&width=400",null);
}

function deleteItem(_this,id) {
	if (confirm("Are you sure to delete this Receipt Item ?")) {
		
		jQuery.post( "incomeReceiptItem.form",{receiptItemId : id, action : "delete"}, function( data ) {
			if (data == "success") {
				jQuery(_this).parents("tr").get(0).remove();
			} else {
				alert("Can not delete Receipt Item." + data);
			}
		});
	}
	
}

function editItem(id) {
	var receiptId = jQuery("#incomeReceiptId").val();
	tb_show("Add/Edit Receipt Item","incomeReceiptItem.form?id="+id+"&receiptId="+receiptId+"&keepThis=true&TB_iframe=true&height=250&width=400",null);
}


function submitForm() {
	jQuery("#mainForm").submit();
}

</script>
	
	
	<div  id="receiptItemBox">
	<br><p><span class="boxHeader"><b>Receipt Item</b></span></p>
	<input style="margin:5px" type="button" value="Add New Item" onclick="addItem()"/>
<table class="box" id="tableReceiptItem">
	<thead>
		<th>Account</th>
		<th>Description</th>
		<th>Type</th>
		<th>Cheuqe number</th>
		<th style="text-align:right">Amount</th>
		<th></th>
	</thead>
	<tbody>
		<c:if test="${not empty incomeReceipt.receiptItems }">
			<c:forEach items="${incomeReceipt.receiptItems }" var="item">
				<tr <c:if test="${item.voided}"> style="text-decoration:line-through;"</c:if>>
					<td><a href="#" onclick='editItem(${item.id})'>${item.account.name}</a></td>
					<td>${item.description}</td>
					<td>${item.type}</td>
					<td>${item.chequeNumber}</td>
					<td align="right">${item.amount}</td>
					<td align="center">
						<c:if test="${!item.voided}"><input type='button' value='Delete' onclick='deleteItem(this,${item.id})'/>&nbsp;</c:if>
					</td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>
</div>

<!---
description
account
amount
cheque_number
type
transaction_date
	-->		
	
</form>
<%@ include file="/WEB-INF/template/footer.jsp"%>