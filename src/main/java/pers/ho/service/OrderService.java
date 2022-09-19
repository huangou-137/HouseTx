package pers.ho.service;

import pers.ho.bean.Order;
import pers.ho.bean.Result;

import java.util.List;

/**
 * @author 黄欧
 * @Date 2021-04-01 22:20
 * 处理普通用户的订单业务
 */
public interface OrderService {

    /**
     * 根据订单ID来查询该用户的某个订单
     */
    Order getMyOrder(Integer id, Integer uid);

    /**
     * 查询该用户的所有订单
     * @param identity 用户身份————"buyer"为购买方，"seller"为出售方，其余为 两者皆是
     */
    List<Order> myOrders(Integer uid, String identity);

    /**
     * 查询该用户的所有订单
     * @param identity 用户身份————"buyer"为购买方，"seller"为出售方，其余为 两者皆是
     * @param state 订单状态，状态为空 或 越界，默认为所有状态下
     */
    List<Order> myOrders(Integer uid, String identity, Byte state);

    /**
     * 用户是否能对该房屋发起交易
     * @return 能则返回null，不能则返回提示信息
     */
    String canBuyerLaunch(Integer houseId, Integer buyerId);

    /**
     * 买方发起订单
     * @param houseId 相关房产ID
     * @return Result中的value值：发起订单成功则为订单号，失败则为null(且Result中有提示信息)
     */
    Result<Integer> launchOrder(Integer buyerId, Integer houseId, String buyerTxPass);

    /**
     * 买方是否能取消该订单
     * @return 能则返回null，不能则返回提示信息
     */
    String canBuyerCancel(Integer orderId, Integer buyerId);

    /**
     * 买方取消订单（成功则返回null，否则返回提示信息）
     */
    String cancelFromBuyer(Integer buyerId, Integer orderId, String buyerTxPass);

    /**
     * 卖家是否能接受该交易订单
     * @return 能则返回null，不能则返回提示信息
     */
    String canSellerAccept(Integer orderId, Integer sellerId);

    /**
     * 卖方接受订单（成功则返回null，否则返回提示信息）
     */
    String acceptOrder(Integer sellerId, Integer orderId, String sellerTxPass);

    /**
     * 卖家是否能拒绝该交易订单
     * @return 能则返回null，不能则返回提示信息
     */
    String canSellerRefuse(Integer orderId, Integer sellerId);

    /**
     * 卖方拒绝订单（成功则返回null，否则返回提示信息）
     */
    String refuseFromSeller(Integer sellerId, Integer orderId, String sellerTxPass);

}