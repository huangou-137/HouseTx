package pers.ho.mapper;

import org.apache.ibatis.annotations.MapKey;
import pers.ho.bean.HouseKind;

import java.util.List;
import java.util.Map;

/**
 * @author 黄欧
 * @Date 2021-09-17 15:20
 */
public interface HouseKindMapper {

    /**
     * @return 所有房子类别的List集合
     */
    List<HouseKind> getHouseKinds();
    /**
     * @return 所有房子类别的Map集合
     */
    @MapKey("id")
    Map<Byte, HouseKind> getHouseKindMap();

}