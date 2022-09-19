package pers.ho.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.caches.ehcache.EhcacheCache;
import pers.ho.bean.Order;
import pers.ho.beancol.OrderCol;

/**
 * @author 黄欧
 * @Date 2021-09-26 20:08
 */
@CacheNamespace(implementation = EhcacheCache.class)
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 根据ID获取该订单的某列值
     */
    @Select("SELECT ${col.name} FROM `t_order` WHERE `id` = #{id}")
    String getColValById(@Param("id") Integer id, @Param("col") OrderCol col);

}