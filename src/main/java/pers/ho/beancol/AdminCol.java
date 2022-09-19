package pers.ho.beancol;

/**
 * @author 黄欧
 * @Date 2021/11/15 14:44
 */
public enum AdminCol {

    ID("admin_id"),
    ADMIN_NAME("admin_name"),
    PASS("pass"),
    TEL("tel"),
    EMAIL("email"),
    DESC("`desc`");

    private final String name;

    AdminCol(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
