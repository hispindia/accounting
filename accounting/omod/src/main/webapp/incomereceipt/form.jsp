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
		<td></td>
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
var arrReceiptItems = new Array();
jQuery(document).ready(function(){
	  var availableTags = jQuery("#accounts").val().split(",");
	   
	jQuery( "#itemSelectAccount" ).autocomplete({
	     source: availableTags
	   });
})

function addItem() {
	resetItemForm();
	tb_show("Add Receipt Item","#TB_inline?height=150&width=300&inlineId=divItemForm&modal=true",null);
}

function generateItem() {
	
	var acc = new Object();
	acc.accountName = jQuery("#itemSelectAccount").val();
	acc.description = jQuery("#itemDescription").val()
	acc.type = jQuery("#itemType").val()
	acc.chequeNumber = jQuery("#itemChequeNo").val()
	acc.amount = jQuery("#itemAmount").val()
	
	// add item to global array
	arrReceiptItems.push(acc);
	
	var txtAccount 		= 	"<td>"	+	acc.accountName	+	"</td>";
	var txtDescription 	= 	"<td>"	+	acc.description	+	"</td>"
	var txtType 		= 	"<td>"	+	acc.type		+	"</td>"
	var txtAmount 		= 	"<td>"	+	acc.amount		+	"</td>";
	var txtChequeNo 	= 	"<td>"	+	acc.chequeNo	+	"</td>";
	var txtAction		= 	"<td><input type='button' value='Delete' onclick='deleteItem(this)'/>&nbsp;<input type='button' value='Edit' onclick='editItem()'/></td>";
	return "<tr>" + txtAccount + txtDescription + txtType + txtChequeNo + txtAmount + txtAction +"</tr>";
	
}

function resetItemForm() {
	jQuery("#itemSelectAccount").val("");
	jQuery("#itemDescription").val("");
	jQuery("#itemType").val("");
	jQuery("#itemAmount").val("");
	jQuery("#itemChequeNo").val("");
}

function deleteItem(this_) {
	jQuery(this_).parents("tr").get(0).remove();
}
function saveItem() {
	
	jQuery("#tableReceiptItem tbody").append(generateItem());
	self.parent.tb_remove();
}


function closePopup() {
	 self.parent.tb_remove();
}

function submitForm() {
	jQuery("#jsonReceiptItems").val(JSON.stringify(arrReceiptItems))
	jQuery("#mainForm").submit();
}

</script>
	
	
	<div clcass="box">
	<br><p><b>Receipt Item</b></p>
	<br><input type="button" value="Add New Item" onclick="addItem()"/><br>
<table id="tableReceiptItem">
	<thead>
		<th>Account</th>
		<th>Description</th>
		<th>Type</th>
		<th>Cheuqe number</th>
		<th>Amount</th>
		<th></th>
	</thead>
	<tbody>
		<c:if test="${not empty incomeReceipt.receiptItems }">
			<c:forEach items="${incomeReceipt.receiptItems }" var="item">
				<tr>
					<td>${item.account.name}</td>
					<td>${item.description}</td>
					<td>${item.type}</td>
					<td>${item.chequeNumber}</td>
					<td>${item.amount}</td>
					<td>
						<input type='button' value='Delete' onclick='deleteItem(this)'/>&nbsp;
						<input type='button' value='Edit' onclick='editItem()'/>
					</td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>
</div>

<div id="divItemForm" style="display: none">
	<span><strong>  Add Receipt Item </strong></span>
	<table id="tableItemForm">
		<tr><td>Account</td> <td><input id="itemSelectAccount"/></td></tr>
		<tr><td>Description</td> <td><input type="text" id="itemDescription"/></td></tr>
		<tr><td>Type</td> 
			<td>
				<select id="itemType">
					<option value="cash">CASH</option>
					<option value="visa">VISA</option>
					<option value="master">MASTER</option>
				</select>
			</td>
		</tr>
		<tr><td>Cheuqe number</td> <td><input type="text" id="itemChequeNo"/></td></tr>
		<tr><td>Amount</td> <td><input type="text" id="itemAmount"/></td></tr>
	</table>
<span>	<input type="button" value=" Save " onclick="saveItem()"/> &nbsp;
	<input type="button" value=" Cancel " onclick="closePopup()"/></span>
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