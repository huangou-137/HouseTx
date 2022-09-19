package pers.ho.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pers.ho.bean.Order;
import pers.ho.bean.Result;
import pers.ho.service.OrderManagerService;
import pers.ho.utils.ParseUtils;
import pers.ho.utils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/manager")
public class OrderManagerController {
    @Autowired
    private OrderManagerService orderManagerService;

    /**
     * 获取待确认交易的订单列表（相当于订单状态值为卖方接受，交易中……）
     */
    @RequestMapping("/orders/ensuring")
    public String page_ensure(HttpServletRequest req, Map<String, Object> model) {
        return page(req, model, Order.OrderState.ACCEPT.getVal());
    }

    /**
     * 根据状态等获取订单列表（分页）,可选请求参数：页码pageNo、显示条数pageSize
     */
    @RequestMapping("/orders")
    public String page(HttpServletRequest req, Map<String, Object> model,
                       @RequestParam(defaultValue = "-128") Byte state) {
        int pageNum = ParseUtils.parseInt(req,"pageNum", 1);
        int pageSize = ParseUtils.parseInt(req,"pageSize", 15);
        PageInfo<Order> pageInfo = orderManagerService.pageOrders(state, pageNum, pageSize);
        if (Order.OrderState.isWrongState(state)) {
            model.put("pageUrl", "manager/orders?");
        } else {
            req.setAttribute("state", state);
            model.put("pageUrl", "manager/orders?state=" + state);
        }
        model.put("pageInfo", pageInfo);
        return "manager/order/page";
    }

    /**
     * 获取订单的详细信息，只响应<b>GET</b>请求
     */
    @GetMapping(value = "/order/{orderId}")
    public String orderDetails(@PathVariable Integer orderId, Map<String, Object> model) {
        if (orderId == null) {
            model.put("msg", "订单ID异常！");
        } else {
            Order order = orderManagerService.getOrder(orderId);
            if (order == null) {
                model.put("msg", "没有找到该订单！");
            } else {
                model.put("order", order);
                model.put("canEnsure", orderManagerService.canEnsure(orderId) == null);
            }
        }
        return "manager/order/details";
    }

    /**
     * 处理确认订单的请求，需要请求参数：交易完成与否
     */
    @RequestMapping("/order/ensure/{orderId}")
    public String ensure(@PathVariable Integer orderId, Map<String, Object> model,
                               @RequestParam(defaultValue = "false") Boolean finished) {
        String tips = orderManagerService.ensureOrder(orderId, finished);
        if (tips == null) {
            return WebUtils.afterSuccess(model, "确认订单", 5, "manager/order/" + orderId,
                    "订单详情页面", orderId, null);
        } else {
            model.put("msg", tips);
            return orderDetails(orderId, model);    //重新获取订单详情
        }
    }

    /**
     * 处理删除订单的请求，只响应<b>DELETE</b>请求
     */
    @DeleteMapping(value = "/order/{orderId}")
    public String del(@PathVariable Integer orderId, Map<String, Object> model) {
        if (orderManagerService.delOrder(orderId)) {
            return WebUtils.afterSuccess(model, "删除订单", 5, "manager/orders"
                    , "订单列表页面", orderId, null);
        } else {
            model.put("msg", "删除订单失败!");
            return orderDetails(orderId, model);    //重新获取订单详情
        }
    }

    /**
     * 修改订单前的准备————获取订单最新信息
     */
    @RequestMapping("/order/toUpd/{orderId}")
    public String toUpd(@PathVariable Integer orderId, Map<String, Object> model) {
        Order order = orderManagerService.getOrder(orderId);
        if (order == null) {
            model.put("msg", "没有找到该订单！");
        } else {
            model.put("order", order);
        }
        return "manager/order/upd";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        // 忽略绑定实体类中某些字段属性，@RequestParam以及HttpServletRequest还可以取出
        webDataBinder.setDisallowedFields("launchTime","acceptTime","endTime");
    }

    /**
     * 通过请求参数设置Order对象的各个时间
     */
    private void setOrderTime(HttpServletRequest req, Order order) {
        // 1、将前端表单"datetime-local"类型输入框的各个时间值转换成TimeStamp类对象
        order.setLaunchTime(ParseUtils.parseTimestamp(req,"launchTime",null));
        order.setAcceptTime(ParseUtils.parseTimestamp(req,"acceptTime",null));
        order.setEndTime(ParseUtils.parseTimestamp(req,"endTime",null));
    }

    /**
     * 处理修改订单的请求，只响应<b>PUT</b>请求
     */
    @PutMapping("/order/{orderId}")
    public String upd(Order order, HttpServletRequest req, @PathVariable Integer orderId,
                            Map<String, Object> model) {
        setOrderTime(req, order);
        if (orderManagerService.updateOrder(order, orderId)) {
            return WebUtils.afterSuccess(model, "修改订单", 5, "manager/order/" + orderId,
                    "订单详情页面", orderId, null);
        } else {
            // 修改失败则重新进行 修改前准备
            model.put("msg", "订单更新失败！");
            return toUpd(orderId, model);
        }
    }

    /**
     * 处理添加订单的请求，只响应<b>POST</b>请求
     */
    @PostMapping("/order")
    public String add(Order order, HttpServletRequest req, Map<String, Object> model) {
        setOrderTime(req, order);
        Result<Integer> result = orderManagerService.addOrder(order);
        Integer orderId = result.getValue();
        if (orderId != null) {
            return WebUtils.afterSuccess(model, "添加订单", 7, "manager/order/" + orderId,
                    "订单详情页面", null, "订单号为：" + orderId);
        } else {
            // 添加失败则把回显信息保存到Request域中，并返回添加页面
            model.put("order", order);
            model.put("msg", result.getTipsStr());
            return "manager/order/add";
        }
    }

}
