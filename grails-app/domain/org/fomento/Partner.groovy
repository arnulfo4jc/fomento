package org.fomento

class Partner {

    String fullName
    Integer numberOfEmployee
    String identificationCard
    String department
    BigDecimal salary
    boolean status

	Date dateCreated
	Date lastUpdated

    static constraints = {
        fullName blank:false
        numberOfEmployee blank:false, min:1
        identificationCard blank:false, unique:true, matches:"\\d{2}\\-\\d{7}"
        department blank:false
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

    static mapping = {
        version false
    }

    String toString() {
        fullName
    }

}
