package pers.ho.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ho.bean.House;
import pers.ho.bean.Order;
import pers.ho.bean.Order.OrderState;
import pers.ho.bean.Result;
import pers.ho.beancol.OrderCol;
import pers.ho.beancol.UserCol;
import pers.ho.mapper.UserMapper;
import pers.ho.service.OrderService;
import pers.ho.utils.CommonUtils;
import pers.ho.utils.ParseUtils;

import java.util.List;

/**
 * @author 黄欧
 * @Date 2021-04-01 22:34
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class OrderServiceImpl extends BaseOrderService implements OrderService {

    /**
     * 验证用户的交易密码 是否为错误的
     */
    private boolean isFalseTxPass(Integer uid, String txPass) {
        String txPassData = CommonUtils.getBean(UserMapper.class).getColValByUid(uid, UserCol.TX_PASS);
        return txPassData == null || !txPassData.equals(txPass);
    }

    @Override
    @Transactional(readOnly = true)
    public Order getMyOrder(Integer id, Integer uid) {
        if (id == null || uid == null) {
            return null;
        }
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        // id = ? AND (buyerId = ? OR sellerId = ?)
        wrapper.eq(OrderCol.ID.getName(), id)
                .and(i -> i.eq(OrderCol.BUYER_ID.getName(),uid).or().eq(OrderCol.SELLER_ID.getName(),uid));
        return orderMapper.selectOne(wrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> myOrders(Integer uid, String identity) {
        if (uid == null) {
            return null;
        }
        QueryWrapper<Order> wrapper = getBriefQueryWrapper();
        // 单个用户不可能产生太多的购房订单，所以返回list而不是包装后的pageInfo
        if (Order.BUYER.equals(identity)) {
           wrapper.eq(OrderCol.BUYER_ID.getName(), uid);
        } else if (Order.SELLER.equals(identity)) {
            wrapper.eq(OrderCol.SELLER_ID.getName(), uid);
        } else {
            // 既有购买方，也有出售方
            wrapper.eq(OrderCol.BUYER_ID.getName(), uid).or().eq(OrderCol.SELLER_ID.getName(), uid);
        }
        return orderMapper.selectList(wrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> myOrders(Integer uid, String identity, Byte state) {
        if (OrderState.isWrongState(state)) {
            // 状态为空 或 错误，默认为所有状态下
            return myOrders(uid, identity);
        } else {
            QueryWrapper<Order> wrapper = getBriefQueryWrapper();
            wrapper.eq(OrderCol.STATE.getName(), state);
            if (Order.BUYER.equals(identity)) {
                wrapper.eq(OrderCol.BUYER_ID.getName(), uid);
            } else if (Order.SELLER.equals(identity)) {
                wrapper.eq(OrderCol.SELLER_ID.getName(), uid);
            } else {
                // 既有购买方，也有出售方
                wrapper.and(i -> i.eq(OrderCol.BUYER_ID.getName(), uid).or().eq(OrderCol.SELLER_ID.getName(), uid));
            }
            return orderMapper.selectList(wrapper);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String canBuyerLaunch(Integer houseId, Integer buyerId) {
        if (houseId == null) {
            return "房屋编号为空";
        } else if (buyerId == null) {
            return "购买方ID为空";
        }
        // 【判断一】该用户是否为房屋登记者
        Integer houseUid = getHouseUid(houseId);
        if (houseUid == null ) {
            return "房子异常！";
        }else if (houseUid.equals(buyerId)) {
            return "不能购买自己登记的房子";
        }
        // 【判断二】当前房屋状态是否为待售中
        if (!House.HouseState.NORMAL.equalsVal(getHouseState(houseId))) {
            return "不能购买非待售中状态的房子";
        }
        return null;
    }

    @Override
    public Result<Integer> launchOrder(Integer buyerId, Integer houseId, String buyerTxPass) {
        String tips = canBuyerLaunch(houseId, buyerId);
        if (tips != null){
            return new Result<>(tips);        // 没有权限对该房子发起订单
        }
        // 1、验证买家的交易密码
        if (isFalseTxPass(buyerId, buyerTxPass)) {
            return new Result<>("买方交易密码出错");
        }
        // 2、获取房子的登记者ID作为订单的卖方ID
        Integer sellerId = getHouseUid(houseId);
        if (sellerId == null) {
            return new Result<>("无法根据房屋编号获取房主ID");
        }
        // 3、产生订单并添加入库
        Order order = new Order(houseId, sellerId, buyerId, OrderState.LAUNCH, CommonUtils.currentTimestamp());
        if (orderMapper.insert(order) > 0) {
            return new Result<>(order.getId(),"");    //返回含订单号的Result
        } else {
            return new Result<>("发起订单失败");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String canBuyerCancel(Integer orderId, Integer buyerId) {
        if (orderId == null) {
            return "房屋编号为空";
        } else if (buyerId == null) {
            return "买方ID为空";
        }
        Integer bId = ParseUtils.parseInt(orderMapper.getColValById(orderId, OrderCol.BUYER_ID));
        if (bId == null) {
            return "该订单不存在";
        } else if (!bId.equals(buyerId)) {
            return "非买方不能取消该订单";
        }

        Byte state = getOrderState(orderId);
        if (OrderState.LAUNCH.equalsVal(state)) {
            return null;
        } else if (OrderState.REFUSE.equalsVal(state)) {
            return "订单已被卖方拒绝，无需取消";
        } else {
            return "订单状态非 “发起”，无法取消";
        }
    }

    @Override
    public String cancelFromBuyer(Integer buyerId, Integer orderId, String buyerTxPass) {
        // 1、判断该买家是否能取消订单
        String tips = canBuyerCancel(orderId, buyerId);
        if (tips != null) {
            return tips;
        }
        // 2、验证买家的交易密码
        if (isFalseTxPass(buyerId, buyerTxPass)) {
            return "买方交易密码出错";
        }
        // 3、修改订单状态为交易取消，并设置结束（取消）时间
        return updOrderState8Time(OrderState.CANCEL, OrderCol.END_TIME, orderId) ? null : "取消订单失败";
    }

    @Override
    @Transactional(readOnly = true)
    public String canSellerAccept(Integer orderId, Integer sellerId) {
        if (orderId == null) {
            return "订单编号为空";
        } else if (sellerId == null) {
            return "卖方ID为空";
        }

        Byte orderState = getOrderState(orderId);
        if (orderState == null) {
            return "该订单不存在";
        } else if (!OrderState.LAUNCH.equalsVal(orderState)){
            return "订单状态非 “发起”，无法接受";
        }

        Integer houseId = getHouseIdByOrderId(orderId);     //从订单中获取房屋编号
        if (!sellerId.equals(getHouseUid(houseId))) {
            return "不能接受非自己登记房产的订单";
        }
        if (!House.HouseState.NORMAL.equalsVal(getHouseState(houseId))) {
            return "房屋处于非“待售中”状态，不能出售，即不能接受其订单";
        }

        return null;
    }

    @Override
    public String acceptOrder(Integer sellerId, Integer orderId, String sellerTxPass) {
        // 1、判断卖家是否有权限接受订单
        String tips = canSellerAccept(orderId, sellerId);
        if (tips != null) {
            return tips;
        }
        // 2、验证卖家的交易密码
        if (isFalseTxPass(sellerId, sellerTxPass)) {
            return "卖方交易密码出错";
        }
        // 3、修改订单状态为交易接受(交易中)，并设置接受时间
        if(updOrderState8Time(OrderState.ACCEPT, OrderCol.ACCEPT_TIME, orderId)) {
            // 4、从订单中获取房屋编号
            Integer houseId = getHouseIdByOrderId(orderId);
            if (houseId == null) {
                throw new RuntimeException("接受交易事务异常：不能根据订单ID查询到房屋ID！");
            }
            // 5、修改房屋状态为“交易中”
            if (updHouseState(houseId, House.HouseState.TRADING)) {
                return null;
            } else {
                throw new RuntimeException("接受交易事务异常：不能同步修改房屋状态！");
            }
        } else {
            return "接受订单失败";
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String canSellerRefuse(Integer orderId, Integer sellerId) {
        if (orderId == null) {
            return "订单编号为空";
        } else if (sellerId == null) {
            return "卖方ID为空";
        }
        //【判断一】订单是否存在 && 订单的当前状态是否为交易发起
        Byte orderState = getOrderState(orderId);
        if (orderState == null) {
            return "该订单不存在";
        } else if (OrderState.CANCEL.equalsVal(orderState)) {
            return "订单已被买方取消，无需拒绝";
        } else if (!OrderState.LAUNCH.equalsVal(orderState)){
            return "订单状态非 “发起”，无法拒绝";
        }
        // 【判断二】该卖家是否为房屋房主
        Integer houseId = getHouseIdByOrderId(orderId);
        Integer houseUid = getHouseUid(houseId);
        if (houseUid == null || !houseUid.equals(sellerId)) {
            return "不能拒绝非自己登记房产的订单";
        }
        return null;
    }

    @Override
    public String refuseFromSeller(Integer sellerId, Integer orderId, String sellerTxPass) {
        // 1、判断卖家是否有权限拒绝订单
        String tips = canSellerRefuse(orderId, sellerId);
        if (tips != null){
            return tips;
        }
        // 2、验证卖家的交易密码
        if (isFalseTxPass(sellerId, sellerTxPass)) {
            return "卖方交易密码出错";
        }
        // 3、修改订单状态为交易拒绝，并设置结束（拒绝）时间
        return updOrderState8Time(OrderState.REFUSE, OrderCol.END_TIME, orderId) ? null : "拒绝订单失败";
    }

}
