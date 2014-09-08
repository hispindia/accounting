<select name="nextPeriodId">
	<option value="">--Select Next Period---</option>
	<c:forEach items="${listPeriods}" var="p">
		<option value="${p.id}">${p.name }</option>
	</c:forEach>
</select>