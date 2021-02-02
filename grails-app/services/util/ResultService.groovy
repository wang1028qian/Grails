package util

import grails.gorm.transactions.Transactional

@Transactional
class ResultService {

    def successMsg(msg){
        Map map=new HashMap()
        map.put("code","SUCCESS")
        map.put("msg",msg)
        return  map
    }
    def successLogin(token){
        def map = new HashMap()
        map.put("code","SUCCESS")
        map.put("token",token)
        return map
    }
    def successData(data){
        Map map=new HashMap()
        map.put("code","SUCCESS")
        map.put("data",data)
        return  map
    }
    //分页
    def successPage(data,total){
        Map map=new HashMap()
        map.put("code","SUCCESS")
        map.put("data",data)
        map.put("total",total)
        return  map
    }
    def failMsg(msg){
        Map map=new HashMap()
        map.put("code","FAIL")
        map.put("msg",msg)
        return  map
    }
    def error(){
        Map map=new HashMap()
        map.put("code","ERROR")
        map.put("msg","f服务器繁忙")
        return  map
    }
}
