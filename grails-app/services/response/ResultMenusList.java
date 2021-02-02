package response;

import system.ChildMenus;
import system.Metas;

public class ResultMenusList {
    int id;
    String path; //路由
    String name;
    long leve; //0代表一级菜单 其他代表父级级菜单
    boolean closable; // (true)
    String title ;//显示在侧边栏、面包屑和标签栏的文字
    String icon ;//该页面在左侧菜单、面包屑和标签导航处显示的图标
    boolean hideInBread; //(true) 设为falste后此级路由将不会出现在面包屑中
    boolean hideInMenu ;// (true) 设为falst后在左侧菜单不会显示该页面选项
    boolean notCache ;//: (true) 设为falst后页面在切换标签后不会缓存，如果需要缓存，无需设置这个字段，而且需要设置页面组件name属性和路由配置的name一致
    ChildMenusList[] children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLeve() {
        return leve;
    }

    public void setLeve(long leve) {
        this.leve = leve;
    }

    public boolean isClosable() {
        return closable;
    }

    public void setClosable(boolean closable) {
        this.closable = closable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isHideInBread() {
        return hideInBread;
    }

    public void setHideInBread(boolean hideInBread) {
        this.hideInBread = hideInBread;
    }

    public boolean isHideInMenu() {
        return hideInMenu;
    }

    public void setHideInMenu(boolean hideInMenu) {
        this.hideInMenu = hideInMenu;
    }

    public boolean isNotCache() {
        return notCache;
    }

    public void setNotCache(boolean notCache) {
        this.notCache = notCache;
    }

    public ChildMenusList[] getChildren() {
        return children;
    }

    public void setChildren(ChildMenusList[] children) {
        this.children = children;
    }
}
