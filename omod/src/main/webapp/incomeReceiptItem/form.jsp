<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require privilege="Add/Edit Income Receipt" otherwise="/login.htm"
	redirect="/module/accounting/main.htm" />
<link href="<openmrs:contextPath/>/scripts/jquery-ui/css/<spring:theme code='jqueryui.theme.name' />/jquery-ui.custom.css" type="text/css" rel="stylesheet" />
<link href="/openmrs/openmrs.css?v=1.9.7.60bd9b" type="text/css" rel="stylesheet" />
		<link href="/openmrs/openmrs_green.css" type="text/css" rel="stylesheet" />
		<link href="/openmrs/style.css?v=1.9.7.60bd9b" type="text/css" rel="stylesheet" />

<openmrs:htmlInclude file="/scripts/jquery/jquery.min.js" />
<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui.custom.min.js" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/moduleResources/accounting/scripts/jquery/css/thickbox.css" />

<style>
.ui-widget {font-size:0.9em}
</style>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/moduleResources/accounting/scripts/jquery/jquery.thickbox.js"></script>
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message
			code="${error.defaultMessage}" text="${error.defaultMessage}" /> </span>
</c:forEach>
<spring:bind path="incomeReceiptItem">
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
<form method="post" class="box" id="itemForm">
<input type="hidden" id="accounts"  value="${accounts}" />
<input type="hidden" id="incomeReceiptId" name="incomeReceiptId" value="${incomeReceiptId} }"/>

	<table id="tableItemForm">
		<tr>
			<td>Account</td>
			<td><form:input path="incomeReceiptItem.account" id="itemSelectAccount" /></td>
			<td><form:errors path="incomeReceiptItem.account"  cssClass="error" /></td>
		</tr>
		<tr>
			<td>Description</td>
			<td><form:input path="incomeReceiptItem.description" /></td>
			<td><form:errors path="incomeReceiptItem.description"  cssClass="error" /></td>
		</tr>
		<tr>
			<td>Type</td>
			<td><form:select path="incomeReceiptItem.type">
				<c:forEach items="${itemTypes}" var="type">
						<form:option value="${type.name }" label="${type.name }"/>
					</c:forEach>
			</form:select>
			<td><form:errors path="incomeReceiptItem.type"  cssClass="error" /></td>
		</tr>
		<tr>
			<td>Cheuqe number</td>
			<td><form:input path="incomeReceiptItem.chequeNumber"  /></td>
			<td><form:errors path="incomeReceiptItem.chequeNumber"  cssClass="error" /></td>
		</tr>
		<tr>
			<td>Amount</td>
			<td><form:input path="incomeReceiptItem.amount" /></td>
			<td><form:errors path="incomeReceiptItem.amount"  cssClass="error" /></td>
		</tr>
	</table>
	<span> <input type="button" value=" Save " onclick="saveItem()" />
		&nbsp; <input type="button" value=" Cancel " onclick="cancel()" /></span>
</form>


<script>


function saveItem() {
	jQuery("#itemForm").submit();
}

function cancel() {
	parent.tb_remove();
}

jQuery(document).ready(function(){
	  var availableTags = jQuery("#accounts").val().split(",");
	   
	jQuery( "#itemSelectAccount" ).autocomplete({
	     source: availableTags
	   });
})
</script>