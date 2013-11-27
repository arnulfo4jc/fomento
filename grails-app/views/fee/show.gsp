<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<r:require modules="bootstrap, app"/>
</head>
<body>
	<g:form action="update">
		<g:hiddenField name="id" value="${params?.id}"/>
		<g:hiddenField name="partner" value="${params?.partner}"/>
		<g:hiddenField name="period" value="${params?.period}"/>

		<div class="control-group">
			<label for="fee">Cuota de socio</label>
			<g:textField name="fee" value="${fee?.fee}" class="form-control" autofocus="true"/>
		</div>
		<div class="control-group">
			<label for="factoryFee">Cuota de empresa</label>
			<g:textField name="factoryFee" value="${fee?.factoryFee}" class="form-control"/>
		</div>
		<g:submitButton name="send" value="Confirmar" class="btn btn-default"/>
		<g:link controller="partner" action="report" params="[id:params?.partner, period:params?.period]">Cancelar</g:link>
	</g:form>
</body>
</html>