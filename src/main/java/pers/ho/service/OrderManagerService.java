package pers.ho.service;

import com.github.pagehelper.PageInfo;
import pers.ho.bean.Order;
import pers.ho.bean.Result;

/**
 * @author 黄欧
 * @create 2021-04-11 18:27
 * 负责订单管理的业务处理
 */
public interface OrderManagerService {

    /**
     * 根据ID来查询订单
     */
    Order getOrder(Integer id);

    /**
     * 添加订单
     * @param order 其中id字段可以为空
     * @return Result中的value值：添加订单成功则为订单号，失败则为null(且Result中有提示信息)
     */
    Result<Integer> addOrder(Order order);

    /**
     * 根据ID值修改订单
     */
    boolean updateOrder(Order order, Integer id);

    /**
     * 根据ID删除订单
     */
    boolean delOrder(Integer id);

    /**
     * 分页查询所有订单
     * @param pageNum 当前页码
     * @param pageSize 分页查询个数
     */
    PageInfo<Order> pageOrders(int pageNum, int pageSize);

    /**
     * 分页查询某个状态下的所有订单
     * @param state 订单状态，为空 或 越界 则默认为所有状态
     * @param pageNum 当前页码
     * @param pageSize 分页查询个数
     */
    PageInfo<Order> pageOrders(Byte state, int pageNum, int pageSize);

    /**
     * 管理员是否能确认该交易订单
     * @return 能则返回null，不能则返回提示信息
     */
    String canEnsure(Integer orderId);

    /**
     * 管理员确认订单最终状态，状态变成交易完成 || 交易失败
     * @param orderId 订单编号
     * @param isFinished true为交易完成；false为交易失败
     * @return 确认成功则返回null，失败则返回提示信息
     */
    String ensureOrder(Integer orderId, Boolean isFinished);

}
