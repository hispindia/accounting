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
<openmrs:require privilege="Add/Edit Budget" otherwise="/login.htm"
	redirect="/module/accounting/account.list" />

<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../includes/nav.jsp" %>

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/moduleResources/accounting/scripts/jquery/css/thickbox.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/accounting/scripts/jquery/jquery.ui.autocomplete.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/accounting/scripts/jquery/jquery.thickbox.js"></script>
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />

<h2>
	<spring:message code="accounting.budget.addedit" />
</h2>

<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message
			code="${error.defaultMessage}" text="${error.defaultMessage}" /> </span>
</c:forEach>
<spring:bind path="budgetCommand">
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
<form:form commandName="budgetCommand"  method="post" cssClass="box" id="budgetForm">
<input type="hidden" id="accounts" value='${accounts}'/>
<input type="hidden" id="budgetId" value="${budgetCommand.budget.id}"/>
	<table>
		<tr>
			<td><spring:message code="general.name" /></td>
			<td><spring:bind path="budget.name">
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
			<td><spring:bind path="budget.description">
					<input type="text" name="${status.expression}"
						value="${status.value}" size="35" />
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				</spring:bind></td>
		</tr>
		<tr>
			<td>Fiscal Period</td>
			<td>
				<select onChange="selectPeriod(this)">
					<option value="">--Select Period---</option>
					<c:forEach items="${periods}" var="period">

						<option value="<openmrs:formatDate date='${period.startDate}' type='textbox' />_<openmrs:formatDate date='${period.endDate}' type='textbox' />">${period.name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td valign="top"><spring:message code="accounting.startDate" />
			</td>
			<td> <spring:bind path="budget.startDate">
				<input type="hidden" name="${status.expression}" value="${status.value}" id="hiddenBudgetStartDate"/>
				<input type="text" readonly="readonly"  value="${status.value}" id="budgetStartDate">
			<!-- 	
				<input type="text" name="${status.expression}"
						value="${status.value}" size="35" onfocus="showCalendar(this)" id="budgetStartDate"/>
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
			 -->
				</spring:bind>
			</td>
				
				
		</tr>
		<tr>
			<td valign="top"><spring:message code="accounting.endDate" />
			</td>
			<td><spring:bind path="budget.endDate">
			<input type="hidden" name="${status.expression}" value="${status.value}" id = "hiddenBudgetEndDate"/>
				<input type="text" readonly="readonly"  value="${status.value}" id="budgetEndDate">
				
				<!-- <input type="text" name="${status.expression}"
						value="${status.value}" size="35" onfocus="showCalendar(this)" id = "budgetEndDate"/>
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				 -->
				</spring:bind></td>
		</tr>
		<tr>
			<td><spring:message code="general.retired" /></td>
			<td><form:radiobutton path="budget.retired" value="false" />NO <form:radiobutton
					path="budget.retired" value="true" />YES</td>
				 <form:errors path="budget.retired" cssClass="error" /></td>
		</tr>
	</table>
	<br /> <input type="button"
		value="<spring:message code="general.save"/>" onclick="submitForm()"> <input
		type="button" value="<spring:message code="general.cancel"/>"
		onclick="javascript:window.location.href='budget.list'">
		<br/><br/>
		<div  <c:if test="${empty budgetCommand.budget.id }"> style="display: none"</c:if>>
		<span class="boxHeader"><strong>Budget Items</strong></span>
		<input type="button" onclick="addItem();" value="Add Budget Item" style="margin:5px"/>
		<table id="itemTable" class="box">
			<thead>
				<th>Account </th>
				<th>Description</th>
				<th>Start Date</th>
				<th>End Date</th>
				<th align="right" style="text-align:right">Amount</th>
				<th></th>
			</thead>
			<tbody>
				<tr><td  colspan="5"></td></tr>
			
				<c:forEach items="${budgetCommand.budget.budgetItems}" var="item">
				<tr <c:if test="${item.retired}"> style="text-decoration:line-through;"</c:if>>
					<td ><a href="#" id="item_${item.id}_accountName" onclick="editItem(${item.id })">${item.account.name }</a>
						<input type="hidden" id="item_${item.id}_accountId" value="${item.account.id}"/>
					</td>
					
					<td id="item_${item.id}_description">${item.description}</td>
					<td id="item_${item.id}_startDate"><openmrs:formatDate date="${item.startDate}" type="textbox" /></td>
					<td id="item_${item.id}_endDate"><openmrs:formatDate date="${item.endDate}" type="textbox" /></td>
					<td id="item_${item.id}_amount" style="text-align:right" align="right" >${item.amount}</td>
					<td align="center"> <c:if test="${!item.retired}"> <input type="button" value="Delete" onclick="deleteItem(this,${item.id})" /></c:if></td>
				</tr>
				</c:forEach>
			
			</tbody>
		</table>
		</div>
</form:form>

<div id="itemDiv" style="display:none">
<span><strong>Add Budget Item</strong></span>
<input type="hidden" id="itemId" value=""/>

	<table>
		<tr>
			<td>Account</td>
			<td><input type="text" id="itemAccountName" size="35"/><input type="hidden" id="itemAccountId" value=""/></td>
		</tr>
		<tr>
			<td>Description</td>
			<td><input type="text" id="itemDescription" size="35"/></td>
		</tr>
		<tr>
			<td>Start Date</td>
			<td><input type="text" id="itemStartDate" size="35" readonly="readonly"/></td>
		</tr>
		<tr>
			<td>End Date</td>
			<td><input type="text" id="itemEndDate" size="35" readonly="readonly"/></td>
		</tr>
		<tr>
			<td>Amount</td>
			<td><input type="text" id="itemAmount" size="35" /></td>
		</tr>
	</table>
	<input type="button" value="Save" onclick="saveItem()"/> <input type="button" value="Cancel" onclick="cancelItem()"/>
</div>

<script>

function selectPeriod(this_) {
	var selected = jQuery(this_).val();
	var arr = selected.split("_");
	var startDate = arr[0];
	var endDate = arr[1];

	jQuery("#budgetStartDate").val(startDate);
	jQuery("#budgetEndDate").val(endDate);
	jQuery("#hiddenBudgetStartDate").val(startDate);
	jQuery("#hiddenBudgetEndDate").val(endDate);

	
}

jQuery(document).ready(function(){
	var arrAccount=[];
	 var accounts = jQuery("#accounts").val();
	var obj = jQuery.parseJSON(accounts);

	jQuery.each(obj, function (index, value) {
	    arrAccount.push({id:value["id"], label:value["name"]});
	});
	

	
	jQuery( "#itemAccountName" ).autocomplete({
				 minLength: 0,
		      source: arrAccount,
		      focus: function( event, ui ) {
				
		        jQuery( "#itemAccountName" ).val( ui.item.label );
				return false;
		      },
		      select: function( event, ui ) {
		        jQuery( "#itemAccountName" ).val( ui.item.label );
		        jQuery( "#itemAccountId" ).val( ui.item.id );
				return false;
		      }
	   });
	
});

function submitForm() {
	jQuery("#budgetForm").submit();
}
/**
* Open dialog for editing Budget Item
**/
function editItem(id) {
	setItemFormValues(id,
			jQuery("#item_"+id+"_accountName").html(),
			jQuery("#item_"+id+"_description").html(), 
			jQuery("#item_"+id+"_startDate").html(), 
			jQuery("#item_"+id+"_endDate").html(),
			jQuery("#item_"+id+"_amount").html()
	);
	var keyEvent = jQuery.Event("keydown");          
	//$("#itemAccountName").val("Item");        // Enters the value "Item" in text field

	setTimeout(function () { 
	    keyEvent.keyCode = jQuery.ui.keyCode.DOWN;  // event for pressing "down" key
	    jQuery("#itemAccountName").trigger(keyEvent);  // Press "down" key to select first item after 50 milliseconds
	    jQuery("#itemAccountName").trigger(keyEvent);
	     setTimeout(function () {
	          keyEvent.keyCode = jQuery.ui.keyCode.ENTER; // event for pressing the "enter" key
	          jQuery("#itemAccountName").trigger(keyEvent);  // Press "Enter" to select marked item after another 50 milliseconds
	     }, 50);
	}, 50);  
	
	tb_show("Edit Budget Item","#TB_inline?height=150&width=300&inlineId=itemDiv&modal=true",null);
	
}

/**
* Is call when user wants to edit a Budget Item
* It will populate the Budget Item data to the dialog form
**/
function setItemFormValues(id, account, desc, startDate, endDate, amount) {
	/*jQuery( "#itemAccount" ).autocomplete({
	     source: availableTags
	   });*/
	jQuery("#itemId").val(id);
	jQuery("#itemAccountName").val(account);
	jQuery("#itemDescription").val(desc);
	jQuery("#itemStartDate").val(startDate);
	jQuery("#itemEndDate").val(endDate);
	jQuery("#itemAmount").val(amount);
}

/**
* Is called after successfully updated Budget Item to database using ajax
* It will update new value to the table row on the main page
**/
function updateItemRowValues(id, accountId, accountName, desc, startDate, endDate, amount) {
	jQuery("#item_"+id+"_accountId").val(accountId);
	
	jQuery("#item_"+id+"_accountName").html(accountName);
	jQuery("#item_"+id+"_description").html(desc); 
	jQuery("#item_"+id+"_startDate").html(startDate); 
	jQuery("#item_"+id+"_endDate").html(endDate);
	jQuery("#item_"+id+"_amount").html(amount);
}
/**
* Reset the hidden form for Budget Item
**/
function resetItemForm() {
	/* jQuery( "#itemAccount" ).autocomplete({
	     source: availableTags
	   }); */
	jQuery("#itemId").val(""); // ID = NULL => Add new 
	jQuery("#itemAccountName").val("");
	jQuery("#itemAccountId").val("");
	jQuery("#itemDescription").val("");
	jQuery("#itemStartDate").val("");
	jQuery("#itemEndDate").val("");
	jQuery("#itemAmount").val("");
}

/**
* Save Item, can be Edit or Add New
* Called by Save button on dialog
**/
function saveItem() {

	var item = new Object();
	item.id = jQuery("#itemId").val();
	item.accountId = jQuery("#itemAccountId").val();
	item.budgetId = jQuery("#budgetId").val();
	item.accountName = jQuery("#itemAccountName").val();
	item.description = jQuery("#itemDescription").val();
	item.startDate = jQuery("#itemStartDate").val();
	item.endDate = jQuery("#itemEndDate").val();
	item.amount = jQuery("#itemAmount").val();
	
	
	if (item.budgetId) {
	/**
	*	Edit Budget: add/edit each budget item by ajax
	**/
	
		jQuery.post( "budgetItem.form",
				{ action : "update",
				  id : item.id,
				  budget : item.budgetId,
				  account: item.accountId,
				  description : item.description,
				  startDate : item.startDate,
				  endDate : item.endDate,
				  amount : item.amount
		}, function( data ) {

			if (!isNaN(data)) {
				if (item.id) {
					updateItemRowValues(item.id,
								item.accountId,
								item.accountName,
								item.description, 
								item.startDate, 
								item.endDate,
								item.amount
						);
					tb_remove();
					} else {
					addItemRow(data, item.accountName, item.accountId, item.description, 
															item.startDate, 
															item.endDate,
															item.amount);

					// Close item popup
					tb_remove();
					}
			} else {
				alert("Can not update Budget Item: "+data);
			} 
		});
		
	} else {
		/**
		*	Add new Budget: add budget item together with Budget
		**/
		addItemRow(item.id, item.accountName, item.accountId, item.description, 
										item.startDate, 
										item.endDate,
										item.amount);

		// Close item popup
		tb_remove();
		
	}
	
	
	
}

function addItemRow(id, accountName, accountId, description, startDate, endDate, amount) {
		
		amount = parseFloat(amount).toFixed(2);
		var table = document.getElementById("itemTable");
		var rowCount = table.rows.length;
		rowCount = rowCount - 2 // count start from 0, also need to minus the Add Item link
		
		var budgetId = jQuery("#budgetId").val();
		
		if (budgetId) {
			// EDIT BUDGET
			
				var row = "<tr>"
					+"<td><a href='#' id='item_"+id+"_accountName' onclick='editItem("+id+")'>"+accountName+"</a> <input type='hidden' name='budgetItems["+rowCount+"].account.id' value='"+accountId+"'/></td>"
					+"<td id='item_"+id+"_description'>"+description+"</td>"
					+"<td id='item_"+id+"_startDate'>"+startDate+"</td>"
					+"<td id='item_"+id+"_endDate'>"+endDate+"</td>"
					+"<td id='item_"+id+"_amount'>"+amount+"</td>"
					+"<td><input type='button' value='Delete' onclick='deleteItem(this,"+id+")'/></td>"
					+"</tr>";
			
				jQuery("#itemTable tbody").append(row);
		} else {
			// ADD NEW BUDGET
				var row = "<tr>"
					+"<td><a href='#' id='item_"+id+"_accountName' onclick='editItem("+id+")'>"+accountName+"</a> <input type='hidden' name='budgetItems["+rowCount+"].account.id' value='"+accountId+"'/></td>"
					+"<td><input type='hidden' name='budgetItems["+rowCount+"].description' value='"+description+"'/>"+description+"</td>"
					+"<td><input type='hidden' name='budgetItems["+rowCount+"].startDate' value='"+startDate+"' />"+startDate+"</td>"
					+"<td><input type='hidden' name='budgetItems["+rowCount+"].endDate' value='"+endDate+" '/>"+endDate+"</td>"
					+"<td align='right'><input type='hidden' name='budgetItems["+rowCount+"].amount' value='"+amount+"'/>"+amount+"</td>"
					+"<td align='center'><input type='button' value='Delete' onclick='deleteItem(this,"+id+")'/></td>"
					+"</tr>";
				jQuery("#itemTable tbody").append(row);
		}
		
	
		
	
}

/**
* Show dialog for add new Budget Item
**/
function addItem() {
	resetItemForm();
	jQuery("#itemStartDate").val(jQuery("#budgetStartDate").val());
	jQuery("#itemEndDate").val(jQuery("#budgetEndDate").val());
	tb_show("Add Budget Item","#TB_inline?height=150&width=300&inlineId=itemDiv&modal=true",null);
}
/**
* Close dialog
**/
function cancelItem() {
	tb_remove();
}

function deleteItem(_this,id) {
	if (confirm("Are you sure to delete this Budget Item ?")) {
		
		jQuery.post( "budgetItem.form",{budgetItemId : id, action : "delete"}, function( data ) {
			if (data == "success") {
				var tr = jQuery(_this).parents("tr");
				jQuery(tr).css("text-decoration","line-through");
				jQuery(_this).remove();
			} else {
				alert("Can not delete Budget Item." + data);
			}
		});
		
	}
	
}

</script>
<%@ include file="/WEB-INF/template/footer.jsp"%>