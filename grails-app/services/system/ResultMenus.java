package system;

class ResultMenus {
    int id;
    Metas meta;
    String path; //路由
    String name;
    long leve; //0代表一级菜单 其他代表父级级菜单
    boolean closable; // (true)
    ChildMenus[] children;

    public ChildMenus[] getChildren() {
        return children;
    }

    public void setChildren(ChildMenus[] children) {
        this.children = children;
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

    public Metas getMeta() {
        return meta;
    }

    public void setMeta(Metas meta) {
        this.meta = meta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
