package system

import grails.converters.JSON
import grails.gorm.transactions.Transactional
import grails.web.api.ServletAttributes
import groovy.sql.Sql
import org.apache.commons.codec.language.bm.Lang
import org.apache.commons.lang.ArrayUtils
import org.grails.web.json.JSONObject
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

import javax.servlet.http.HttpServletResponse
import javax.sql.rowset.FilteredRowSet
import java.text.SimpleDateFormat

@Transactional
class SystemService {
    def resultService
    def dataSource

    def quueryWorkeMenuByRoleId(obj) {
        try {
            if (obj.roleId != "") {
                def db = new Sql(dataSource)
                def sql_where = "select menu_id from tb_rolemenu where role_id=" + obj.roleId
                def sql = "select *, id as menuId from tb_menu where level=0 and id in(" + sql_where + ")"
                //println(sql)
                def oneMenu = db.rows(sql)
                for (a in oneMenu) {
                    def sql_chaildren = "select *,id as menuId from tb_menu where level=" + a.id + " and id in (" + sql_where + ")"
                    def chikdren = db.rows(sql_chaildren)
                    a.put("children",chikdren)
                }
                return resultService.successData(oneMenu)
            }
            return resultService.error()
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }
    }

    def queryMenu() {
        try {
            def db = new Sql(dataSource)
            def sql = "select *,id as menuId from tb_menu where level=0";
            def oneMenu = db.rows(sql)
            //查询二级才
            for (a in oneMenu) {
                def sql_chaildren = "select *,id as menuId from tb_menu where level=" + a.id
                def children = db.rows(sql_chaildren)
                a.put("children", children)
            }
            return resultService.successData(oneMenu)
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }
    }
    //角色操作
    //添加
    def addRole(obj) {
        try {
            Role role = Role.findByName(obj.name)
            if (role != null) {
                return resultService.failMsg("角色名称已存在")
            } else {

                role = new Role()
                role.name = obj.name
                def newDate = new Date();
                def ft = new SimpleDateFormat("yyy-MM-dd")
                role.ctDay = "" + ft.format(newDate);
                role.save()
                return resultService.successMsg("添加成功")
            }
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }
    }
    //查询
    def queryRole(obj) {
        try {
            def db = new Sql(dataSource)
            //println(obj)
            def sql_where = ""
            if (obj.name != "") {
                sql_where += " and name like '%" + obj.name + "%'"
            }
            if (obj.ct_day != "") {
                sql_where += " and ct_day like '%" + obj.ct_day + "%'"
            }
            def sql = ""
            def sqlpage = Long.parseLong(obj.page)
            def sqlrows = Long.parseLong(obj.rows)
            if (sqlpage >= 1 || sqlrows >= 1) {
                def pageSet = " limit " + (Long.parseLong(obj.page) - 1) * Long.parseLong(obj.rows) + "," + Long.parseLong(obj.rows)
                sql = "select * from role where 1=1 " + sql_where + " order by id desc " + pageSet
            } else {
                sql = "select * from role where 1=1 " + sql_where + " order by id desc "
            }
            def sql_count = "select count(*) as count from role where 1=1 " + sql_where
            //println(sql)
            def count = db.rows(sql_count)
            def total = count[0].count
            def result = db.rows(sql)
            return resultService.successPage(result, total)
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }
    }
    //查询员工详细信息
    def queryWorkeruser(Object obj) {
        try {
            def id = Long.parseLong(obj.token)
            if (id != null) {
                def worker = Worker.findById(id)
                return resultService.successData(worker)
            }
            return resultService.failMsg("系统繁忙")
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }
    }
    //查询员工
    def queryWorker(Object obj) {
        try {
           // println("obj:" + obj)
            def db = new Sql(dataSource)
            def sql_where = ""
            if (obj.name != "") {
                sql_where += " and w.name like '%" + obj.name + "%'"
            }
            if (obj.user_code != "") {
                sql_where += " and w.user_code like '%" + obj.user_code + "%'"
            }
            if (obj.sex != "") {
                sql_where += " and w.sex='" + obj.sex + "'"
            }
            def role = Long.parseLong(obj.role)
            if (role > 0) {
                sql_where += " and w.role_id=" + obj.role
            }
            def page = Long.parseLong(obj.page)
            def rows = Long.parseLong(obj.rows)
            def sql = ""
            if (page > 0 || rows > 0) {
                def pageSql = " limit " + ((page - 1) * rows) + "," + rows
                sql = "select w.*,r.name as role_name from role r,tb_worker w where w.role_id=r.id " + sql_where + " order by id desc " + pageSql
            } else {
                sql = "select w.*,r.name as role_name from role r,tb_worker w where w.role_id=r.id " + sql_where + " order by id desc"
            }
            def sql_count = "select count(*) as count from tb_worker w where 1=1 " + sql_where

            //println(sql_count)
            def count = db.rows(sql_count)
            def total = count[0].count
            def result = db.rows(sql)
            return resultService.successPage(result, total)
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }
    }
    //添加员工
    def addWorker(obj) {
        try {

            println("addwork")
            def db = new Sql(dataSource)
            def worker = Worker.findByUserCode(obj.user_code)
            if (worker != null) {
                return resultService.failMsg("账户已存在")
            } else {
                def role_id = Long.parseLong(obj.role_id)
                def role = Role.findById(role_id)
                worker = new Worker();
                worker.role = role
                worker.sex = obj.sex
                worker.name = obj.name
                worker.userCode = obj.user_code
                //worker.pwd = DESUtil.getEncryptString(obj.pwd)
                worker.pwd = MD5Utils.getSaltMd5AndSha(obj.pwd)
                worker.avatarImgPath = "/"
                def save = worker.save()
                //println(save)
                if (save != null){
                    return resultService.successMsg("新增员工成功")
                }
                return resultService.error()
            }
        } catch (Exception e) {
            e.printStackTrace();
            return resultService.error()
        }

    }
    //修改角色
    def updateRole(obj) {
        try {
            Role role = Role.findById(obj.id)
            if (role != null) {
                Role have = Role.findByName(obj.name)
                if (have != null) {
                    if (have.id != obj.id) {
                        return resultService.failMsg("角色名称已存在")
                    }
                }
                role.name = obj.name
                role.save()
                return resultService.successMsg("修改成功")
            } else {
                return resultService.failMsg("修改异常")
            }
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }
    }
    //修改员工
    def undateWorker(obj) {
        try {
            //println(obj)
            def worker = Worker.findById(obj.id)
            def role = Role.findById(Long.parseLong(obj.role_id))
            worker.role = role
            worker.pwd = obj.pwd
            worker.name = obj.name
            worker.sex = obj.sex
            worker.userCode = obj.user_code
            worker.save()
            return resultService.successMsg("修改成功")
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }
    }
    //删除员工
    def delWorker(obj) {
        try {
            def worker = Worker.findById(obj.id)
            if (worker != null) {
                def db = new Sql(dataSource)
                def sql_del = "delete from tb_Worker where id=" + Long.parseLong(obj.id)
                //println(sql_del)
                 db.execute(sql_del)
                return resultService.successMsg("删除成功")
            } else {
                return resultService.failMsg("删除异常")
            }
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }
    }
    //删除
    def delRole(obj) {
        try {
            Role role = Role.findById(obj.id)
            if (role != null) {
                def db = new Sql(dataSource)
                def sql_del = "delete from tb_RoleMenu where role_id=" + role.id
                db.execute(sql_del)
                db.commit()
                role.delete()
                return resultService.successMsg("删除成功")
            } else {
                return resultService.failMsg("删除异常")
            }
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }
    }

    //查询角色菜单
    def queryRoleMenu(obj) {
        try {
            //println(obj)
            //需要角色ID
            def db = new Sql(dataSource)
            //查询一级菜单
            def sql_one = "select * from tb_menu where leve=0"
            //查询二菜单
            def sql_select = "select menu_id from tb_RoleMenu where role_id=" + obj.id
            def sql_two = "select *,'true' as status from tb_menu where leve!=0 and id in(" + sql_select + ")" +
                    " union all " +
                    "select *,'false' as status from tb_menu where leve!=0 and id not in(" + sql_select + ")"
            def oneMenu = db.rows(sql_one)
            def twoMenu = db.rows(sql_two)
            List resultList = new ArrayList()
            def queryTwoList = { oneId ->
                //println("oneId1111111111:" + oneId)
                List list = new ArrayList()
                for (a in twoMenu) {
                    //println("oneId:" + oneId)
                    if (a.leve == oneId) {
                        Map map = new HashMap()
                        map.put("id", a.id)
                        map.put("title", a.title)
                        map.put("expand", true)
                        map.put("checked", a.status == "true" ? true : false)
                        list.add(map)
                    }
                }
                return list
            }
            for (a in oneMenu) {
                Map map = new HashMap()
                map.put("id", a.id)
                map.put("title", a.title)
                map.put("children", queryTwoList(a.id))
                map.put("expand", true)
                resultList.add(map)
            }
            return resultService.successData(resultList)
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }
    }
    //角色菜单保存
    def saveRoleMenu(obj) {
        try {
            def db = new Sql(dataSource)
            def sql_del = "delete from tb_RoleMenu where role_id=" + obj.roleId
            def sql_insert = "insert into tb_RoleMenu(role_id,menu_id) " + obj.ids
            db.execute(sql_del)
            db.commit()
            db.execute(sql_insert)
            db.commit()
            return resultService.successMsg("保存成功")
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }
    }
    //菜单保存
    def addMenu(Object obj) {
        try {
            Menu menu = Menu.findByLabel(obj.label)
            if (menu != null) {
                return resultService.failMsg("菜单名称已存在")
            } else {
                if (obj.id == "0") {
                    menu = new Menu();
                    menu.level = 0
                    menu.closable = true
                    menu.icon = obj.icon
                    menu.label = obj.label
                    menu.path = obj.path
                    menu.save()
                    return resultService.successMsg("添加成功")
                } else {
                    menu = new Menu();
                    menu.level = Long.parseLong(obj.id)
                    menu.path = obj.path
                    menu.closable = true
                    menu.icon = obj.icon
                    menu.label = obj.label
                    menu.save()
                    return resultService.successMsg("添加成功")
                }
            }
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }
    }
    //菜单修改
    def updateMenu(String obj) {
        try {
            JSONObject jsonObject = JSON.parse(obj)
            Menu menu = Menu.findById(jsonObject.menuId)
            menu.label = jsonObject.label
            menu.path = jsonObject.path
            menu.icon = jsonObject.icon
            menu.closable = jsonObject.closable
            menu.level = jsonObject.level
            menu.save()
            return resultService.successMsg("修改成功")
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }
    }

    def login(Object obj) {
        try {
            String userCode=obj.user_code+""
            String pwd = obj.pwd+""
            if (userCode != null && pwd != null) {
                def worker = Worker.findByUserCode(userCode)
                if (worker != null) {
                  // def password = DESUtil.getEncryptString(obj.pwd)
                    println(worker.pwd)
                    if (!MD5Utils.getSaltverifyMd5AndSha(pwd,worker.pwd)) {
                        return resultService.failMsg("密码或帐号错误")
                    }
                    def workerid = "" + worker.id
                    this.saveCookie(workerid)
                    return resultService.successLogin(workerid)
                }
                return resultService.failMsg("密码或帐号错误")
            } else {
                return resultService.error()
            }
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }
    }

    def saveCookie(String token) {
        def response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse()
        CookieUtil.addCookie(response, "test.com", "/", "workerId", token, -1, false)
    }
//    def saveRoleMenu(obj){
//        try {
//           String ids=obj.ids
//            def arr=ids.split(",")
//            Role role=Role.findById(Long.parseLong(obj.roleId))
//            for(a in arr){
//                Menu menu=Menu.findById(Long.parseLong(a))
//                RoleMenu roleMenu=new RoleMenu()
//                roleMenu.menu=menu
//                roleMenu.role=role
//                roleMenu.save()
//            }
//        } catch (Exception e) {
//            e.printStackTrace()
//            return resultService.error()
//        }
//    }
}
