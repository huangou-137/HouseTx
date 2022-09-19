package pers.ho.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ho.bean.House;
import pers.ho.bean.Order;
import pers.ho.bean.Order.OrderState;
import pers.ho.bean.Result;
import pers.ho.beancol.OrderCol;
import pers.ho.service.OrderManagerService;
import pers.ho.utils.CommonUtils;

/**
 * @author 黄欧
 * @Date 2021-04-11 19:23
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class OrderManagerServiceImpl extends BaseOrderService implements OrderManagerService {

    @Override
    @Transactional(readOnly = true)
    public Order getOrder(Integer id) {
        return orderMapper.selectById(id);
    }

    @Override
    public Result<Integer> addOrder(Order order) {
        if (order.getBuyerId() == null){
            return new Result<>("订单买家ID为空");
        } else {
            // 1、检查订单卖家ID是否为空
            if (order.getSellerId() == null) {
                // 2、为空则获取房屋的房主ID，并将其设置成卖方ID
                Integer uid = getHouseUid(order.getHouseId());
                if (uid == null) {
                    return new Result<>("该订单相关的房屋不存在");
                } else {
                    order.setSellerId(uid);
                }
            }
            if (order.getLaunchTime() == null) {
                order.setLaunchTime(CommonUtils.currentTimestamp());
            }
            // 3、调用mapper层的方法添加订单
            if (orderMapper.insert(order) > 0) {
                return new Result<>(order.getId(),"");     //返回包含订单号的Result
            } else {
                return new Result<>( "添加订单失败");
            }
        }
    }

    @Override
    public boolean updateOrder(Order order, Integer id) {
        order.setId(id);
        return orderMapper.updateById(order) > 0;
    }

    @Override
    public boolean delOrder(Integer id) {
        return orderMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<Order> pageOrders(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Order> wrapper = getBriefQueryWrapper();
        return new PageInfo<>(orderMapper.selectList(wrapper));
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<Order> pageOrders(Byte state, int pageNum, int pageSize) {
        if (OrderState.isWrongState(state)) {
            // 状态为空 或 错误，默认为所有状态下
            return pageOrders(pageNum, pageSize);
        } else {
            PageHelper.startPage(pageNum, pageSize);
            QueryWrapper<Order> wrapper = getBriefQueryWrapper().eq(OrderCol.STATE.getName(), state);
            return new PageInfo<>(orderMapper.selectList(wrapper));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String canEnsure(Integer orderId) {
        Byte orderState = getOrderState(orderId);
        if (orderState == null) {
            return "该订单不存在";
        } else if (!OrderState.ACCEPT.equalsVal(orderState)) {
            return "订单非交易中不可确认";
        }
        Byte houseState = getHouseState(getHouseIdByOrderId(orderId));
        if (houseState == null) {
            return "该订单相关的房产不存在";
        } else if (!House.HouseState.TRADING.equalsVal(houseState)) {
            return "房产非交易中不可确认订单";
        }
        return null;
    }

    @Override
    public String ensureOrder(Integer orderId, Boolean isFinished) {
        // 1、判断管理员是否有权限确认订单
        String tips = canEnsure(orderId);
        if (tips != null) {
            return tips;
        }
        // 2、根据判断设置相对应的订单和房屋状态
        OrderState orderState;
        House.HouseState houseState;
        if (isFinished) {
            orderState = OrderState.FINISHED;
            houseState = House.HouseState.SOLD;
        } else {
            orderState = OrderState.FAILED;
            houseState = House.HouseState.NORMAL;
        }
        // 3、修改订单状态，并设置结束时间
        if(updOrderState8Time(orderState, OrderCol.END_TIME, orderId)) {
            // 4、根据订单号获取房屋ID
            Integer houseId = getHouseIdByOrderId(orderId);
            if (houseId == null) {
                throw new RuntimeException("确认交易事务异常：不能根据订单号获取房屋ID！");
            }
            // 5、修改房屋状态
            if (updHouseState(houseId, houseState)) {
                return null;
            } else {
                throw new RuntimeException("确认交易事务异常：不能同步修改房屋状态！");
            }
        } else {
            return "确认订单失败";
        }
    }

}