<!doctype html>
<html lang="es">
<head>
	<meta name="layout" content="main">
	<r:require modules="bootstrap, app"/>
</head>
<body>
	<g:render template="toolbar"/>

	<g:hasErrors bean="${partner}">
		<g:renderErrors bean="${partner}"/>
	</g:hasErrors>

	<g:form action="update">
		<g:hiddenField name="id" value="${params.id}"/>
		<g:render template="form"/>
		<g:submitButton name="update" value="Actualizar" class="btn btn-default"/>
	</g:form>
</body>
</html>