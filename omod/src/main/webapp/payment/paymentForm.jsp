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
<openmrs:require privilege="Add/Edit Payment" otherwise="/login.htm"
	redirect="/module/accounting/account.list" />

<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../includes/nav.jsp" %>


<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/moduleResources/accounting/scripts/jquery/css/thickbox.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/accounting/scripts/jquery/jquery.ui.autocomplete.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/accounting/scripts/jquery/jquery.thickbox.js"></script>
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />

<h2>
	<spring:message code="accounting.payment.addedit" />
</h2>

<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message
			code="${error.defaultMessage}" text="${error.defaultMessage}" /> </span>
</c:forEach>
<spring:bind path="payment">
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
<form:form method="post" commandName="payment" cssClass="box">
	<table>
		<tr>
			<td><spring:message code="accounting.account" /><em>*</em></td>
			<td><input type="text" id="comboAccount"/><form:hidden path="account" id="hiddenAccount"/>
			<input type="hidden" id="hAccountName" value="${payment.account.name}"/>
			</td>
			<td></td>
		</tr>
		<tr>
			<td><spring:message code="accounting.payment.date"/><em>*</em></td>
			<td>
				<form:input path="paymentDate"    onfocus="showCalendar(this)" id="paymentDate"/>
			</td>
			<td><form:errors path="paymentDate"  cssClass="error" /></td>
		</tr>
		<tr>
			<td><spring:message code="accounting.payment.aie"/></td>
			<td><input type="button" value="Get AIE" onclick="getAIE()"/> <span  id="paymentAIE" >${accountBudget}</span>
			<form:hidden path="totalAIE" id="totalAIE"/></td>
			<input type="hidden" id="hPayeeName" value="${payment.payee.name }"/>
		</tr>
		<tr>
			<td><spring:message code="accounting.payee" /><em>*</em></td>
			<td><input type="text" id="comboPayee"/><form:hidden path="payee" id="hiddenPayee"/>
			</td>
			<td><input type="button" value="Add Payee" onclick="addPayee()"/></td>
		</tr>
		
		
		<tr>
			<td><spring:message code="accounting.orderReferenceNumber"/></td>
			<td><form:input path="referenceOrderNo"/></td>
			<td><form:errors path="referenceOrderNo"  cssClass="error" /></td>
		</tr>
		<tr>
			<td><spring:message code="accounting.commitmentNo"/></td>
			<td><form:input path="commitmentNo"/></td>
			<td><form:errors path="commitmentNo"  cssClass="error" /></td>
		</tr>
		<tr>
			<td><spring:message code="accounting.voucherNo"/></td>
			<td><form:input path="voucherNo"/></td>
			<td><form:errors path="voucherNo"  cssClass="error" /></td>
		</tr>
		<tr>
			<td><spring:message code="accounting.checkNumber"/></td>
			<td><form:input path="checkNumber"/></td>
			<td><form:errors path="checkNumber"  cssClass="error" /></td>
		</tr>
		<tr>
			<td><spring:message code="accounting.payableAmount"/><em>*</em></td>
			<td><form:input path="payableAmount"/></td>
			<td><form:errors path="payableAmount"  cssClass="error" /></td>
		</tr>
		<tr>
			<td><spring:message code="accounting.commitmentAmount"/><em>*</em></td>
			<td><form:input path="commitmentAmount"/></td>
			<td><form:errors path="commitmentAmount"  cssClass="error" /></td>
		</tr>
		<tr>
			<td><spring:message code="accounting.actualPayment"/></td>
			<td><form:input path="actualPayment"  /></td>
			<td><form:errors path="actualPayment"  cssClass="error" /></td>
		</tr>
		<tr>
			<td><spring:message code="accounting.status"/></td>
			<td><form:select path="status">
				<form:options items="${paymentStatuses}"  itemLabel="name"/>
			</form:select></td>
			<td><form:errors path="status"  cssClass="error" /></td>
		</tr>
		<tr>
			<td><spring:message code="accounting.note"/></td>
			<td><form:textarea path="note"/></td>
			<td><form:errors path="note"  cssClass="error" /></td>
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
		onclick="javascript:window.location.href='payment.list'">
		<input type="button" value="Print Payment Form" onclick="printForm()"/>
</form:form>
<input type="hidden" value='${listPayees}' id="listPayees"/>
<input type="hidden" value='${accounts}' id="accounts"/>

<script>
	function getAIE() {
		var date = jQuery("#paymentDate").val();
		var id = jQuery("#hiddenAccount").val();
		if(!date || !id) {
			alert("Please enter Payment Date and Account");
			return;
		}
			
			jQuery.get( "getPaymentAIE.htm",
					{ 
					  accountId: id, paymentDate: date
			}, function( data ) {
				jQuery("#paymentAIE").html(data);
				jQuery("#totalAIE").val(data);
			});
	}

	function addPayee() {
		tb_show("Add Payee","payee.form?&keepThis=true&TB_iframe=true&height=250&width=400",null);
	}
	
	jQuery(document).ready(function(){
		var arrPayee=[];
		 var payees = jQuery("#listPayees").val();
		var obj = jQuery.parseJSON(payees);

		jQuery.each(obj, function (index, value) {
			arrPayee.push({id:value["id"], label:value["name"]});
		});
		
		jQuery( "#comboPayee" ).autocomplete({
					 minLength: 0,
			      source: arrPayee,
			      focus: function( event, ui ) {
					
			        jQuery( "#comboPayee" ).val( ui.item.label );
					return false;
			      },
			      select: function( event, ui ) {
			        jQuery( "#comboPayee" ).val( ui.item.label );
			        jQuery( "#hiddenPayee" ).val( ui.item.id );
					return false;
			      }
		   });
		var arrAccount=[];
		 var accounts = jQuery("#accounts").val();
		var obj = jQuery.parseJSON(accounts);

		jQuery.each(obj, function (index, value) {
			arrAccount.push({id:value["id"], label:value["name"]});
		});
		
		jQuery( "#comboAccount" ).autocomplete({
					 minLength: 0,
			      source: arrAccount,
			      focus: function( event, ui ) {
					
			        jQuery( "#comboAccount" ).val( ui.item.label );
					return false;
			      },
			      select: function( event, ui ) {
			        jQuery( "#comboAccount" ).val( ui.item.label );
			        jQuery( "#hiddenAccount" ).val( ui.item.id );
					return false;
			      }
		   });
		var accountName = jQuery("#hAccountName").val();
		var payeeName = jQuery("#hPayeeName").val();
		
		jQuery("#comboAccount").val(accountName);
		jQuery("#comboPayee").val(payeeName);
		
	});
	
	function printForm() {
		var printer = window.open('', '', 'width=300,height=300');
		printer.document.open("text/html");
		printer.document.write(document.getElementById('printDiv').innerHTML);
		printer.document.close();
		printer.window.close();
		printer.print();
	}
</script>


<!-- PRINT DIV -->

<div id="printDiv" class="hidden"	style="width: 1280px; font-size: 0.8em">
<style>
@media print {
	.donotprint {
		display: none;
	}
	.spacer {
		margin-top: 100px;
		font-family: "Dot Matrix Normal", Arial, Helvetica, sans-serif;
		font-style: normal;
		font-size: 14px;
	}
	.printfont {
		font-family: "Dot Matrix Normal", Arial, Helvetica, sans-serif;
		font-style: normal;
		font-size: 14px;
	}
}
</style>
<center><h1>PAYMENT FORM</h1></center>
<table class="printfont" style="margin-left: 60px; margin-top: 10px; font-family: 'Dot Matrix Normal', Arial, Helvetica, sans-serif; font-style: normal;">
<tr>
	<td>Account: </td>
	<td>${payment.account.name}</td>
</tr>
<tr>
	<td>Payment Date: </td>
	<td>${payment.paymentDate}</td>
</tr>
<tr>
	<td>AIE amount: </td>
	<td>${payment.totalAIE}</td>
</tr>
<tr>
	<td>Payee: </td>
	<td>${payment.payee.name}</td>
</tr>
<tr>
	<td>Reference Order Number	: </td>
	<td>${payment.referenceOrderNo}</td>
</tr>
<tr>
	<td>Commitment Number: </td>
	<td>${payment.commitmentNo}</td>
</tr>
<tr>
	<td>Cheque Number: </td>
	<td>${payment.checkNumber}</td>
</tr>
<tr>
	<td>Voucher Number: </td>
	<td>${payment.voucherNo}</td>
</tr>
<tr>
	<td>Payable Amount: </td>
	<td>${payment.payableAmount}</td>
</tr>
<tr>
	<td>Commitment Amount: </td>
	<td>${payment.commitmentAmount}</td>
</tr>
<tr>
	<td>Actual Payment: </td>
	<td>${payment.actualPayment}</td>
</tr>
<tr>
	<td>Status: </td>
	<td>${payment.status}</td>
</tr>
<tr>
	<td>Note: </td>
	<td>${payment.note}</td>
</tr>
</table>
</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>