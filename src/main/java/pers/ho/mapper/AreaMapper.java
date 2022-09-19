package pers.ho.mapper;

import pers.ho.bean.Area;

import java.util.List;

/**
 * @author 黄欧
 * @Date 2021-09-16 15:59
 */

public interface AreaMapper {
    /**
     * 根据编号查询区域信息
     */
    Area getAreaById(Short id);

    /**
     * @return 所有区域信息
     */
    List<Area> getAreas();
}
