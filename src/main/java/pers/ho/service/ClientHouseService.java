package pers.ho.service;

import com.github.pagehelper.PageInfo;
import pers.ho.bean.House;

/**
 * @author 黄欧
 * @create 2021-04-12 0:27
 * 负责处理未登录和已登录皆有的房屋信息业务
 */
public interface ClientHouseService {

    /**
     * 根据编号查询待售中房子全部详细信息
     * @return 如果返回null,说明该房子不存在 或 该房产非待售中状态。
     */
    House getHouse(Integer id);

    /**
     * 分页查询待售中状态下的房子的简略信息
     * @param pageNum 当前页码
     * @param pageSize 分页查询个数
     */
    PageInfo<House> pageBriefHouses(int pageNum, int pageSize);

    /**
     * 分页查询预期价格在[min~max](元)之间、状态为待售中的房子简略信息【按价格从小到大排序】
     * @param pageNum 当前页码
     * @param pageSize 分页查询个数
     */
    PageInfo<House> pageBriefHousesByPrice(Double min, Double max, int pageNum, int pageSize);

    /**
     * 分页查询面积在[min~max](平方米)之间、状态为待售中的房子简略信息【按面积从小到大排序】
     * @param pageNum 当前页码
     * @param pageSize 分页查询个数
     */
    PageInfo<House> pageBriefHousesBySize(Short min, Short max, int pageNum, int pageSize);

}