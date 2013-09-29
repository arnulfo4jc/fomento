package org.fomento

class Partner {

    static searchable = true

    String fullName
    Integer numberOfEmployee
    String identificationCard
    String department
    BigDecimal salary
    boolean status
    Affiliation affiliation

	Date dateCreated
	Date lastUpdated

    static constraints = {
        fullName blank:false
        numberOfEmployee blank:false, unique:true, min:1
        identificationCard blank:false, unique:true
        department blank:false, maxSize:255
        salary blank:false, min:1000.0
    }

    static namedQueries = {
        byStatus { status ->
            eq "status", status
        }

        byDepartment { department ->
            eq "department", department
        }
    }

    List fees
    static hasMany = [fees:Fee]

    static mapping = {
        version false
        sort dateCreated: "desc"
    }

    String toString() {
        fullName
    }

}
