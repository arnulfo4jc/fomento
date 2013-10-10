package org.fomento

class DeductionController {

	def feeService

	static defaultAction = "deductions"
	static allowedMethods = []

    def deductionsFlow = {
        init {
            action {
                def partner = Partner.findByNumberOfEmployee(params.int("partner"))

                [partner:partner, total:feeService.calcTotal(partner)]
            }

            on("success").to "create"
            on(Exception).to "done"
        }

        create {
            on("confirm") {

            }.to "list"

            on("cancel").to "done"
        }

        done {
            redirect controller:"fee", action:"list", params:[id:flow.partner.id]
        }
    }

}
