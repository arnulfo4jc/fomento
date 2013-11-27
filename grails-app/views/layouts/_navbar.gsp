<ul class="nav nav-tabs">
  	<li class="${(actionName == 'show') ? 'active' : ''}">
  		<g:link controller="partner" action="show" id="${params?.id}">${partner}</g:link>
  	</li>
	<li class="${(actionName == 'report') ? 'active' : ''}">
		<g:link controller="partner" action="report" id="${params?.id}" params="[period:fomento.currentYear()]">
			Reporte
		</g:link>
	</li>
	<li class="${(actionName == 'changeStatus') ? 'active' : ''}">
		<g:link controller="partner" action="changeStatus" id="${params?.id}">Estado</g:link>
	</li>
	<g:if test="${partner?.affiliation?.typeOfPayment == 'Catorcena'}">
		<li class="${(actionName == 'splitFee') ? 'active' : ''}">
			<g:link controller="partner" action="splitFee" id="${params?.id}">Dividir cuota</g:link>
		</li>
	</g:if>
</ul>
<br>