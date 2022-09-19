package pers.ho.test;

import org.junit.Test;
import pers.ho.bean.Order;
import pers.ho.mapper.OrderMapper;
import pers.ho.service.OrderManagerService;
import pers.ho.service.OrderService;
import pers.ho.utils.CommonUtils;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

/**
 * @author 黄欧
 * @create 2021-03-26 16:14
 */
public class OrderTest {
    static OrderMapper mapper = CommonUtils.getBean(OrderMapper.class);
    static OrderManagerService mService = CommonUtils.getBean(OrderManagerService.class);
    static OrderService service = CommonUtils.getBean(OrderService.class);

    public static void main(String[] args) {
        List<Order> list = mService.pageOrders(1,6).getList();
        for (Order order : list) {
            System.out.println(order);
        }
    }

    @Test
    public void testGet1(){
        Integer id = 93;
        System.out.println(mService.getOrder(id));
    }

    @Test
    public void testUpd() {
        Integer id = 93;
        Integer houseId = 13;
        Integer sellerId = 3;
        Integer buyerId = 15;
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Byte state = Order.OrderState.CANCEL.getVal();
        Order order = new Order(id, houseId, sellerId, buyerId, state, time, time, time, null);
        System.out.println(mapper.updateById(order));
        System.out.println(order.getId());
    }

}
