package pers.ho.service;

import pers.ho.bean.HouseKind;

import java.util.List;
import java.util.Map;

/**
 * @author 黄欧
 * @create 2021-03-30 16:18
 */
public interface HouseKindService {

    /**
     * @return 所有房子类别的List集合
     */
    List<HouseKind> getHouseKinds();

    /**
     * @return 保存所有房子类别的HashMap
     */
    Map<Byte,HouseKind> getHouseKindMap();
}
