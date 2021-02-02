package system

import grails.converters.JSON

class IndexInterceptor {

    boolean before() {
        def obj = JSON.parse(params.obj)
        println(obj)

        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
