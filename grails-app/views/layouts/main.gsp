<!doctype html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title><g:layoutTitle default="Socios"/></title>
	<r:layoutResources/>
</head>
<body>
	<g:render template="/layouts/main-navbar"/>

	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<g:layoutBody/>
				<g:if test="${flash.message}">
					<div class="alert alert-info alert-fomento">${flash.message}</div>
				</g:if>
			</div>
		</div>
	</div>
	<r:layoutResources/>
</body>
</html>
