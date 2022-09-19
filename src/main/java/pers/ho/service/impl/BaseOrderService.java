package pers.ho.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import pers.ho.bean.House.HouseState;
import pers.ho.bean.Order;
import pers.ho.beancol.HouseCol;
import pers.ho.beancol.OrderCol;
import pers.ho.mapper.HouseMapper;
import pers.ho.mapper.OrderMapper;
import pers.ho.utils.CommonUtils;
import pers.ho.utils.ParseUtils;

/**
 * @author 黄欧
 * @Date 2021-04-11 19:04
 * 基础的订单处理业务
 */
public class BaseOrderService {

    @Autowired
    protected OrderMapper orderMapper;
    @Autowired
    protected HouseMapper houseMapper;

    protected Integer getHouseUid(Integer houseId) {
        return ParseUtils.parseInt(houseMapper.getColValById(houseId, HouseCol.UID));
    }
    protected Byte getHouseState(Integer houseId) {
        return ParseUtils.parseByte(houseMapper.getColValById(houseId, HouseCol.STATE));
    }
    protected boolean updHouseState(Integer houseId, HouseState state) {
        return houseMapper.updColById(houseId, HouseCol.STATE, state);
    }

    protected Byte getOrderState(Integer orderId) {
        return ParseUtils.parseByte(orderMapper.getColValById(orderId, OrderCol.STATE));
    }
    protected Integer getHouseIdByOrderId(Integer orderId) {
        return ParseUtils.parseInt(orderMapper.getColValById(orderId, OrderCol.HOUSE_ID));
    }

    protected QueryWrapper<Order> getQueryWrapper(OrderCol... cols) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        if (cols != null && cols.length > 0) {
            String[] colNames = new String[cols.length];
            for (int i = 0; i < cols.length; i++) {
                colNames[i] = cols[i].getName();
            }
            wrapper.select(colNames);
        }
        return wrapper;
    }
    /**
     * 默认简略信息：订单ID、房子ID、卖方ID、买方ID、订单状态
     */
    protected QueryWrapper<Order> getBriefQueryWrapper() {
        return getQueryWrapper(OrderCol.ID, OrderCol.HOUSE_ID, OrderCol.SELLER_ID, OrderCol.BUYER_ID, OrderCol.STATE);
    }

    /**
     * 根据ID更新订单的状态和某个时间，即：<br/>
     * …… SET `state` = ? AND 【timeCol.name】= ? WHERE `id` = ?
     * @param timeCol 必须是值类型为 <b>java.sql.Timestamp</b> 的订单时间相关列<br/>
     *                而更新的对应列值为系统当前时间
     */
    protected boolean updOrderState8Time(Order.OrderState state, OrderCol timeCol, Integer id) {
        UpdateWrapper<Order> wrapper = new UpdateWrapper<>();
                wrapper.set(OrderCol.STATE.getName(), state.getVal())
                .set(timeCol.getName(), CommonUtils.currentTimestamp())
                .eq(OrderCol.ID.getName(), id);
        return orderMapper.update(null, wrapper)> 0;
    }

}
