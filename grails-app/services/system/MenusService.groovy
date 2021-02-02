package system

import com.sun.corba.se.spi.ior.ObjectKey
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import groovy.sql.Sql
import org.grails.web.json.JSONObject
import org.springframework.web.servlet.tags.Param
import response.ChildMenusList
import response.ResultMenusList

import java.awt.print.PrinterGraphics

@Transactional
class MenusService {
    def resultService
    def dataSource

    def delMenus(obj){
        try {
            def db = new Sql(dataSource)
            if (obj.id != null){
                def menus = Menu.findAllById(obj.id)
                if (menus != null){
                    def sql = "delete from tb_Menu where id="+obj.id
                    db.execute(sql)
                   return resultService.successMsg("删除成功")
                }
            }
            return resultService.error()
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }
    }
    def updateMenus(obj) {
        try {
            if (obj.title != "" && obj.name != "" && obj.id != null){
                def menu = Menu.findById(obj.id)
                def titleOrName = Menu.findAllByTitleOrName(obj.title, obj.name)
                if (titleOrName.size() == 1 && menu.id == titleOrName.get(0).id){
                    menu.name = obj.name
                    menu.title = obj.title
                    menu.icon = obj.icon
                    menu.path = obj.path
                    menu.leve = obj.leve
                    menu.closable = true
                    menu.save()
                    return resultService.successMsg("修改菜单成功")
                }
                return resultService.failMsg("菜单名称或name已经存在")
            }
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }

    }


    def addMenus(obj) {
        try {
            if (obj.title != "" && obj.name != "" && obj.id != null) {
                def titleName = Menu.findAllByLeveOrName(obj.tilte, obj.name)
               // println(titleName)
                if (titleName.size() == 0) {
                    def menu = new Menu()
                    menu.name = obj.name
                    menu.title = obj.title
                    menu.icon = obj.icon
                    menu.path = obj.path
                    menu.leve = obj.leve
                    menu.closable = true
                    menu.save()
                    return resultService.successMsg("添加菜单成功")
                }
                return resultService.failMsg("菜单名称或name已经存在")
            }
            return resultService.error()
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }
    }

    def queryMenusList(obj) {
        def db = new Sql(dataSource)
        def sql_where = ""
        if (obj.title != "") {
            sql_where += " and m.title '%" + obj.title + "%'"
        }
        def menusByLeve = ""
        if (obj.page > 0 && obj.row > 0) {
            def pageSql = " limit " + ((obj.page - 1) * obj.rows) + "," + obj.row
            menusByLeve = "select * from tb_menu m where leve=0" + sql_where + pageSql
        } else {
            menusByLeve = "select * from tb_menu m where leve=0" + sql_where
        }
        List list = db.rows(menusByLeve)
        int[] ids = list.id
        def buffer = new StringBuffer()
        for (id in ids) {
            buffer.append(id + ",")
        }
        buffer.deleteCharAt(buffer.length() - 1)
        def idSting = buffer.toString()
        def childByLeve
        if (obj.page > 0 && obj.row > 0) {
            def pageSql = " limit " + ((obj.page - 1) * obj.rows) + "," + obj.row
            childByLeve = "select * from tb_menu m where leve in(" + idSting + ")" + sql_where + pageSql
        } else {
            childByLeve = "select * from tb_menu m where leve in(" + idSting + ")" + sql_where
        }
        def childList = db.rows(childByLeve)
        ResultMenusList menusList
        List resultList = new ArrayList<ResultMenusList>()
        for (a in list) {
            menusList = new ResultMenusList()
            menusList.title = a.title
            menusList.icon = a.icon
            menusList.hideInBread = a.hide_in_bread
            menusList.hideInMenu = a.hide_in_menu
            menusList.notCache = a.not_cache
            menusList.id = a.id
            menusList.name = a.name
            menusList.path = a.path
            menusList.leve = a.leve
            menusList.closable = a.closable
            List arrayList = new ArrayList<ChildMenusList>()
            ChildMenusList childMenus
            for (child in childList) {
                if (child.leve == a.id) {
                    childMenus = new ChildMenusList()
                    childMenus.title = child.title
                    childMenus.icon = child.icon
                    childMenus.hideInBread = child.hide_in_bread
                    childMenus.hideInMenu = child.hide_in_menu
                    childMenus.notCache = child.not_cache
                    childMenus.id = child.id
                    childMenus.setName(child.name)
                    childMenus.setPath(child.path)
                    childMenus.setLeve(child.leve)
                    childMenus.setClosable(child.closable)
                    arrayList.add(childMenus)
                }
            }
            ChildMenusList[] array = arrayList.toArray()
            menusList.setChildren(array)
            resultList.add(menusList)
        }
        def sql_count = "select count(m.id) as count from tb_menu m where 1=1 " + sql_where
        def count = db.rows(sql_count)
        def total = count[0].count

        resultService.successPage(resultList, total)
    }

    def queryMenus() {
        try {
            def db = new Sql(dataSource)
            def menusByLeve = "select * from tb_menu m where leve=0"
            List list = db.rows(menusByLeve)
            int[] ids = list.id
            def buffer = new StringBuffer()
            for (id in ids) {
                buffer.append(id + ",")
            }
            buffer.deleteCharAt(buffer.length() - 1)
            def idSting = buffer.toString()
            def childByLeve = "select * from tb_menu m where leve in(" + idSting + ")"
            def childList = db.rows(childByLeve)
            ResultMenus resultMenus
            Metas resultMeta
            List resultList = new ArrayList<ResultMenus>()
            for (a in list) {
                resultMeta = new Metas()
                resultMeta.title = a.title
                resultMeta.icon = a.icon
                resultMeta.hideInBread = a.hide_in_bread
                resultMeta.hideInMenu = a.hide_in_menu
                resultMeta.notCache = a.not_cache
                resultMenus = new ResultMenus()
                resultMenus.setMeta(resultMeta)
                resultMenus.id = a.id
                resultMenus.name = a.name
                resultMenus.path = a.path
                resultMenus.leve = a.leve
                resultMenus.closable = a.closable
                List arrayList = new ArrayList<ChildMenus>()

                for (child in childList) {
                    if (child.leve == a.id) {
                        ChildMenus childMenus = new ChildMenus()
                        Metas childMetas = new Metas()
                        childMetas.title = child.title
                        childMetas.icon = child.icon
                        childMetas.hideInBread = child.hide_in_bread
                        childMetas.hideInMenu = child.hide_in_menu
                        childMetas.notCache = child.not_cache
                        childMenus.setMeta(childMetas)
                        childMenus.id = child.id
                        childMenus.setName(child.name)
                        childMenus.setPath(child.path)
                        childMenus.setLeve(child.leve)
                        childMenus.setClosable(child.closable)
                        arrayList.add(childMenus)
                    }
                }
                ChildMenus[] array = arrayList.toArray()
                resultMenus.setChildren(array)
                resultList.add(resultMenus)
            }

            return resultService.successData(resultList)
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }
    }
    def queryMenuByRoleid(obj){
        try {
            def db = new Sql(dataSource)
            def menuids = "select menu_id from tb_rolemenu where role_id="+obj.roleId

            def menusByLeve = "select * from tb_menu m where leve=0 and m.id in("+menuids+")"
            List list = db.rows(menusByLeve)
            def childByLeve = "select * from tb_menu m where leve!=0 and m.id in("+menuids+")"
            def childList = db.rows(childByLeve)
            ResultMenus resultMenus
            Metas resultMeta
            List resultList = new ArrayList<ResultMenus>()
            for (a in list) {
                resultMeta = new Metas()
                resultMeta.title = a.title
                resultMeta.icon = a.icon
                resultMeta.hideInBread = a.hide_in_bread
                resultMeta.hideInMenu = a.hide_in_menu
                resultMeta.notCache = a.not_cache
                resultMenus = new ResultMenus()
                resultMenus.setMeta(resultMeta)
                resultMenus.id = a.id
                resultMenus.name = a.name
                resultMenus.path = a.path
                resultMenus.leve = a.leve
                resultMenus.closable = a.closable
                List arrayList = new ArrayList<ChildMenus>()

                for (child in childList) {
                    if (child.leve == a.id) {
                        ChildMenus childMenus = new ChildMenus()
                        Metas childMetas = new Metas()
                        childMetas.title = child.title
                        childMetas.icon = child.icon
                        childMetas.hideInBread = child.hide_in_bread
                        childMetas.hideInMenu = child.hide_in_menu
                        childMetas.notCache = child.not_cache
                        childMenus.setMeta(childMetas)
                        childMenus.id = child.id
                        childMenus.setName(child.name)
                        childMenus.setPath(child.path)
                        childMenus.setLeve(child.leve)
                        childMenus.setClosable(child.closable)
                        arrayList.add(childMenus)
                    }
                }
                ChildMenus[] array = arrayList.toArray()
                resultMenus.setChildren(array)
                resultList.add(resultMenus)
            }

            return resultService.successData(resultList)

        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }

    }
}
