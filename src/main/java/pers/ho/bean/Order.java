package pers.ho.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@TableName("t_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;             //订单编号*

    private Integer houseId;        //房屋编号*

    private Integer sellerId;       //卖方ID*

    private Integer buyerId;        //买方ID*

    private Byte state;             //交易状态*

    private Timestamp launchTime;   //买方发起时间

    private Timestamp acceptTime;   //卖家接受时间，可为空

    private Timestamp endTime;      //订单结束时间，可为空

    @TableField(exist = false)
    private House house;            //相关房产（多对一）

    /**
     * 卖方身份标识符
     */
    public static final String SELLER = "seller";
    /**
     * 买方身份标识符
     */
    public static final String BUYER = "buyer";

    public Order(Integer houseId, Integer sellerId, Integer buyerId, OrderState state, Timestamp launchTime) {
        this(null, houseId, sellerId, buyerId, state.getVal(), launchTime,null,null,null);
    }

    /**
     * @return 对订单状态的文字详细描述
     */
    public static String getStateDesc(Byte val) {
        OrderState state = OrderState.valueOf(val);
        return state == null ? "" : state.getDesc();
    }

    /**
     * @return 对订单状态的文字简单描述
     */
    public static String getStateSke(Byte val) {
        OrderState state = OrderState.valueOf(val);
        return state == null ? "" : state.getSke();
    }

    /**
     * @return 所有订单状态的值的list集合
     */
    public static List<Byte> genStateList(){
        List<Byte> list = new ArrayList<>();
        for (OrderState state : OrderState.values()) {
            list.add(state.val);
        }
        return list;
    }

    public enum OrderState {
        LAUNCH  ( (byte) 0,"发起","买方发起了交易"),
        ACCEPT  ( (byte) 1,"接受","卖方接受了交易，正在交易中……"),
        FINISHED( (byte) 2,"成功","交易已成功完成"),
        CANCEL  ( (byte)-1,"取消","买方取消了交易"),
        REFUSE  ( (byte)-2,"拒绝","卖方拒绝了交易"),
        FAILED  ( (byte)-3,"失败","交易失败");

        private final Byte val;     //值
        private final String ske;   //简述
        private final String desc;  //详细描述

        OrderState(Byte val, String ske, String desc) {
            this.val = val;
            this.ske = ske;
            this.desc = desc;
        }

        public Byte getVal() {
            return val;
        }

        public String getSke() {
            return ske;
        }

        public String getDesc() {
            return desc;
        }

        public boolean equalsVal(Byte val) {
            return this.val.equals(val);
        }

        public static OrderState valueOf(Byte val) {
            for (OrderState state : values()) {
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
