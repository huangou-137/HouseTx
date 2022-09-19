package pers.ho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pers.ho.bean.House;
import pers.ho.bean.Order;
import pers.ho.service.HouseService;
import pers.ho.utils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 黄欧
 * @create 2021-03-30 12:49
 * 登录用户才能访问
 */
@Controller
@RequestMapping("/house")
public class HouseController extends BaseHouseController {
    @Autowired
    private HouseService houseService;

    private static Integer getLoginUid(HttpServletRequest req) {
        return UserController.getLoginUid(req);
    }

    /**
     * 获取本登录用户登记的某状态state下（或所有状态下）的所有房子
     */
    @RequestMapping("/myHouses")
    public String myHouses(HttpServletRequest req, @RequestParam(defaultValue = "-128") Byte state) {
        // 1、获取登录用户ID，并调用service层的方法获取房产列表
        List<House> list = houseService.listBriefHousesByOwner(getLoginUid(req), state);
        // 2、保存List对象和状态值到Request域中，并转发到我的房屋信息列表页面
        req.setAttribute("houseList", list);
        if (!House.HouseState.isWrongState(state)) {
            req.setAttribute("state", state);
        }
        return "house/myHouses";
    }

    /**
     * 获取登录用户某个房屋的详细信息
     */
    @GetMapping("/myHouse/{houseId}")
    public String myHouseDetails(@PathVariable Integer houseId, Map<String,Object> model, HttpServletRequest req) {
        if (houseId == null) {
            model.put("msg", "房产ID异常！");
        } else {
            boolean canDel = false;
            boolean canUpd = false;
            Integer loginUid = getLoginUid(req);
            House house = houseService.getMyHouse(houseId, loginUid);
            if (house == null) {
                req.setAttribute("msg", "没有找到该房子！");
            } else {
                // 是否能下架房产，修改房产？
                canDel = houseService.canUserDelHouse(houseId, loginUid);
                canUpd = (houseService.canUserReEnlist(houseId, loginUid) == null);
                model.put("house", house);
            }
            model.put("canDel", canDel);
            model.put("canUpd", canUpd);
        }
        return "house/myHouseDetails";
    }

    /**
     * 获取登录用户欲购买或已购买的某个房屋的详细信息
     */
    @GetMapping("/buy/{orderId}")
    public String myBuyingHouse(@PathVariable Integer orderId, Map<String,Object> model, HttpServletRequest req) {
        House house = houseService.getBuyingHouse(orderId, getLoginUid(req));
        if (house == null) {
            req.setAttribute("msg", "没有找到该房子或没有权限访问！");
        } else {
            model.put("house", house);
        }
        return "client/houseDetails";
    }

    /**
     * 用户添加、登记房产前的准备<br/>
     * （一般是未加载首页时使用，为了执行父类的 @ModelAttribute方法，防止未初始化）
     */
    @RequestMapping("/toEnlist")
    public String toEnlist() {
        return "house/enlist";
    }

    /**
     * 用户添加、登记房产
     */
    @RequestMapping("/enlist")
    public String enlist(House house, Map<String,Object> model, HttpServletRequest req) {
        // 1、验证验证码是否正确
        if (CheckCodeController.verify(req)) {
            // 2、调用service层的方法保存房子信息
            Integer houseId = houseService.enlistHouse(house, getLoginUid(req));
            if (houseId == null) {
                echoHouse(house, model);
                req.setAttribute("msg","房产登记失败！");
            } else {
                // 3、设置成功页面所需请求域对象，并跳转至操作成功页面
                return WebUtils.afterSuccess(model,"登记房产",7,"house/myHouse/" + houseId,
                        "房屋详情页面", houseId,null);
            }
        } else {
            echoHouse(house, model);
            req.setAttribute("msg","验证码错误！");
        }
        return "house/enlist";
    }

    /**
     * 前往修改房产信息页面前的准备————判断是否能修改 以及 获取最新的房屋信息
     */
    @RequestMapping("/myHouse/toUpd/{houseId}")
    public String toUpdHouse(@PathVariable Integer houseId, Map<String,Object> model, HttpServletRequest req) {
        Integer loginUid = getLoginUid(req);
        String error = houseService.canUserReEnlist(houseId, loginUid);
        if(error != null) {
            req.setAttribute("msg", error);
            return myHouseDetails(houseId, model, req);
        } else {
            House house = houseService.getMyHouse(houseId, loginUid);
            if (house == null) {
                req.setAttribute("msg", "没有找到该房子！");
            } else {
                echoHouse(house, model);
            }
            return "house/updHouse";
        }
    }

    /**
     * 处理修改房产的请求
     */
    @PutMapping("/myHouse/{houseId}")
    public String updHouse(@PathVariable Integer houseId, House house, Map<String,Object> model,
                                 HttpServletRequest req) {
        if (houseService.reEnlistHouse(houseId, getLoginUid(req), house)) {
            // 设置成功页面所需请求域对象，并跳转至操作成功页面
            return WebUtils.afterSuccess(model,"修改房产信息",6,"house/myHouse/" + houseId,
                    "房屋详情页面", houseId,null);
        } else {
            // 修改失败则重新进行 修改前准备
            req.setAttribute("msg", "房产信息修改失败！");
            return toUpdHouse(houseId, model, req);
        }
    }

    /**
     * 删除房产前的准备
     */
    @RequestMapping("/myHouse/toDel/{houseId}")
    public String toDelHouse(@PathVariable Integer houseId, Map<String,Object> model, HttpServletRequest req) {
        if (houseService.canUserDelHouse(houseId, getLoginUid(req))) {
            req.setAttribute("houseId", houseId);
            return "house/delHouse";    //转发到房子删除签名页面
        } else {
            req.setAttribute("msg", "不能删除该房产");
            return myHouseDetails(houseId, model, req);
        }
    }

    /**
     * 处理删除房产的请求
     */
    @DeleteMapping("/myHouse/{houseId}")
    public String delHouse(@PathVariable Integer houseId, Map<String,Object> model, HttpServletRequest req) {
        // 1、验证验证码
        if (CheckCodeController.verify(req)) {
            // 2、获取请求参数：用户输入的交易密码
            String txPass = req.getParameter("txPass");
            // 3、获取本登录用户ID，并执行删除房产业务
            if (houseService.delHouseByOwner(houseId, getLoginUid(req), txPass)) {
                // 4、设置成功页面所需请求域对象，并跳转至操作成功页面
                return WebUtils.afterSuccess(model,"删除房产",5,"house/myHouses",
                        "我的登记房产列表页面", houseId,null);
            } else {
                req.setAttribute("msg", "删除该房产失败，请输入正确的交易密码！");
            }
        } else {
            req.setAttribute("msg", "验证码错误！！");
        }
        // 4、删除房产失败 或 验证码错误，重新准备
        return toDelHouse(houseId, model, req);
    }

    /**
     * 我的已购买房产列表（包含相关订单简略信息）
     */
    @RequestMapping("/myBoughtHouses")
    public String myBoughtHouses(HttpServletRequest req) {
        req.setAttribute("list", houseService.myBoughtHouseOrders(getLoginUid(req)));
        return "house/myBoughtHouses";
    }

    /**
     * 我的已购买房产（包含相关订单）详细信息
     */
    @RequestMapping("/myBoughtHouse/{houseId}")
    public String myBoughtHouse(@PathVariable Integer houseId, HttpServletRequest req) {
        Order order = houseService.myBoughtHouseOrder(getLoginUid(req), houseId);
        if (order == null) {
            req.setAttribute("msg", "本登录用户的该房产的购买订单出错!！");
        } else {
            House house = order.getHouse();
            if (house == null) {
                req.setAttribute("msg", "没有找到该房产！！");
            } else {
                req.setAttribute("house", house);
                req.setAttribute("order", order);
            }
        }
        return "house/myBoughtHouse";
    }

}