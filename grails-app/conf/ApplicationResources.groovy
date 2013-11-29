modules = {
    app {
        resource url:"css/app.css"
    }

    //applied to partner list view
    list {
        resource url:"css/list.css"
    }

    datepicker {
    	dependsOn "jquery"
    	resource url:"js/bootstrap-datepicker.js"
    	resource url:"js/application.js"
    	resource url:"js/tooltips.js"
    	resource url:"css/datepicker.css"
    }

    effect {
        dependsOn "datepicker"

    	resource url:"js/classie.js"
    	resource url:"js/sidebarEffects.js"
    	resource url:"css/component.css"
    }

    capitalize {
        dependsOn "jquery, app"
        resource url:"js/capitalize.js"
    }

    fees {
        dependsOn "jquery, app"
        resource url:"js/fees.js"
    }

    overrides {
        'jquery-theme' {
            resource id:'theme', url:'css/custom-theme/jquery-ui-1.10.3.custom.min.css'
        }
    }

}
