package pers.ho.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ho.bean.House;
import pers.ho.bean.House.HouseState;
import pers.ho.beancol.HouseCol;
import pers.ho.mapper.HouseMapper;
import pers.ho.service.HouseManagerService;
import pers.ho.utils.CommonUtils;

import java.util.List;

/**
 * @author 黄欧
 * @Date 2021-04-12 13:30
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class HouseManagerServiceImpl implements HouseManagerService {
    @Autowired
    protected HouseMapper houseMapper;
    
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
        wrapper.select(colNames);
        return wrapper;
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageInfo<House> pageBriefHouses(int pageNum, int pageSize) {
        QueryWrapper<House> wrapper = pageInit(pageNum, pageSize);
        List<House> list = houseMapper.selectList(wrapper);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<House> pageBriefHouses(int pageNum, int pageSize, Byte state) {
        if (House.HouseState.isWrongState(state)) {
            // 状态为空 或 越界，默认为所有状态下
            return pageBriefHouses(pageNum, pageSize);
        }
        QueryWrapper<House> wrapper = pageInit(pageNum, pageSize);
        wrapper.eq(HouseCol.STATE.getName(), state);
        List<House> list = houseMapper.selectList(wrapper);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(readOnly = true)
    public House getHouse(Integer id) {
        return houseMapper.selectById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canAudit(Integer houseId) {
        String state = houseMapper.getColValById(houseId, HouseCol.STATE);
        return state.equals(HouseState.DEFAULT.toString());
    }

    @Override
    public boolean auditHouse(Integer houseId, boolean adopt) {
        if (canAudit(houseId)) {
            Byte stateVal = adopt? HouseState.NORMAL.getVal() : HouseState.FAILED.getVal();
            return houseMapper.updColById(houseId, HouseCol.STATE, stateVal);
        }
        return false;
    }

    @Override
    public Integer addHouse(House house) {
        if (house.getTime() == null) {
            house.setTime(CommonUtils.currentTimestamp());
        }
        return houseMapper.insert(house) > 0 ? house.getId() : null;
    }

    @Override
    public boolean updateHouse(House house, Integer id) {
        house.setId(id);
        return houseMapper.updateById(house) > 0;
    }

    @Override
    public boolean delHouse(Integer id) {
        return houseMapper.deleteById(id) > 0;
    }

}
