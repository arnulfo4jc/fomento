package org.fomento

import grails.plugins.springsecurity.Secured
import static java.util.Calendar.*

@Secured(['ROLE_ADMIN', 'ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
class FeeController {

	static defaultAction = "create"
	static allowedMethods = [
		create:["GET", "POST"],
		list:["GET", "POST"],
		show:"GET"
	]

	def create(String typeOfPayment) {
		if (request.method == "POST") {
			def p = params.list("partners")

			if (p) {
				for(id in p) {
					def partner = Partner.get(id)

					if (partner) {
						def range = partner.affiliation.range
						def lastFee = Fee.findAllByPartner(partner).last()

						if (range == lastFee.fee) {
							lastFee.properties["fee"] = partner.affiliation.fee
							lastFee.save()
							break
						}

						partner.addToFees(
							fee:(typeOfPayment == "Catorcena" && partner.affiliation.range) ? partner.affiliation.range : partner.affiliation.fee,
							factoryFee:partner.affiliation.factoryFee,
							period:new Date()[YEAR]
						)

						partner.save()
					}
				}

				flash.message = "${p.size()} cuotas aplicadas"
			} else {
				flash.message = "Debes seleccionar socios para poder continuar"
			}
		}

		[partners:Partner.byTypeOfPayment(typeOfPayment).byStatus(true).list(params)]
	}

	def list() {
		if (request.method == "POST") {
			def criteria = Fee.createCriteria()
			def partners = criteria.list {
				eq "period", params.int("period")
				projections {
					distinct "partner"
				}
			}

			return [partners:partners]
		}
	}

	def show(Long id) {
		def fee = Fee.get(id)

		if (!fee) {
			response.sendError 404
		}

		[fee:fee]
	}

	def update(Long id) {
		def fee = Fee.get(id)

		if (!fee) {
			response.sendError 404
		}

		fee.properties["fee", "factoryFee"] = params

		if (!fee.save()) {
			render view:"show", model:[fee:fee, id:id, partner:params?.partner, period:params?.period]
			return false
		}

		flash.message = "Aztualizacion confirmada"

		redirect action:"show", params:[id:id, partner:params?.partner, period:params?.period]
	}

}
