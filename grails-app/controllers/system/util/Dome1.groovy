package system.util

class Dome1 {
    static void main(String[] args) {
        String config = new ConfigSlurper().parse( new File(
                "${System.properties['user.dir']}/grails-app/conf/LoginRsaKey/Test.groovy" ).toURL())

       // println(config)
        String resource = this.getClass().getResource("/LoginRsaKey/privateKey.txt")
        String path = this.getClass().getResource("/LoginRsaKey/privateKey.txt").getFile();
        //String privateKey = ToStringUtils.respondsTo(config)
       // println(privateKey)
    }
}
