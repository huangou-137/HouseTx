package pers.ho.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mybatis.caches.ehcache.EhcacheCache;
import pers.ho.bean.House;
import pers.ho.beancol.HouseCol;

/**
 * @author 黄欧
 * @Date 2021-09-16 23:24
 */
@CacheNamespace(implementation = EhcacheCache.class)
public interface HouseMapper extends BaseMapper<House> {

    /**
     * 根据ID获取该房产的某列值
     */
    @Select("SELECT ${col.name} FROM `t_house` WHERE `id` = #{id}")
    String getColValById(@Param("id") Integer id, @Param("col") HouseCol col);

    /**
     * 根据ID修改房子的某个列值
     */
    @Update("UPDATE `t_house` SET ${col.name} = #{val} WHERE `id` = #{id}")
    boolean updColById(@Param("id") Integer id, @Param("col") HouseCol col, @Param("val") Object val);

}
