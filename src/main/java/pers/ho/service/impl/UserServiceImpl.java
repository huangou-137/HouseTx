package pers.ho.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ho.beancol.UserCol;
import pers.ho.mapper.UserMapper;
import pers.ho.bean.User;
import pers.ho.service.UserService;

/**
 * @author 黄欧
 * @Date 2021-03-09 23:47
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String loginPass) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select(User.class, info -> !(info.getColumn().equals(UserCol.LOGIN_PASS.getName()) ||
                info.getColumn().equals(UserCol.TX_PASS.getName())))   //不查询登录密码和交易密码
                .eq(UserCol.USERNAME.getName(), username).eq(UserCol.LOGIN_PASS.getName(), loginPass);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public boolean existsUsername(String username) {
        return userMapper.selectCount(new QueryWrapper<User>().eq(UserCol.USERNAME.getName(), username)) > 0;
    }

    @Override
    public User getUser(Integer uid) {
        return userMapper.selectById(uid);
    }

    @Override
    public User getOtherUser(Integer uid) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select(User.class, info -> !(info.getColumn().equals(UserCol.LOGIN_PASS.getName()) ||
                info.getColumn().equals(UserCol.TX_PASS.getName())))   //不查询登录密码和交易密码
                .eq(UserCol.UID.getName(), uid);
        return userMapper.selectOne(wrapper);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public boolean updateUser(User user, Integer uid) {
        user.setUid(uid);
        return userMapper.updateById(user) > 0;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public Integer registUser(User user) {
        return userMapper.insert(user) > 0 ? user.getUid() : null;
    }

}