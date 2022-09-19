package pers.ho.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ho.bean.House;
import pers.ho.beancol.HouseCol;
import pers.ho.mapper.HouseMapper;
import pers.ho.service.ClientHouseService;

import java.util.List;

/**
 * @author 黄欧
 * @Date 2021-04-12 13:14
 */
@Service
@Transactional(readOnly = true)
public class ClientHouseServiceImpl implements ClientHouseService {
    @Autowired
    protected HouseMapper houseMapper;

    @Override
    public House getHouse(Integer id) {
        House house = houseMapper.selectById(id);
        if (house != null && House.HouseState.NORMAL.equalsVal(house.getState())) {
            return house;
        }
        return null;
    }

    /**
     * 开启分页查询 以及 创建 House对象封装操作类的对象并初始化
     * @return 新 QueryWrapper&lt;House>对象
     */
    private QueryWrapper<House> pageInit(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<House> wrapper = new QueryWrapper<>();
        // 要查询的房子简略信息列有：
        String[] colNames = HouseCol.getNames(HouseCol.ID, HouseCol.AREA_ID, HouseCol.ADDRESS,
                HouseCol.KIND_ID, HouseCol.TYPE_ID, HouseCol.SIZE, HouseCol.PRICE);
        wrapper.select(colNames).eq(HouseCol.STATE.getName(), House.HouseState.NORMAL.getVal());
        return wrapper;
    }

    @Override
    public PageInfo<House> pageBriefHouses(int pageNum, int pageSize) {
        QueryWrapper<House> wrapper = pageInit(pageNum, pageSize);
        List<House> list = houseMapper.selectList(wrapper);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<House> pageBriefHousesByPrice(Double min, Double max, int pageNum, int pageSize) {
        if (min <= 0 && max == Double.MAX_VALUE) {
            return pageBriefHouses(pageNum, pageSize);
        }
        QueryWrapper<House> wrapper = pageInit(pageNum, pageSize);
        wrapper.between(HouseCol.PRICE.getName(), min, max)
                .orderByAsc(HouseCol.PRICE.getName());
        List<House> list = houseMapper.selectList(wrapper);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<House> pageBriefHousesBySize(Short min, Short max, int pageNum, int pageSize) {
        if (min <= 0 && max == Short.MAX_VALUE) {
            return  pageBriefHouses(pageNum, pageSize);
        }
        QueryWrapper<House> wrapper = pageInit(pageNum, pageSize);
        wrapper.between(HouseCol.SIZE.getName(), min, max)
                .orderByAsc(HouseCol.SIZE.getName());
        List<House> list = houseMapper.selectList(wrapper);
        return new PageInfo<>(list);
    }

}