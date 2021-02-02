package system

import grails.converters.JSON

class UploadController {
    def uploadService
    def resultService
    def index() { }
    def uploadImage(){
        try {
            render uploadService.uploadImage(params,request) as JSON
        } catch (Exception e) {
            e.printStackTrace()
            render resultService.error() as JSON
        }
    }
    def imageListByWorkerId(){
        try {
            render uploadService.imgByWorkerId(params.parse) as JSON
        } catch (Exception e) {
            e.printStackTrace()
            render resultService.error() as JSON
        }
    }

    def delImgById(){
        try {
            render uploadService.delImgById(params.parse) as JSON
        } catch (Exception e) {
            return  resultService.error() as JSON
        }
    }
}
