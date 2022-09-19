package pers.ho.service;

import pers.ho.bean.User;

/**
 * @author 黄欧
 * @Date 2021-03-09 23:25
 */
public interface UserService {

    /**
     * 根据 用户名和登录密码 进行用户登录
     * @return 如果返回null，说明登录失败，反之返回登录用户的信息
     */
    User login(String username, String loginPass);

    /**
     * 检查 用户名是否已存在
     * @return 返回true表示用户名已存在，返回false表示用户名不存在（即可用）
     */
    boolean existsUsername(String username);

    /**
     * 根据用户ID查询该用户的全部信息
     */
    User getUser(Integer uid);

    /**
     * 根据用户ID查询其它用户可公开信息（密码除外）
     */
    User getOtherUser(Integer uid);

    /**
     * 根据用户ID更新此用户的信息
     * 手机号、email、简介可以为空替代原本值，其它字段为空则这些字段不作更新
     */
    boolean updateUser(User user, Integer uid);

    /**
     * 注册用户
     * @return 返回用户uid，null代表注册失败
     */
    Integer registUser(User user);

}
