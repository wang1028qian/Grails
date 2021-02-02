package system

import grails.converters.JSON
import system.util.RSAEncrypt
import system.util.ToStringUtils

import java.awt.print.PrinterGraphics


class SystemInterceptor {

    boolean before() {

        try {
            if (params.obj != null){
                def parse = JSON.parse(params.obj)
                if (actionName == 'login'){
                    String pwdSting = parse.pwd
                    String userString = parse.user_code
                    String path = System.getProperty("user.dir")+"/grails-app/conf/LoginRsaKey/privateKey.txt";
                    String privateKey = ToStringUtils.readTxt(path);
                    String pwd = RSAEncrypt.decrypt(pwdSting, privateKey);
                    String user_code = RSAEncrypt.decrypt(userString,privateKey)
                    params.put("pwd",pwd)
                    params.put("user_code",user_code)
                    return true
                }
                params.put("parse",parse)
                return true
            }
            return true
        } catch (Exception e) {
            e.printStackTrace()
            return false
        }
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
