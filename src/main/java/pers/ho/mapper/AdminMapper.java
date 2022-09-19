package pers.ho.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.mybatis.caches.ehcache.EhcacheCache;
import pers.ho.bean.Admin;

/**
 * @author 黄欧
 * @Date 2021-09-14 15:27
 */
@CacheNamespace(implementation = EhcacheCache.class)
public interface AdminMapper extends BaseMapper<Admin> {

}