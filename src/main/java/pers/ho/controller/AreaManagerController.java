package pers.ho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.ho.bean.Area;
import pers.ho.service.AreaService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author 黄欧
 * @create 2021-08-10 11:24
 * 地区管理
 */
@Controller
@RequestMapping("/manager")
public class AreaManagerController{
    @Autowired
    private AreaService areaService;

    /**
     * 生成areaJson前的准备
     */
    @RequestMapping("/beforeGenJson")
    public String beforeGenJson(HttpServletRequest req) {
        // 1、获取全部地区
        List<Area> areaList = areaService.getAreas();
        if (areaList == null) {
            return "error/error500";
        }
        // 将list转换成HashMap，其中Key值为Area对象的ID字段
        HashMap<Short,Area> areaMap = new HashMap<>(4600);
        for (Area area : areaList) {
            areaMap.put(area.getId(), area);
        }

        // ————2、获取省市区选择时需要的地区maps————
        // 保存省级地区的序号（key与areaMap中一致）
        HashMap<Short,Byte> provinceMap = new HashMap<>(48);
        // 保存地级地区在对应省中的序号（key与areaMap中一致）
        HashMap<Short,Byte> cityMap = new HashMap<>(512);
        // 省数、各省的地级地区数量（索引对应序号）
        byte provinceNum = 0;
        byte[] cityNum = new byte[40];
        for (Area area: areaList) {
            if (Area.PROVINCE_TYPE.equals(area.getType())) {
                provinceMap.put(area.getId(),provinceNum);
                cityNum[provinceNum] = 0;
                provinceNum++;
            } else if (Area.CITY_TYPE.equals(area.getType())) {
                byte pIdx =  provinceMap.get(area.getPid());
                cityMap.put(area.getId(),cityNum[pIdx]);
                cityNum[pIdx]++;
            }
        }

        // 3、保存信息至Request域中
        req.setAttribute("areaMap",areaMap);
        req.setAttribute("provinceMap",provinceMap);
        req.setAttribute("cityMap",cityMap);
        // 4、请求转发到生成areaJson页面
        return "manager/area/genAreaJson";
    }

}