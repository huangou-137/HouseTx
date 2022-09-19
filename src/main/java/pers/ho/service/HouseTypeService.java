package pers.ho.service;

import pers.ho.bean.HouseType;

import java.util.List;
import java.util.Map;

/**
 * @author 黄欧
 * @create 2021-03-30 16:18
 */
public interface HouseTypeService {

    /**
     * @return 所有的房子户型的List集合
     */
    List<HouseType> getHouseTypes();

    /**
     * @return 保存所有房子户型的HashMap
     */
    Map<Byte,HouseType> getHouseTypeMap();
}
