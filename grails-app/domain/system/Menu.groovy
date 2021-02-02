package system

class Menu {
    String path //路由
    String name
    long leve //0代表一级菜单 其他代表父级级菜单
    boolean closable // (true)
    String title //显示在侧边栏、面包屑和标签栏的文字
    String icon //该页面在左侧菜单、面包屑和标签导航处显示的图标
    boolean hideInBread //(true) 设为falste后此级路由将不会出现在面包屑中
    boolean hideInMenu // (true) 设为falst后在左侧菜单不会显示该页面选项
    boolean notCache //: (true) 设为falst后页面在切换标签后不会缓存，如果需要缓存，无需设置这个字段，而且需要设置页面组件name属性和路由配置的name一致

    static constraints = {
        name(unique: true)
        closable(blank: true)
        title(unique: true)
        hideInBread(blank: true)
        hideInMenu(blank: true)
        notCache(blank: true)

    }
    static mapping = {
        hideInBread defaultValue: true
        hideInMenu defaultValue: true
        notCache defaultValue: true
        closable defaultValue: true
        version false
        table "tb_menu"
    }
}
