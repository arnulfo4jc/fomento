<!doctype html>
<html lang="es">
<head>
	<meta name="layout" content="deduction/deductions">
	<r:require modules="bootstrap, deductions"/>
</head>
<body>
	<g:hasErrors bean="${deduction}">
		<g:renderErrors bean="${deduction}"/>
	</g:hasErrors>

	<h2>Aplicar deduccion a ${partner}</h2>

	<g:form>
		<div class="form-group">
			<g:hiddenField name="totalBeforeDeduction" value="${(deduction?.totalBeforeDeduction) ?: total}"/>
			<g:hiddenField name="totalAfterDeduction" value="${deduction?.totalAfterDeduction}"/>

			<label for="deduction">Deduccion</label>
			<div>
				<input type="range" name="percentage" id="percentage" min="0" max="100" step="10" value="${(deduction?.range) ?: 0}">
				<br>
				<span name="amount" id="amount">${(deduction?.range) ?: 0}</span>%
			</div>
		</div>
		<div class="form-group">
			<label for="reason">Razon de deduccion</label>
			<g:textArea name="reason" value="${deduction?.reason}" class="form-control"/>
		</div>
		<g:submitButton name="confirm" value="Confirmar deduccion" class="btn btn-default"/>
		<g:link action="deductions" event="cancel" class="btn btn-default">Regresar a cuotas de ${partner}</g:link>
	</g:form>
</body>
</html>