package org.fomento

class Affiliation implements Serializable {

    BigDecimal fee
    BigDecimal range
    String typeOfPayment
    BigDecimal factoryFee
    Date enrollmentDate
    BigDecimal capitalization = 0.00
    BigDecimal factoryCapital

	Date dateCreated
	Date lastUpdated

    static constraints = {
        fee blank:false, min:1.00
        range nullable:true, min:1.00, validator: { val, obj -> val < obj.fee }
        typeOfPayment blank:false, inList: ["Catorcena", "Fin de mes", "Bono"], maxSize:255
        factoryFee blank:false, min:200.00
        enrollmentDate blank:false, validator: { enrollmentDate ->
            def today = new Date()

            enrollmentDate <= today
        }
        capitalization blank:false, min:0.00
        factoryCapital blank:false, min:0.00
    }

    static belongsTo = [partner:Partner]

    static mapping = {
        version false
    }

    String toString() { fee }

}
