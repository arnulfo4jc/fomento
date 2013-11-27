<!doctype html>
<html lang="es">
<head>
	<r:require modules="bootstrap, app"/>
</head>
<body>
	<!--FEES-->
	<g:if test="${fees}">
		<h3>Cuotas del periodo ${params?.period}</h3>
		<table class="table table-hover">
			<thead>
				<tr>
					<th style="width:1px;"></th>
					<th>Cuota de socio</th>
					<th>Cuota de empresa</th>
					<th>Fecha de cuota</th>
				</tr>
			</thead>
			<tbody>
				<g:each in="${fees}" var="fee">
					<tr>
						<td><g:link controller="fee" action="show" params="[id:fee.id, partner:fee.partner.id, period:params?.period]"><span class="glyphicon glyphicon-pencil"></span></g:link></td>
						<td>${fee}</td>
						<td>${fee.factoryFee}</td>
						<td>${fee.dateCreated.format("yyyy-MM-dd")}</td>
					</tr>
				</g:each>
			</tbody>
		</table>
	</g:if>
</body>
</html>