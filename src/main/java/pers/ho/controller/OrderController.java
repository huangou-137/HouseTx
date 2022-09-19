package pers.ho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pers.ho.bean.Order;
import pers.ho.bean.Result;
import pers.ho.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 黄欧
 * @Date 2021-04-20 16:40
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 获取本登录用户作为买家或卖家（或买卖家）、处于某状态（或所有状态下）的订单
     */
    @RequestMapping("/myOrders")
    public String myOrders(HttpServletRequest req, @RequestParam(defaultValue = "-128") Byte state) {
        // 1、获取请求参数：用户身份 及 登录用户ID，并调用service层的方法获取订单列表
        String identity = req.getParameter("identity");
        List<Order> list = orderService.myOrders(UserController.getLoginUid(req), identity, state);
        // 2、保存List对象和状态值到Request域中，转发到我的订单信息页面
        req.setAttribute("orderList", list);
        req.setAttribute("identity", identity);
        if (!Order.OrderState.isWrongState(state) ) {
            req.setAttribute("state", state);
        }
        return "order/myOrders";
    }

    /**
     * 获取订单的详细信息
     */
    @RequestMapping("/myOrder/{orderId}")
    public String myOrderDetails(@PathVariable Integer orderId, Map<String,Object> model, HttpServletRequest req) {
        if (orderId == null) {
            model.put("msg", "订单ID异常！");
        } else {
            Integer loginUid = UserController.getLoginUid(req);
            Order myOrder = orderService.getMyOrder(orderId, loginUid);
            if (myOrder == null) {
                model.put("msg", "没有找到该订单！");
            } else {
                model.put("order", myOrder);
                model.put("canCancel", orderService.canBuyerCancel(orderId, loginUid) == null);
                model.put("canAccept", orderService.canSellerAccept(orderId, loginUid) == null);
                model.put("canRefuse", orderService.canSellerRefuse(orderId, loginUid) == null);
            }
        }
        return "order/orderDetails";
    }

    /**
     * 发起订单前的准备：检查有无权限发起
     */
    @RequestMapping("/toLaunch/{houseId}")
    public String toLaunch(@PathVariable Integer houseId, HttpServletRequest req) {
        // 1、获取本登录用户ID，并判断其能否对该房屋发起订单
        String tips = orderService.canBuyerLaunch(houseId, UserController.getLoginUid(req));
        if (tips == null) {
            // 2、设置交易页面的操作类型以及表单提交action
            req.setAttribute("id", houseId);
            req.setAttribute("doType","发起");
            req.setAttribute("action","order/launch/"+houseId);
            // 3、请求转发到订单交易操作页面
            return "order/orderTx";
        } else {
            // 2、无权限，重新获取房屋详情
            req.setAttribute("msg", tips);
            return "forward:/client/house/" + houseId;
        }
    }

    /**
     * 处理发起订单的请求
     */
    @RequestMapping("/launch/{houseId}")
    public String launch(@PathVariable Integer houseId, HttpServletRequest req){
        // 1、验证验证码
        if (CheckCodeController.verify(req)) {
            // 2、获取请求参数：买方交易密码
            String txPass = req.getParameter("txPass");
            // 3、获取本登录用户ID，并调用service层的方法发起订单
            Result<Integer> result = orderService.launchOrder(UserController.getLoginUid(req), houseId, txPass);
            Integer orderId = result.getValue();
            if (orderId != null) {
                req.setAttribute("orderId", orderId);
                req.setAttribute("news", "对房产（" + houseId + "）发起订单成功！订单号为：" + orderId);
                // 4、请求转发到订单操作成功页面
                return "order/success";
            } else {
                req.setAttribute("msg", result.getTipsStr());
            }
        }else {
            req.setAttribute("msg", "验证码错误！！");
        }
        // 4、验证码错误 或 发起失败，重新进行发起前准备
        return toLaunch(houseId, req);
    }

    /**
     * 取消订单前的准备：检查有无权限取消
     */
    @RequestMapping("/toCancel/{orderId}")
    public String toCancel(@PathVariable Integer orderId, HttpServletRequest req) {
        // 1、获取本登录用户ID，并判断其能否取消该订单
        String tips = orderService.canBuyerCancel(orderId, UserController.getLoginUid(req));
        if (tips == null) {
            // 2、设置交易页面的操作类型以及表单提交action
            req.setAttribute("id", orderId);
            req.setAttribute("doType","取消");
            req.setAttribute("action","order/cancel/"+orderId);
            // 3、请求转发到订单交易操作页面
            return "order/orderTx";
        } else {
            req.setAttribute("msg", tips);
            // 2、重新获取订单详情
            return "forward:/order/myOrder/" + orderId;
        }
    }

    /**
     * 处理取消订单的请求
     */
    @RequestMapping("/cancel/{orderId}")
    public String cancel(@PathVariable Integer orderId, HttpServletRequest req) {
        // 1、验证验证码
        if (CheckCodeController.verify(req)) {
            // 2、获取请求参数：买方交易密码
            String txPass = req.getParameter("txPass");
            // 3、获取本登录用户的用户ID，并执行取消订单业务方法
            String tips = orderService.cancelFromBuyer(UserController.getLoginUid(req), orderId, txPass);
            if (tips == null) {
                req.setAttribute("orderId", orderId);
                req.setAttribute("news", "取消订单（" + orderId + "）成功！");
                // 4、请求转发到订单操作成功页面
                return "order/success";
            } else {
                req.setAttribute("msg", tips);
            }
        }else {
            req.setAttribute("msg", "验证码错误！！");
        }
        // 4、重新进行取消前准备
        return toCancel(orderId, req);
    }

    /**
     * 拒绝订单前的准备：检查有无权限拒绝
     */
    @RequestMapping("/toRefuse/{orderId}")
    public String toRefuse(@PathVariable Integer orderId, HttpServletRequest req) {
        // 1、获取本登录用户ID，并判断其能否拒绝该订单
        String tips = orderService.canSellerRefuse(orderId, UserController.getLoginUid(req));
        if (tips == null) {
            // 2、设置交易页面的操作类型以及表单提交action
            req.setAttribute("id", orderId);
            req.setAttribute("doType","拒绝");
            req.setAttribute("action","order/refuse/"+orderId);
            // 3、请求转发到订单交易操作页面
            return "order/orderTx";
        } else {
            req.setAttribute("msg", tips);
            // 2、重新获取订单详情
            return "forward:/order/myOrder/" + orderId;
        }
    }

    /**
     * 处理拒绝订单的请求
     */
    @RequestMapping("/refuse/{orderId}")
    public String refuse(@PathVariable Integer orderId, HttpServletRequest req) {
        // 1、验证验证码
        if (CheckCodeController.verify(req)) {
            // 2、获取请求参数：卖方交易密码
            String txPass = req.getParameter("txPass");
            String tips = orderService.refuseFromSeller(UserController.getLoginUid(req), orderId, txPass);
            // 3、获取本登录用户Uid，并执行拒绝订单业务方法
            if (tips == null) {
                req.setAttribute("orderId", orderId);
                req.setAttribute("news", "拒绝订单（" + orderId + "）成功！");
                // 4、请求转发到订单操作成功页面
                return "order/success";
            } else {
                req.setAttribute("msg", tips);
            }
        }else {
            req.setAttribute("msg", "验证码错误！！");
        }
        // 4、重新进行拒绝前准备
        return toRefuse(orderId, req);
    }

    /**
     * 接受订单前的准备：检查有无权限接受
     */
    @RequestMapping("/toAccept/{orderId}")
    public String toAccept(@PathVariable Integer orderId, HttpServletRequest req) {
        // 2、获取本登录用户ID，并判断其能否接受该订单
        String tips = orderService.canSellerAccept(orderId, UserController.getLoginUid(req));
        if (tips == null) {
            // 3、设置交易页面的操作类型以及表单提交action
            req.setAttribute("id", orderId);
            req.setAttribute("doType","接受");
            req.setAttribute("action","order/accept/"+orderId);
            // 4、请求转发到订单交易操作页面
            return "order/orderTx";
        } else {
            req.setAttribute("msg", tips);
            // 3、重新获取订单详情
            return "forward:/order/myOrder/" + orderId;
        }
    }

    /**
     * 处理接受订单的请求
     */
    @RequestMapping("/accept/{orderId}")
    public String accept(@PathVariable Integer orderId, HttpServletRequest req) {
        // 1、验证验证码
        if (CheckCodeController.verify(req)) {
            // 2、获取请求参数：卖方交易密码
            String txPass = req.getParameter("txPass");
            // 3、获取本登录用户ID，并执行接受订单业务方法
            String tips = orderService.acceptOrder(UserController.getLoginUid(req), orderId, txPass);
            if (tips == null) {
                req.setAttribute("orderId", orderId);
                req.setAttribute("news", "接受订单（" + orderId + "）成功！");
                // 4、请求转发到订单操作成功页面
                return "order/success";
            } else {
                req.setAttribute("msg", tips);
            }
        }else {
            req.setAttribute("msg", "验证码错误！！");
        }
        // 4、重新进行接受前准备
        return toAccept(orderId, req);
    }

}
