package pers.ho.beancol;

/**
 * @author 黄欧
 * @Date 2021-09-23 20:59
 */
public enum HouseCol {
    ID("id"),
    UID("uid"),
    AREA_ID("area_id"),
    ADDRESS("address"),
    KIND_ID("kind_id"),
    TYPE_ID("type_id"),
    SIZE("size"),
    PRICE("price"),
    DESC("`desc`"),
    STATE("state"),
    TIME("`time`");

    private final String name;
    private static final String tableName = "`t_house`";

    HouseCol(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getWholeName() {
        return tableName + '.' + name;
    }

    public static String[] getNames(HouseCol... houseCols){
        if (houseCols == null || houseCols.length <= 0) {
            return null;
        }
        String[] names = new String[houseCols.length];
        for (int i = 0; i < houseCols.length; i++) {
            names[i] = houseCols[i].getName();
        }
        return names;
    }

}
