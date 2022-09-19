package pers.ho.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import pers.ho.bean.Area;
import pers.ho.bean.House;
import pers.ho.bean.HouseKind;
import pers.ho.bean.HouseType;
import pers.ho.service.AreaService;
import pers.ho.service.HouseKindService;
import pers.ho.service.HouseTypeService;
import pers.ho.utils.CommonUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 黄欧
 * @create 2021-08-24 19:08
 * 对房屋信息请求Servlet的基本实现类
 * 即使不标注 @Controller，其子类也会执行其 @ModelAttribute的方法
 * 执行顺序：先执行子类的@ModelAttribute方法，再执行父类的
 */
public class BaseHouseController {

    private Map<Short,Area> areaMap = null;

    /**
     * 用以初始化房屋类别、房屋户型、地区信息等这些不易变的信息，并将它们保存至应用作用域中
     */
    @ModelAttribute
    public void init(HttpServletRequest req) {
        // 1、获取应用上下文，并判断不易变信息是否已经初始化
        ServletContext context = req.getServletContext();
        Boolean isInitHouse = (Boolean) context.getAttribute("isInitHouse");
        if (isInitHouse != null && isInitHouse.equals(true)) {
            // 已经初始化过一次
            if (areaMap == null) {
                areaMap = (Map<Short, Area>) context.getAttribute("areaMap");
            }
            return;
        }

        // ————————没有初始化则进行以下步骤————————

        // 2、获取工厂类对象和Service层类对象
        HouseKindService houseKindService = CommonUtils.getBean(HouseKindService.class);
        HouseTypeService houseTypeService = CommonUtils.getBean(HouseTypeService.class);
        AreaService areaService = CommonUtils.getBean(AreaService.class);

        // 3、获取各相关Map集合
        Map<Byte,HouseKind> houseKindMap = houseKindService.getHouseKindMap();
        Map<Byte,HouseType> houseTypeMap = houseTypeService.getHouseTypeMap();
        areaMap = areaService.getAreaMap();

        // 4、把不易变信息保存至应用作用域中
        context.setAttribute("houseKindMap", houseKindMap);
        context.setAttribute("houseTypeMap", houseTypeMap);
        context.setAttribute("areaMap", areaMap);
        context.setAttribute("isInitHouse", true);
    }

    /**
     * 通过地区ID从应用作用域的areaMap中获取<u>省市县ID</u>，并保存至<b>Model</b>中
     */
    protected void echoArea(Short areaId, Map<String, Object> model) {
        if (areaMap == null) {
            return;
        }

        Short provinceId = -1;
        Short cityId = -1;
        Short countyId = -1;
        Area area = areaMap.get(areaId);
        Byte areaType = area.getType();
        if (Area.PROVINCE_TYPE.equals(areaType)) {
            // 省级
            provinceId = area.getId();
        } else if (Area.CITY_TYPE.equals(areaType)) {
            // 市级
            cityId = area.getId();
            provinceId = area.getPid();
        } else if (Area.COUNTY_TYPE.equals(areaType)) {
            // 县、区级
            countyId = area.getId();
            cityId = area.getPid();
            provinceId = areaMap.get(cityId).getPid();
        }

        model.put("provinceId", provinceId);
        model.put("cityId", cityId);
        model.put("countyId", countyId);
    }

    /**
     * 把房屋实体及相关信息（通过地区ID转换后的<u>省市县ID</u>）保存至<b>Model</b>中
     */
    protected void echoHouse(House house, Map<String, Object> model) {
        model.put("house", house);
        echoArea(house.getAreaId(), model);
    }

}