package system.util;

import groovy.util.ConfigSlurper;
import org.apache.commons.lang.StringUtils;
import org.codehaus.groovy.util.StringUtil;
import sun.security.krb5.internal.crypto.RsaMd5CksumType;

import java.io.File;
import java.io.PipedOutputStream;
import java.util.Map;

public class test {
    public void pring(){
        try {

            /*
            //String path = this.getClass().getResource("/").getPath();
            String string = "QRYAm7ppjy1JLTvzc62TgBdNPqavVeFlB4FkK29uuxagqCOKQj9QE76sZFcr%2B9l7gsuUACtkLhJJLzNKTJM1nxgcufgF3aCdDmxkWq%2BNpGgK2AVxCEOWvH6KOumlvDb2Y1%2B2b/Zl7dwYtvNShh%2BJnYlzK9XT9d0pLejIbe/bY6g=";
            String password = string.replaceAll("%2B","+");
            String path = System.getProperty("user.dir")+"/grails-app/conf/LoginRsaKey/privateKey.txt";
            String privateKey = ToStringUtils.readTxt(path);

            byte[] decryptByPrivateKey = RSAUtils_user.decryptByPrivateKey(Base64Utils.decode(password), privateKey);
            System.out.println(decryptByPrivateKey);
            */
            /*
            String pwdparma = "GL4c3DbVJg7WiVmCnynGon6DuCR27vNBKK0uYz8g6k6F9qMZWxdM9FBPOV9hMssU78I7bX2/ANmyAqdLREhb/30uGKSItAH4gpt9Uy1bpPmCdOZ9O1zCacdYb5H7f6QIC8w+ytl4Sftdx8RLWjAYZAuX1mzBHrTcubAwxngiKpU=";
            byte[] parma = pwdparma.getBytes();
            String password  =  "12345678";
            byte[] bytes1 = password.getBytes();
            String s = new String(bytes1);
            Map<String, Object> keyPair = RSAUtils_user.genKeyPair();
            //String publicKey = RSAUtils_user.getPublicKey(keyPair);
           // String privateKey = RSAUtils_user.getPrivateKey(keyPair);
            String path = System.getProperty("user.dir")+"/grails-app/conf/LoginRsaKey/privateKey.txt";
            String publicKeypath = System.getProperty("user.dir")+"/grails-app/conf/LoginRsaKey/publicKey.txt";
            String privateKey = ToStringUtils.readTxt(path);
            String publicKey = ToStringUtils.readTxt(publicKeypath);
            byte[] RSAPwd = RSAUtils_user.encryptByPublicKey(bytes1, publicKey);
            byte[] pwd = RSAUtils_user.decryptByPrivateKey(parma, privateKey);
            String pwd16 = ToStringUtils.toHexString1(pwd);
            String pwdString = ToStringUtils.hexStringToString(pwd16);
            System.out.println("密码："+pwdString);
               */
            String pwdparma = "DAbgJmSz06VSUh0BTZFViFE9bsRjfIv7kyC5RbdaoM6+2126I3zGaBFdG0KRsDE5e4X/b6zgA/Quxh8x6uCck3yMQfT+bisvCjinZvTzxfF7LM/XgS+U8thGoaPeIMR8+S4PbvxJSJcYN5yCd43HCVF7afZyLBPhj7uqG1MzzYg=";
            String path = System.getProperty("user.dir")+"/grails-app/conf/LoginRsaKey/privateKey.txt";
            String privateKey = ToStringUtils.readTxt(path);
            String pwd = RSAEncrypt.decrypt(pwdparma, privateKey);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       // test test = new test();
       // test.pring();
    }
}
