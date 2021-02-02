package system

class Image {
    String fild //fastDFS返回的文件ID
    String fileName
    long fileSize
    String fileType
    String filePath
    String workerId
    String fileWidth
    String fileHeight
    static constraints = {
        fileWidth(blank: true)
        fileHeight(blank: true)
    }
    static  mapping = {
        version false
        table "tb_image"
    }
}
