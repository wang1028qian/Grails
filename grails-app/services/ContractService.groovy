import grails.transaction.Transactional
import org.hibernate.Transaction
import system.Contract

import java.text.SimpleDateFormat

@Transactional
class ContractService {
    /**
     * 为开发环境创建初始化数据
     */
    def populateForDevelopEnv(){
        def simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        new Contract(name: "一期", signDate: simpleDateFormat.parse("2017-09-03 00:00:00")).save()
        new Contract(name: "二期", signDate: simpleDateFormat.parse("2017-10-30 00:00:00")).save()
        new Contract(name: "三期", signDate: simpleDateFormat.parse("2018-01-10 00:00:00")).save()
        new Contract(name: "四期", signDate: simpleDateFormat.parse("2018-03-07 00:00:00")).save()
        new Contract(name: "五期", signDate: simpleDateFormat.parse("2018-10-05 00:00:00")).save()
        new Contract(name: "六期", signDate: simpleDateFormat.parse("2019-01-20 00:00:00")).save()
    }
    def list(Map params){
        Contract.list(params)
    }
}
