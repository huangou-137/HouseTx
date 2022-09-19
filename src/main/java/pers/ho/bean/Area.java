package pers.ho.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 黄欧
 * @Date 2021-02-24 11:56
 */
@Data
public class Area implements Serializable {
    private static final long serialVersionUID = 1L;

    private Short id;           //区域编号（自增）
    
    private Short pid;          //所属区域（父区域）编号

    private String name;        //区域名

    private Byte type;          //区域行政等级（一共四级，0-3，国省地县）

    private String wholeName;   //完整名称（不包括国级）

    /**
     * 中华人民共和国行政等级
     */
    public static final Byte ZH_TYPE = 0;
    /**
     * 省级
     */
    public static final Byte PROVINCE_TYPE = 1;
    /**
     * 地级（市）
     */
    public static final Byte CITY_TYPE = 2;
    /**
     * 县级（区）
     */
    public static final Byte COUNTY_TYPE = 3;

    public static Byte getMinType() {
        return ZH_TYPE;
    }
    public static Byte getMaxType() {
        return COUNTY_TYPE;
    }
    /**
     * 判断该Byte值是否为正确的行政等级
     */
    public static boolean isTrueType(Byte value) {
        return value != null && value >= getMinType() && value <= getMaxType();
    }

    public Area() {}

    public Area(Short id, Short pid, String name, Byte type) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        setType(type);
    }

    public void setType(Byte type) {
        if (isTrueType(type)) {
            this.type = type;
        } else {
            //错误级别则设置成默认值（县级）
            this.type = COUNTY_TYPE;
        }
    }

    @Override
    public String toString() {
        return getWholeName();
    }

}
