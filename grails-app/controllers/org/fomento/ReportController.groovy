package org.fomento

import grails.plugins.springsecurity.Secured

@Secured(["ROLE_ADMIN","ROLE_USER"])
class ReportController {

	def feeService
	def dividendService

	static defaultAction = "dividends"
	static allowedMethods = [
		dividends:["GET", "POST"],
		applyDividends:"POST",
		overwriteDividends:["GET", "POST"],
		list:"GET",
		show:"GET",
        delete:"GET"
	]

    def dividends(DividendsCommand cmd) {
    	if (request.method == "POST") {
    		if (cmd.hasErrors()) {
    			return [cmd:cmd]
    		}

	    	def partners = Partner.findAllByStatus(true)

            def result = dividendService.calcTAS(partners, cmd.period)

	    	return [
                partners:partners,
                partnerTAS:result.partnerTAS,
                factoryTAS:result.factoryTAS,
                up:cmd.up,
                period:cmd.period
            ]
    	}
    }

    //list
    def list() {
    	def dividends = Dividend.byPeriod().list()

    	[dividends:dividends]
    }

    //show
    def show(Integer period) {
    	def dividends = Dividend.findAllByPeriod(period)

    	if (!dividends) {
    		redirect action:"list"
    	}

    	[
            dividends:dividends,
            up:dividends.first().periodUP,
            partnerTAS:dividends.first().partnerTAS,
            factoryTAS:dividends.first().factoryTAS
        ]
    }

    //create
    def applyDividends(ApplyDividendsCommand cmd) {
    	//get all partners with status set to true
    	if (cmd.hasErrors()) {
    		return [cmd:cmd]
    	}

    	def partners = Partner.findAllByStatus(true)
    	def dividendsCount =  Dividend.countByPeriod(cmd.period)

		if (!dividendsCount) {
			partners.each { partner ->
				def partnerDP = dividendService.getPeriodUtility(partner, cmd.partnerTAS, cmd.up, cmd.period)
                def factoryDP = dividendService.getPeriodUtility(partner, cmd.factoryTAS, cmd.up, cmd.period)

				//add dividend to each partner in period
                def dividend = new Dividend (
                    partnerDividend:partnerDP.partnerDP,
                    factoryDividend:factoryDP.factoryDP,
                    partnerTAS:cmd.partnerTAS,
                    factoryTAS:cmd.factoryTAS,
                    periodUP:cmd.up,
                    period:cmd.period
                )

				partner.addToDividends(dividend)
			}
		} else {
			//ask user if want to procede
			redirect action:"overwriteDividends", params:[partnerTAS:cmd.partnerTAS, factoryTAS:cmd.factoryTAS, up:cmd.up, period:cmd.period]
			return false
		}

        redirect action:"list"
    }

    //update
    @Secured("ROLE_ADMIN")
    def overwriteDividends() {
    	if (request.method == "POST") {
    		def partners = Partner.findAllByStatus(true)

    		partners.each { partner ->
    			def dividend = Dividend.findByPartnerAndPeriod(partner, params.period)
                def partnerDP = dividendService.getPeriodUtility(partner, params.double("partnerTAS"), params.double("up"), params.int("period"))
                def factoryDP = dividendService.getPeriodUtility(partner, params.double("factoryTAS"), params.double("up"), params.int("period"))

    			if (dividend) {
    				dividend.partnerDividend = partnerDP.partnerDP
                    dividend.factoryDividend = factoryDP.factoryDP
                    dividend.partnerTAS = params.double("partnerTAS")
                    dividend.factoryTAS = params.double("factoryTAS")
                    dividend.periodUP = params.double("up")

    				if (!dividend.save()) {
    					dividend.errors.allErrors.each {
    						print it
    					}
    				}
    			}
    		}

    		redirect action:"list"
    		return false
    	}
    }

    //delete
    @Secured("ROLE_ADMIN")
    def delete(Integer period) {
        def query = Dividend.where {
            period == period
        }

        int total = query.deleteAll()

        flash.message = "$total dividendos eliminados"

        redirect action:"list"
    }

}

class DividendsCommand {
	BigDecimal up
	Integer period

	static constraints = {
		up blank:false, min:1.0
		period min:2013
	}

}

class ApplyDividendsCommand {
	BigDecimal partnerTAS
    BigDecimal factoryTAS
	BigDecimal up
	Integer period

	static constraints = {
		partnerTAS blank:false, min:1.0
        factoryTAS blank:false, min:1.0
		up blank:false, min:1.0
		period blank:false, min:2013
	}
}
