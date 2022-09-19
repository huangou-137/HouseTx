package pers.ho.beancol;

/**
 * @author 黄欧
 * @Date 2021-09-27 13:37
 */
public enum UserCol {
    UID("uid"),
    USERNAME("username"),
    LOGIN_PASS("login_pass"),
    PHONE("phone"),
    EMAIL("email"),
    TX_PASS("tx_pass"),
    DESC("`desc`");

    private final String name;

    UserCol(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
