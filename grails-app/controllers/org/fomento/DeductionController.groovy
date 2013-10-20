package org.fomento

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN', 'ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
class DeductionController {

	def feeService

	static defaultAction = "deductions"
	static allowedMethods = [
        deductions:["GET", "POST"]
    ]

    def deductionsFlow = {
        init {
            action {
                def partner = Partner.findById(params.int("partner"))

                [partner:partner, period:params?.period, total:feeService.partnerTotalCapitalization(partner, params.int("period")), id:partner.id]
            }

            on("success").to "list"
        }

        create {
            on("confirm") {
                def deduction = new Deduction(
                    totalBeforeDeduction:params?.totalBeforeDeduction,
                    totalAfterDeduction:params?.totalAfterDeduction,
                    period:flow.period,
                    percentage:params.int("percentage") / 100,
                    reason:params?.reason,
                    partner:flow.partner
                )

                if (!deduction.save()) {
                    deduction.errors.allErrors.each {
                        print it
                    }
                }

                flow.partner.refresh()
                flow.total = feeService.partnerTotalCapitalization(flow.partner, flow.period.toInteger())
            }.to "list"

            on("list").to "list"
            on("create").to "create"
        }

        list {
            on("list").to "list"
            on("create").to "create"

            on("delete") {
                def deduction = Deduction.get(params.id)
                flow.partner.removeFromDeductions(deduction)

                flow.partner.refresh()
            }.to "list"

            on("show"){
                def ded = Deduction.get(params.int("id"))

                [ded:ded]
            }.to "show"
        }

        show {
            on("list").to "list"
            on("create").to "create"
        }
    }
}
