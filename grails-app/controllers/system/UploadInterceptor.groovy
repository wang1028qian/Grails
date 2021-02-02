package system

import grails.converters.JSON
import system.util.RSAEncrypt
import system.util.ToStringUtils


class UploadInterceptor {

    boolean before() {

        if (params.obj != null){
            def parse = JSON.parse(params.obj)
            params.put("parse",parse)
            return true
        }
        return true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
