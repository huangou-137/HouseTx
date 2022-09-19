package pers.ho.beancol;

/**
 * @author 黄欧
 * @Date 2021-09-27 0:07
 */
public enum OrderCol {
    ID("id"),
    HOUSE_ID("house_id"),
    SELLER_ID("seller_id"),
    BUYER_ID("buyer_id"),
    STATE("state"),
    LAUNCH_TIME("launch_time"),
    ACCEPT_TIME("accept_time"),
    END_TIME("end_time");

    private final String name;
    private static final String tableName = "`t_order`";

    OrderCol(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getWholeName() {
        return tableName + '.' + name;
    }

}
