package pers.ho.service;

import com.github.pagehelper.PageInfo;
import pers.ho.bean.User;

/**
 * @author 黄欧
 * @create 2021-03-14 13:29
 * 用户管理业务
 */
public interface UserManagerService {

    /**
     * 分页查询所有用户的简略信息
     * @param pageNum 当前页码
     * @param pageSize 分页查询个数
     */
    PageInfo<User> pageUsers(int pageNum, int pageSize);

    /**
     * 根据用户ID查询该用户的全部信息
     */
    User getUser(Integer uid);

    /**
     * 添加用户
     * @return 返回用户uid，null代表添加失败
     */
    Integer addUser(User user);

    /**
     * 根据用户ID更新此用户的信息
     * 手机号、email、简介可以为空替代原本值，其它字段为空则这些字段不作更新
     */
    boolean updateUser(User user, Integer uid);

    /**
     * 根据用户ID删除此用户
     */
    boolean delUser(Integer uid);

}