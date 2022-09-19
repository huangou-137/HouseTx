package pers.ho.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@TableName("t_house")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class House implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;         //房子编号

    private Integer uid;        //房主（登记人）ID

    private Short areaId;       //所属区域编号

    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String address;     //详细地址

    private Byte kindId;        //房子类别编号

    private Byte typeId;        //户型编号（?厅?室）

    private Short size;         //面积（平方米）

    private Double price;       //预期价格（元）

    @TableField("`desc`")
    private String desc;        //描述

    private Byte state;         //房子的状态

    @TableField("`time`")
    private Timestamp time;     //登记时间

    /**
     * @return 对房子状态的文字简述
     */
    public static String getStateSke(Byte val) {
        HouseState state = HouseState.valueOf(val);
        return state == null ? "" : state.getSke();
    }

    /**
     * @return 所有房产状态的值list集合
     */
    public static List<Byte> genStateList(){
        List<Byte> list = new ArrayList<>();
        for (HouseState state : HouseState.values()) {
            list.add(state.val);
        }
        return list;
    }

    public enum HouseState {
        /**
         * 默认状态，审核中
         */
        DEFAULT((byte) 0,"审核中"),
        FAILED ((byte)-1,"审核失败"),
        /**
         * 审核成功，待售中
         */
        NORMAL ((byte) 1,"待售中"),
        TRADING((byte) 2,"交易中"),
        SOLD   ((byte) 3,"已出售");

        private final Byte val;
        private final String ske;

        HouseState(Byte val, String ske) {
            this.val = val;
            this.ske = ske;
        }

        public Byte getVal() {
            return val;
        }

        public String getSke() {
            return ske;
        }

        public boolean equalsVal(Byte val) {
            return this.val.equals(val);
        }

        public static HouseState valueOf(Byte val){
            for (HouseState state : values()) {
                if (state.equalsVal(val)) {
                    return state;
                }
            }
            return null;
        }

        public static boolean isWrongState(Byte val){
            return valueOf(val) == null;
        }

    }

}
