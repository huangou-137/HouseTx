package pers.ho.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pers.ho.bean.House;
import pers.ho.service.ClientHouseService;
import pers.ho.utils.ParseUtils;
import pers.ho.utils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 黄欧
 * @create 2021-08-24 21:11
 * 未登录和已登录用户皆能访问
 */
@Controller
@RequestMapping("/client")
public class ClientHouseController extends BaseHouseController {
    @Autowired
    private ClientHouseService clientHouseService;

    /**
     * 分页获取待售中的房产信息列表，并请求转发到房屋信息首页
     */
    @RequestMapping("/houses")
    public String page(HttpServletRequest req, Map<String,Object> model) {
        // 1、获取请求的参数 pageNum 和 pageSize
        int pageNum = ParseUtils.parseInt(req,"pageNum", 1);
        int pageSize = ParseUtils.parseInt(req,"pageSize", 10);
        // 2、获取状态为待售中的房屋简略信息PageInfo
        PageInfo<House> pageInfo = clientHouseService.pageBriefHouses(pageNum, pageSize);
        model.put("pageInfo", pageInfo);
        model.put("pageUrl", "client/houses?");
        return "client/index";
    }

    /**
     * 根据【最低和最高价格】进行待售中房屋信息分页查询功能
     */
    @RequestMapping(value = "/houses", params = {"minPrice","maxPrice"})
    public String pageByPrice(HttpServletRequest req, Map<String,Object> model,
                              @RequestParam String minPrice, @RequestParam String maxPrice) {
        // 1、获取请求的参数 pageNum 和 pageSize 以及最低价和最高价
        int pageNum = ParseUtils.parseInt(req,"pageNum", 1);
        int pageSize = ParseUtils.parseInt(req,"pageSize", 10);
        double min = ParseUtils.parseDouble(minPrice, (double) 0,"请求参数：最低价数据出错！");
        double max = ParseUtils.parseDouble(maxPrice, Double.MAX_VALUE,"请求参数：最高价数据出错！");
        // 2、获取pageInfo对象
        PageInfo<House> pageInfo = clientHouseService.pageBriefHousesByPrice(min, max, pageNum, pageSize);
        StringBuilder pageUrl = new StringBuilder("client/houses?");
        // 2.?、如果 最低价格 或 最高价格 的参数不为空, 追加对应参数到分页条地址中
        WebUtils.addUrlParam(pageUrl, "minPrice", min);
        WebUtils.addUrlParam(pageUrl, "maxPrice", max);
        model.put("pageInfo", pageInfo);
        model.put("pageUrl", pageUrl.toString());
        return "client/index";
    }

    /**
     * 根据【最小和最大面积】进行待售中房屋信息分页查询功能
     */
    @RequestMapping(value = "/houses", params = {"minSize","maxSize"})
    public String pageBySize(HttpServletRequest req, Map<String,Object> model,
                             @RequestParam String minSize, @RequestParam String maxSize) {
        // 1、获取请求的参数 pageNo 和 pageSize 以及最小和最大面积
        int pageNum = ParseUtils.parseInt(req,"pageNum", 1);
        int pageSize = ParseUtils.parseInt(req,"pageSize", 10);
        short min = ParseUtils.parseShort(minSize, (short) 0,"请求参数：最小面积数据出错！");
        short max = ParseUtils.parseShort(maxSize, Short.MAX_VALUE,"请求参数：最大面积数据出错！");
        // 2、获取PageInfo对象
        PageInfo<House> pageInfo = clientHouseService.pageBriefHousesBySize(min, max, pageNum, pageSize);
        StringBuilder pageUrl = new StringBuilder("client/houseServlet?");
        // 2.？、如果有 最小面积 或 最大面积 的参数, 则将对应参数追加到分页条地址中
        WebUtils.addUrlParam(pageUrl, "minSize", min);
        WebUtils.addUrlParam(pageUrl, "maxSize", max);
        model.put("pageInfo", pageInfo);
        model.put("pageUrl", pageUrl.toString());
        return "client/index";
    }

    /**
     * 获取某个房屋的详细信息
     */
    @RequestMapping("/house/{houseId}")
    public String houseDetails(@PathVariable Integer houseId, Map<String,Object> model, HttpServletRequest req) {
        if (houseId == null) {
            model.put("msg", "房屋编号异常！");
        } else {
            House house = clientHouseService.getHouse(houseId);
            if (house == null) {
                model.put("msg", "没有找到该房子或其处于非待售中状态！");
            } else {
                model.put("house", house);
            }
        }
        return "client/houseDetails";
    }

}