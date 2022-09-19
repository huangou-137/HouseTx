package pers.ho.service;

import com.github.pagehelper.PageInfo;
import pers.ho.bean.House;

/**
 * @author 黄欧
 * @create 2021-04-12 0:30
 * 负责房屋信息管理业务（管理员）
 */
public interface HouseManagerService {

    /**
     * 分页查询所有状态房子的简略信息
     * @param pageNum 当前页码
     * @param pageSize 分页查询个数
     */
    PageInfo<House> pageBriefHouses(int pageNum, int pageSize);

    /**
     * 分页查询某个状态下的房子的简略信息
     * @param pageNum 当前页码
     * @param pageSize 分页查询个数
     * @param state 为空 或 越界 默认所有状态下
     */
    PageInfo<House> pageBriefHouses(int pageNum, int pageSize, Byte state);

    /**
     * 根据编号查询房子全部详细信息
     */
    House getHouse(Integer id);

    /**
     * @param houseId 房产编号
     * @return 是否能审核该房产
     */
    boolean canAudit(Integer houseId);

    /**
     * 审核房子，更改房子的状态为NORMAL_STATE或FAILED_STATE
     * @param houseId 房子编号
     * @param adopt 房子审核通过与否
     * @return 审核结果是否成功添加入库
     */
    boolean auditHouse(Integer houseId, boolean adopt);

    /**
     * 增加新的房子
     * @param  house 其中id和desc字段可以为空
     * @return 房产编号
     */
    Integer addHouse(House house);

    /**
     * 根据ID值修改旧的房子
     * @param  house 其中house.desc字段可以为空
     * @return 修改成功与否
     */
    boolean updateHouse(House house, Integer id);

    /**
     * 根据ID值删除房子
     * @return 删除成功与否
     */
    boolean delHouse(Integer id);

}
