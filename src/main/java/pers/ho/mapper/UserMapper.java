package pers.ho.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.caches.ehcache.EhcacheCache;
import pers.ho.bean.User;
import pers.ho.beancol.UserCol;

/**
 * @author 黄欧
 * @Date 2021-09-17 16:43
 */
@CacheNamespace(implementation = EhcacheCache.class)
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据UID获取该用户的某列值
     */
    @Select("SELECT ${col.name} FROM `t_user` WHERE `uid` = #{uid}")
    String getColValByUid(@Param("uid") Integer uid, @Param("col") UserCol col);

}