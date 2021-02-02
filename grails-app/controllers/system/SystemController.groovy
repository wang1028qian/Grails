package system

import grails.converters.JSON


class SystemController {
    def resultService
    def systemService
    def menusService
    def addMenus(){
        try {
            render menusService.addMenus(params.parse) as JSON
        }catch(Exception e){
            e.printStackTrace()
            render resultService.error() as JSON
        }
    }
    def login(){
        try {
            render systemService.login(params) as JSON
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error() as JSON
        }
    }
    def delMenus(){
        try {
            render menusService.delMenus(params.parse) as JSON
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error() as JSON
        }
    }
    def updateMenus(){
        try {
            render menusService.updateMenus(params.parse) as JSON
        }catch(Exception e){
            e.printStackTrace()
            render resultService.error() as JSON
        }
    }
    def queryMenu(){
       try {
           render systemService.queryMenu() as JSON
       } catch (Exception e) {
           e.printStackTrace()
           render resultService.error() as JSON
       }
    }
    def queryMenusList(){
        try {
            render menusService.queryMenusList(params.parse) as JSON
        } catch (Exception e) {
            e.printStackTrace()
            render resultService.error() as JSON
        }
    }
    def queryMenus(){
        try {
            render menusService.queryMenus() as JSON
        } catch (Exception e) {
            e.printStackTrace()
            render resultService.error() as JSON
        }
    }

    def queryMenuByRole(){
        try {
            render menusService.queryMenuByRoleid(params.parse) as JSON
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error() as JSON
        }
    }
    def queryWorker(){
        try {
            render systemService.queryWorker(params) as JSON
        }catch(Exception e){
            e.printStackTrace()
            render resultService.error() as JSON
        }
    }
    def queryWorkeruser(){
        try {
            render systemService.queryWorkeruser(params) as JSON
        } catch (Exception e) {
            e.printStackTrace()
            render resultService.error() as JSON
        }
    }
    def addWorker(){
        try{

            render systemService.addWorker(params) as JSON
        }catch(Exception e){
            e.printStackTrace()
            render resultService.error() as JSON
        }
    }
    def updateWorker(){
        try {
            render systemService.undateWorker(params) as JSON
        } catch (Exception e) {
            e.printStackTrace()
            render resultService.error() as JSON
        }
    }
    def addRole(){
        try {
            render systemService.addRole(params) as JSON
        } catch (Exception e) {
            e.printStackTrace()
            render resultService.error() as JSON
        }
    }
    def queryRole(){
        try {
            render systemService.queryRole(params) as JSON
        } catch (Exception e) {
            e.printStackTrace()
            render resultService.error() as JSON
        }
    }
    //修改
    def updateRole(){
        try {
            render systemService.updateRole(params) as JSON
        } catch (Exception e) {
            e.printStackTrace()
            render resultService.error() as JSON
        }
    }
    //删除角色
    def delRole(){
        try {
            render systemService.delRole(params) as JSON
        } catch (Exception e) {
            e.printStackTrace()
            render resultService.error() as JSON
        }
    }
    //删除
    def delWorker(){
        try {
            render systemService.delWorker(params) as JSON
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error() as JSON
        }
    }
    //查询角色菜单
    def queryRoleMenu(){
        try {
            render systemService.queryRoleMenu(params.parse) as JSON
        } catch (Exception e) {
            e.printStackTrace()
            render resultService.error() as JSON
        }
    }
    //角色菜单保存
    def saveRoleMenu(){
        try {
            render systemService.saveRoleMenu(params) as JSON
        } catch (Exception e) {
            e.printStackTrace()
            render resultService.error() as JSON
        }
    }
}
