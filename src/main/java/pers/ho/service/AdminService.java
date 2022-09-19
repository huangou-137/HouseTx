package pers.ho.service;

import pers.ho.bean.Admin;

/**
 * @author 黄欧
 * @create 2021-03-13 14:53
 */
public interface AdminService {
    /**
     * 根据 管理员名 和 密码 登录
     * @return 如果返回null，说明登录失败，反之返回登录管理员的信息
     */
    Admin login(String adminName, String pass);

    /**
     * 根据 管理员ID 查询其它管理员信息（用户也可用）
     */
    Admin getOtherAdmin(Short id);

    /**
     * 根据管理员ID查询该管理员全部信息
     */
    Admin getAdmin(Short id);

    /**
     * 检查 管理员名是否已存在
     * @return 返回true表示管理员名已存在，返回false表示管理员名不存在（即可用）
     */
    boolean existsAdminName(String adminName);

    /**
     * 根据管理员ID更新此管理员的信息
     * @param admin  待更新的管理员信息（其中id属性值被忽略）
     * @return 更新成功与否
     */
    boolean updateAdmin(Admin admin, Short id);

}
