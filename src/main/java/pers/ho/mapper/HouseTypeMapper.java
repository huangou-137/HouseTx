package pers.ho.mapper;

import org.apache.ibatis.annotations.MapKey;
import pers.ho.bean.HouseType;

import java.util.List;
import java.util.Map;

/**
 * @author 黄欧
 * @Date 2021-09-17 16:25
 */
public interface HouseTypeMapper {

    /**
     * @return 所有的房子户型的List集合
     */
    List<HouseType> getHouseTypes();

    /**
     * @return 所有的房子户型的Map集合
     */
    @MapKey("id")
    Map<Byte, HouseType> getHouseTypeMap();

}