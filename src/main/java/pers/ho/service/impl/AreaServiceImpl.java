package pers.ho.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ho.mapper.AreaMapper;
import pers.ho.bean.Area;
import pers.ho.service.AreaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 黄欧
 * @Date 2021-03-30 21:18
 */
@Service
@Transactional(readOnly = true)
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaMapper areaMapper;

    @Override
    public Area getAreaById(Short id) {
        return areaMapper.getAreaById(id);
    }

    @Override
    public List<Area> getAreas() {
        List<Area> list = areaMapper.getAreas();
        if (list == null) {
            return null;
        }

        //保存省级完整名称的HashMap
        HashMap<Short,String> map1 = new HashMap<>(48);
        //保存地级完整名称的HashMap
        HashMap<Short,String> map2 = new HashMap<>(512);

        for (Area area: list) {
            String wholeName = area.getName();
            Byte type = area.getType();
            if (Area.COUNTY_TYPE.equals(type)) {
                wholeName = map2.getOrDefault(area.getPid(), " ") + "：" + wholeName;
            } else if (Area.CITY_TYPE.equals(type)) {
                wholeName = map1.getOrDefault(area.getPid(), " ") + "：" + wholeName;
                map2.put(area.getId(), wholeName);
            } else if (Area.PROVINCE_TYPE.equals(type)) {
                //省级（wholeName与name一致）
                map1.put(area.getId(), wholeName);
            }
            area.setWholeName(wholeName);
        }
        return list;
    }

    @Override
    public Map<Short, Area> getAreaMap() {
        List<Area> list = areaMapper.getAreas();
        if (list == null) {
            return null;
        }
        Map<Short,Area> map = new HashMap<>(4600);

        Area defaultArea = new Area();
        defaultArea.setWholeName(" ");
        for (Area area: list) {
            String wholeName = area.getName();
            Byte type = area.getType();
            if (Area.COUNTY_TYPE.equals(type) || Area.CITY_TYPE.equals(type)) {
                //县级、地级
                wholeName = map.getOrDefault(area.getPid(), defaultArea).getWholeName() + "：" + wholeName;
            }
            //省级（wholeName与name一致）
            area.setWholeName(wholeName);
            map.put(area.getId(), area);
        }
        return map;
    }
}
