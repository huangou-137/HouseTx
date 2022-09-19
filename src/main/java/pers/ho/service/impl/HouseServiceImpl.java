package pers.ho.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ho.bean.House;
import pers.ho.bean.House.HouseState;
import pers.ho.bean.Order;
import pers.ho.beancol.HouseCol;
import pers.ho.beancol.OrderCol;
import pers.ho.beancol.UserCol;
import pers.ho.mapper.HouseMapper;
import pers.ho.mapper.OrderMapper;
import pers.ho.mapper.UserMapper;
import pers.ho.service.HouseService;
import pers.ho.utils.CommonUtils;
import pers.ho.utils.ParseUtils;

import java.util.List;

/**
 * @author 黄欧
 * @create 2021-03-30 12:54
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class HouseServiceImpl implements HouseService {
    @Autowired
    protected HouseMapper houseMapper;

    @Override
    @Transactional(readOnly = true)
    public House getMyHouse(Integer houseId, Integer uid) {
        QueryWrapper<House> wrapper = new QueryWrapper<>();
        wrapper.eq(HouseCol.ID.getName(), houseId)
                .eq(HouseCol.UID.getName(), uid);
        return houseMapper.selectOne(wrapper);
    }

    @Override
    public Integer enlistHouse(House house, Integer uid) {
        if (house == null || uid == null) {
            return null;
        }
        house.setUid(uid);
        house.setState(HouseState.DEFAULT.getVal());
        if (house.getTime() == null) {
            house.setTime(CommonUtils.currentTimestamp());
        }
        return houseMapper.insert(house) > 0 ? house.getId() : null;
    }

    private Integer getHouseUid(Integer houseId) {
        return ParseUtils.parseInt(houseMapper.getColValById(houseId, HouseCol.UID));
    }
    private Byte getHouseState(Integer houseId) {
        return ParseUtils.parseByte(houseMapper.getColValById(houseId, HouseCol.STATE));
    }

    @Override
    @Transactional(readOnly = true)
    public String canUserReEnlist(Integer houseId, Integer uid) {
        if (houseId == null || uid == null) {
            return "房屋编号或用户ID为空";
        }
        // 【判断一】用户ID是否与房屋房主ID对应得上
        Integer houseUid = getHouseUid(houseId);
        if (houseUid == null){
            return "找不到该房屋";
        } else if(!houseUid.equals(uid)) {
            return "非房屋登记人，不能修改房屋信息";
        }
        // 【判断二】当前房屋状态是否为交易中或已出售
        Byte state = getHouseState(houseId);
        if (state == null || HouseState.TRADING.equalsVal(state) || HouseState.SOLD.equalsVal(state)) {
            return "该房屋不能被修改";
        }
        return null;
    }

    @Override
    public boolean reEnlistHouse(Integer id, Integer uid, House house) {
        if (canUserReEnlist(id,uid) != null) {
            return false;   //没权限更改该房子信息
        }
        house.setId(id);
        house.setUid(null);         // 房主不能修改登记者ID
        house.setState(HouseState.DEFAULT.getVal());
        if (house.getTime() == null) {
            house.setTime(CommonUtils.currentTimestamp());
        }
        return houseMapper.updateById(house) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canUserDelHouse(Integer houseId, Integer uid) {
        if (houseId == null || uid == null) {
            return false;
        }
        // 【判断一】用户ID是否与房屋房主ID对应得上
        Integer houseUid = getHouseUid(houseId);
        if (houseUid == null || !houseUid.equals(uid)) {
            return false;
        }
        // 【判断二】当前房屋状态是否为交易中或已出售
        Byte state = getHouseState(houseId);
        return !(state == null || HouseState.TRADING.equalsVal(state) || HouseState.SOLD.equalsVal(state));
    }

    @Override
    public boolean delHouseByOwner(Integer id, Integer uid, String txPass) {
        if (!canUserDelHouse(id,uid)) {
            return false;    //该用户没有权限删除房子
        }
        // 获取交易密码并判断是否一致
        String pass = CommonUtils.getBean(UserMapper.class).getColValByUid(uid, UserCol.TX_PASS);
        if (pass != null && pass.equals(txPass)) {
            return houseMapper.deleteById(id) > 0;
        } else {
            return false;
        }
    }

    private QueryWrapper<House> getBriefWrapper() {
        QueryWrapper<House> wrapper = new QueryWrapper<>();
        // 要查询的房子简略信息列有：
        String[] colNames = HouseCol.getNames(HouseCol.ID, HouseCol.AREA_ID, HouseCol.ADDRESS,
                HouseCol.KIND_ID, HouseCol.TYPE_ID, HouseCol.SIZE, HouseCol.PRICE);
        wrapper.select(colNames);
        return wrapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<House> listBriefHousesByOwner(Integer uid) {
        QueryWrapper<House> wrapper = getBriefWrapper();
        wrapper.eq(HouseCol.UID.getName(), uid);
        return houseMapper.selectList(wrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<House> listBriefHousesByOwner(Integer uid, Byte state) {
        QueryWrapper<House> wrapper = getBriefWrapper();
        wrapper.eq(HouseCol.UID.getName(), uid);
        if (!House.HouseState.isWrongState(state)) {
            wrapper.eq(HouseCol.STATE.getName(), state);
        }
        return houseMapper.selectList(wrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canUserBuyHouse(Integer houseId, Integer uid) {
        if (houseId == null || uid == null) {
            return false;
        }
        // 【判断一】用户ID是否与房屋房主ID对应得上
        Integer houseUid = getHouseUid(houseId);
        if (houseUid == null || houseUid.equals(uid)) {
            return false;       //不能购买自己登记的房子
        }
        // 【判断二】当前房屋状态是否为待售中
        return HouseState.NORMAL.equalsVal(getHouseState(houseId));
    }

    @Override
    @Transactional(readOnly = true)
    public House getBuyingHouse(Integer orderId, Integer buyerId) {
        OrderMapper orderMapper = CommonUtils.getBean(OrderMapper.class);
        Integer houseId = ParseUtils.parseInt(orderMapper.getColValById(orderId, OrderCol.HOUSE_ID));
        if (houseId != null) {
            House house = houseMapper.selectById(houseId);
            if (house != null) {
                Byte houseState = house.getState();
                if (HouseState.NORMAL.equalsVal(houseState)) {
                    return house;           //(1)房产状态为【待售中】
                } else {
                    Byte orderState = ParseUtils.parseByte(orderMapper.getColValById(orderId, OrderCol.STATE));
                    if (HouseState.TRADING.equalsVal(houseState)) {
                        if (Order.OrderState.ACCEPT.equalsVal(orderState)) {
                            return house;   //(2)房产状态为【交易中】,订单状态为【交易接受】
                        }
                    } else if (HouseState.SOLD.equalsVal(houseState)) {
                        if (Order.OrderState.FINISHED.equalsVal(orderState)) {
                            return house;   //(3)房产状态为【已出售】,订单状态为【交易完成】
                        }
                    }
                }
            }
        }
        return null;
    }

    private QueryWrapper<Order> getFinishedOWrapper(Integer uid) {
        return new QueryWrapper<Order>().eq(OrderCol.BUYER_ID.getName(), uid)
                .eq(OrderCol.STATE.getName(), Order.OrderState.FINISHED.getVal());
    }

    private QueryWrapper<House> getSoldHWrapper(QueryWrapper<House> wrapper, Integer houseId) {
        if (wrapper == null) {
            wrapper = new QueryWrapper<>();
        }
        return wrapper.eq(HouseCol.ID.getName(), houseId)
                .eq(HouseCol.STATE.getName(), HouseState.SOLD.getVal());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> myBoughtHouseOrders(Integer uid) {
        OrderMapper mapper = CommonUtils.getBean(OrderMapper.class);
        QueryWrapper<Order> oWrapper = getFinishedOWrapper(uid);
        //订单简略信息（订单ID、房产ID、卖方ID、买方ID、状态）
        oWrapper.select(OrderCol.ID.getName(), OrderCol.HOUSE_ID.getName(), OrderCol.SELLER_ID.getName(),
                OrderCol.BUYER_ID.getName(),OrderCol.STATE.getName());
        List<Order> orders = mapper.selectList(oWrapper);
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order == null || order.getHouseId() == null) {
                orders.remove(i);
            } else {
                House house = houseMapper.selectOne(getSoldHWrapper(getBriefWrapper(),order.getHouseId()));
                if (house == null) {
                    orders.remove(i);
                } else {
                    order.setHouse(house);
                }
            }
        }
        return orders;
    }

    @Override
    @Transactional(readOnly = true)
    public Order myBoughtHouseOrder(Integer uid, Integer houseId) {
        OrderMapper mapper = CommonUtils.getBean(OrderMapper.class);
        QueryWrapper<Order> wrapper = getFinishedOWrapper(uid);
        wrapper.eq(OrderCol.HOUSE_ID.getName(), houseId);
        Order order = mapper.selectOne(wrapper);
        if (order != null) {
            order.setHouse(houseMapper.selectOne(getSoldHWrapper(null, houseId)));
        }
        return order;
    }

}