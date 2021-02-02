package system

import grails.gorm.transactions.Transactional
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import org.apache.commons.collections.map.LinkedMap

import java.lang.management.PlatformManagedObject
import java.time.OffsetDateTime

@Transactional
class UploadService {
    def resultService
    def dataSource
    def uploadImage(param, request) {
        def map
        try {
            def path = '/upload/img'
            def basePath = request.getSession().getServletContext().getRealPath(path)
            File saveFile = new File(basePath)
            if (!saveFile.exists()) {
                saveFile.mkdirs()
            }
            def file = request.getFile("file")
            def tiemName = System.currentTimeMillis()
            def image = new Image()
            map = new HashMap<String, String>()
            if (file) {
                String fileName = file.getOriginalFilename()
                def oldName = fileName.substring(0, fileName.indexOf("."))
                String extension = fileName.split('\\.')[-1] //文件后缀名
                def timeName = System.currentTimeMillis()
                if (extension.indexOf("jpg") >= 0 || extension.indexOf("jpeg") >= 0 || extension.indexOf("png") >= 0){
                    image.workerId = param.workerId
                    image.fileName = oldName
                    image.fileType = extension
                    image.fileSize = file.size
                    image.fild = tiemName
                    image.fileWidth = "400"
                    image.fileHeight = "400"
                    def newName = timeName+"."+extension
                    def resPath = "http://127.0.0.1:8089/img/"+newName
                    def filePath = basePath+File.separator+newName
                    image.filePath = resPath

                    def save = image.save()
                    if (save){
                        def uploadFile = new File(filePath)
                        image.fild = uploadFile
                        //if (!uploadFile.exists()){
                          //  uploadFile.mkdirs()
                        //}
                        map.put("name",oldName)


                        map.put("url",resPath)
                        file.transferTo(uploadFile)
                        /////        将文件存储在自己希望存放的地方以及格式
                        /////// f.transferTo( new File( "d://1.jpg", f.originalFilename))
                        return resultService.successData(map)
                    }
                    return resultService.failMsg("上传失败")
                }
                return resultService.failMsg("文件格式错误")
            }
            return resultService.error()
        }catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }
    }
    def imgByWorkerId (obj){
        try {
            def id = obj.workerId
            if(id != null || id != "" ){
                def db = new Sql(dataSource)
                def  sql =  "select i.file_name,i.file_path,i.id from tb_image i where worker_id ="+id
                def resList = db.rows(sql)
                def resultList =new ArrayList()
                for(a in resList){
                    def  map = new HashMap<String,String>()
                    map.put("name",a.file_name)
                    map.put("url",a.file_path)
                    map.put("id",a.id)
                    map.put("status","finished")
                    resultList.add(map)
                }
                return resultService.successData(resultList)
            }
            return resultService.error()
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }

    }
    def delImgById(obj){
        try {
            if (obj.id){
                def db =new Sql(dataSource)
                def sql_path = "select fild from tb_image where id ="+obj.id
                def sql_del = "delete from tb_image where id ="+obj.id
                def rows = db.rows(sql_path)
                def isDel = deleteFile(rows.fild)
                if (isDel){
                    db.execute(sql_del)
                    return resultService.successMsg("删除成功")
                }
            }
            return  resultService.error()
        } catch (Exception e) {
            e.printStackTrace()
            return resultService.error()
        }
    }

    def deleteFile(String sPaht){
        def flag = false
        def file = new File(sPaht)
        if (file.isFile() && file.exists()){
            file.delete()
            flag = true
        }
        return flag
    }
}
