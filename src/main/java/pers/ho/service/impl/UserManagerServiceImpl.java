package pers.ho.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ho.bean.User;
import pers.ho.beancol.UserCol;
import pers.ho.mapper.UserMapper;
import pers.ho.service.UserManagerService;

/**
 * @author 黄欧
 * @Date 2021-03-09 23:47
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserManagerServiceImpl implements UserManagerService {
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public PageInfo<User> pageUsers(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 简略信息：用户ID，用户名，登录密码，手机号，电子邮箱，交易密码
        wrapper.select(UserCol.UID.getName(), UserCol.USERNAME.getName(), UserCol.LOGIN_PASS.getName(),
                UserCol.PHONE.getName(), UserCol.EMAIL.getName(), UserCol.TX_PASS.getName());
        return new PageInfo<>(userMapper.selectList(wrapper));
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Integer uid) {
        return userMapper.selectById(uid);
    }

    @Override
    public Integer addUser(User user) {
        return userMapper.insert(user) > 0 ? user.getUid() : null;
    }

    @Override
    public boolean updateUser(User user, Integer uid) {
        user.setUid(uid);
        return userMapper.updateById(user) > 0;
    }

    @Override
    public boolean delUser(Integer uid) {
        return userMapper.deleteById(uid) > 0;
    }

}