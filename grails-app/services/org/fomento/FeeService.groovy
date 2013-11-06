package org.fomento

import static java.util.Calendar.*

class FeeService implements Serializable {

    //total por socio de sus aportes + capitalizacion inicial
    def calcTotal(fees, Partner partner) {
    	def total = 0

    	if (fees) {
    		fees.each { fee ->
    			total = total + fee.fee
    		}
    	}

    	return total + partner.affiliation.capitalization
    }

    //total fees by partner fee and by factory fee
    def totalFeesByPeriod(Partner partner, Integer period, String property) {
        def criteria = Fee.createCriteria()
        def result = criteria.get {
            eq "partner", partner
            eq "period", period

            projections {
                sum property
            }
        }

        result
    }

    //total def aporte de la empresa
    def calcFactoryTotalFeesByPartner(fees) {
    	def total = 0

    	if (fees) {
    		fees.each { fee ->
    			total = total + fee.factoryFee
    		}
    	}

    	return total
    }

    def partnerTotalCapitalization(partner, int period) {
        //all this must past formula validation
        //sum capitalization + sum all fees by current user - (sum deductions)

        //get partner fees in period
        def fees = Fee.byPeriod(period).findAllByPartner(partner)

        def partnerFees = calcTotal(fees, partner)
        def factoryFees = calcFactoryTotalFeesByPartner(partner.fees)

        def totalDeductions = 0

        if (partner?.deductions) {
            totalDeductions = Deduction.findByPartner(partner).last().totalAfterDeduction
        }

        def total = (partnerFees + factoryFees) - totalDeductions

        total
    }
}
