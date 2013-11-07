<!doctype html>
<html lang="es">
<head>
	<meta name="layout" content="main">
	<r:require modules="bootstrap, app"/>
</head>
<body>
	<h1>Lorem ipsum dolor sit amet</h1>
	<p>
		Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quidem, repellendus sint perspiciatis soluta nostrum voluptates iusto veniam mollitia fuga nobis ducimus nisi praesentium laborum necessitatibus obcaecati. Amet deleniti aliquam fuga!
	</p>

	<g:form action="overwriteDividends">
		<g:hiddenField name="partnerTAS" value="${params?.partnerTAS}"/>
		<g:hiddenField name="factoryTAS" value="${params?.factoryTAS}"/>

		<g:hiddenField name="tap" value="${params?.tap}"/>
		<g:hiddenField name="fps" value="${params?.fps}"/>
		<g:hiddenField name="fpe" value="${params?.fpe}"/>

		<g:hiddenField name="up" value="${params?.up}"/>
		<g:hiddenField name="period" value="${params?.period}"/>

		<button type="submit" class="btn btn-warning">Confirmar</button>
		<g:link action="dividends" class="btn btn-default">Cancelar</g:link>
	</g:form>
</body>
</html>
