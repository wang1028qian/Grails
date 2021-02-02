package system

class Worker {
    String name
    String userCode //登录账户
    String pwd
    String sex
    String avatarImgPath
    Role role
    static constraints = {
    }
    static mapping = {
        version false
        table "tb_worker"
    }
}
