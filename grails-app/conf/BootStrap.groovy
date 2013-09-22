 import org.fomento.*
import grails.util.GrailsUtil

class BootStrap {

    def init = { servletContext ->
        //roles
        def adminRole = Role.findByAuthority("ROLE_ADMIN") ?: new Role(authority:"ROLE_ADMIN").save()
        def userRole = Role.findByAuthority("ROLE_USER") ?: new Role(authority:"ROLE_USER").save()

    	switch(GrailsUtil.environment) {
    		case "development":
                //users
                def admin = User.findByUsername("me") ?: new User(username:"me", password:"123", enabled:true).save()

                if (!admin.authorities.contains(adminRole)) {
                    UserRole.create admin, adminRole, true
                }

                //partners
                def juanPerez = Partner.findByIdentificationCard("291-290180-0001W") ?: new Partner(
                    fullName:"Juan Perez",
                    numberOfEmployee:125,
                    identificationCard:"291-290180-0001W",
                    department:"Molino",
                    salary:15000
                ).save()

                def johnDoe = Partner.findByIdentificationCard("291-200280-0001W") ?: new Partner(
                    fullName:"John Doe",
                    numberOfEmployee:125,
                    identificationCard:"291-200280-0001W",
                    department:"Taller",
                    salary:17000
                ).save()

    		break
    		case "production":
    			def prodAdmin = User.findByUsername("me") ?: new User(username:"me", enabled:true, password:"123").save()

                if (!prodAdmin.authorities.contains(adminRole)) {
                    UserRole.create prodAdmin, adminRole, true
                }
    		break
    	}
    }
    def destroy = {
    }
}
