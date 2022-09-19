package pers.ho.service;

import pers.ho.bean.Area;

import java.util.List;
import java.util.Map;

/**
 * @author 黄欧
 * @create 2021-03-30 21:17
 */
public interface AreaService {
    /**
     * 根据编号获取区域信息
     */
    Area getAreaById(Short id);

    /**
     * @return 所有区域信息（含完整名称）的list集合
     */
    List<Area> getAreas();

    /**
     * @return 所有区域信息的Map集合
     */
    Map<Short, Area> getAreaMap();

}
