package pers.ho.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pers.ho.bean.House;
import pers.ho.service.HouseManagerService;
import pers.ho.utils.ParseUtils;
import pers.ho.utils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 黄欧
 * @create 2021-03-31 22:03
 */
@Controller
@RequestMapping("/manager")
public class HouseManagerController extends BaseHouseController {
    @Autowired
    private HouseManagerService houseManagerService;

    /**
     * 获取待确审核的房屋列表（相当于房屋状态值为默认状态，审核中……）
     */
    @RequestMapping("/houses/auditing")
    public String page_audit(HttpServletRequest req, Map<String, Object> model) {
        return page(req, model, House.HouseState.DEFAULT.getVal());
    }

    /**
     * 根据状态等获取房产列表（分页）,可选请求参数：页码pageNum、显示条数pageSize
     */
    @RequestMapping("/houses")
    public String page(HttpServletRequest req, Map<String, Object> model,
                       @RequestParam(defaultValue = "-128") Byte state) {
        int pageNum = ParseUtils.parseInt(req,"pageNum",1);
        int pageSize = ParseUtils.parseInt(req,"pageSize", 10);
        PageInfo<House> pageInfo = houseManagerService.pageBriefHouses(pageNum, pageSize, state);
        if (House.HouseState.isWrongState(state)) {
            model.put("pageUrl", "manager/houses?");
        } else {
            req.setAttribute("state", state);
            model.put("pageUrl", "manager/houses?state=" + state);
        }
        model.put("pageInfo",pageInfo);
        return "manager/house/page";
    }

    /**
     * 获取房产的详细信息
     */
    @GetMapping("/house/{houseId}")
    public String houseDetails(@PathVariable Integer houseId, Map<String, Object> model) {
        House house = houseManagerService.getHouse(houseId);
        if (house == null) {
            model.put("msg", "没有找到该房产！");
        } else {
            model.put("house", house);
            model.put("canAudit",houseManagerService.canAudit(houseId));
        }
        return "manager/house/details";
    }

    /**
     * 处理审核房产的请求，需要请求参数：审核选择———通过与否
     */
    @RequestMapping("/house/audit/{houseId}")
    public String  audit(@PathVariable Integer houseId, Map<String, Object> model,
                         @RequestParam(defaultValue = "false") Boolean adopt) {
        if (houseManagerService.auditHouse(houseId, adopt)) {
            return WebUtils.afterSuccess(model, "审核房产", 5, "manager/house/" + houseId,
                    "房产详情页面", houseId, null);
        } else {
            model.put("msg","审核房产失败！");
            // 重新返回房产详情页面
            return houseDetails(houseId, model);
        }
    }

    /**
     * 处理删除房产的请求
     */
    @DeleteMapping("/house/{houseId}")
    public String del(@PathVariable Integer houseId, Map<String, Object> model) {
        if (houseManagerService.delHouse(houseId)) {
            return WebUtils.afterSuccess(model, "删除房产", 5, "manager/houses",
                    "房产列表页面", houseId, null);
        } else {
            // 删除失败，重新获取房产详情
            model.put("msg","删除房产失败!");
            return houseDetails(houseId, model);
        }
    }

    /**
     * 修改房产前的准备————获取房产最新信息
     */
    @RequestMapping("/house/toUpd/{houseId}")
    public String toUpd(@PathVariable Integer houseId, Map<String, Object> model) {
        House house = houseManagerService.getHouse(houseId);
        if (house == null) {
            model.put("msg", "没有找到该房产！");
        } else {
            echoHouse(house, model);
        }
        return "manager/house/upd";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        // 忽略绑定实体类中某些字段属性，@RequestParam以及HttpServletRequest还可以取出
        webDataBinder.setDisallowedFields("time");
    }

    /**
     * 处理修改房产的请求
     */
    @PutMapping("/house/{houseId}")
    public String upd(House house, HttpServletRequest req, @PathVariable Integer houseId,
                      Map<String, Object> model) {
        house.setTime(ParseUtils.parseTimestamp(req,"time",null));
        if (houseManagerService.updateHouse(house, houseId)) {
            return WebUtils.afterSuccess(model, "修改房产", 5, "manager/house/" + houseId,
                    "房产详情页面", houseId, null);
        } else {
            // 修改失败则重新进行 修改前准备
            req.setAttribute("msg","修改房产信息失败！");
            return toUpd(houseId, model);
        }
    }

    /**
     * 处理添加房产的请求
     */
    @PostMapping("/house")
    public String add(House house, HttpServletRequest req, Map<String, Object> model) {
        house.setTime(ParseUtils.parseTimestamp(req,"time",null));
        Integer houseId = houseManagerService.addHouse(house);
        if (houseId != null) {
            return WebUtils.afterSuccess(model, "添加房产", 7, "manager/house/" + houseId,
                    "房产详情页面", null, "房产编号为：" + houseId);
        } else {
            // 添加失败则把回显信息保存到Request域中，并返回添加页面
            echoHouse(house, model);
            req.setAttribute("msg", "添加房产失败！");
            return "manager/house/add";
        }
    }

}
