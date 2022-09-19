package pers.ho.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ho.bean.HouseType;
import pers.ho.mapper.HouseTypeMapper;
import pers.ho.service.HouseTypeService;

import java.util.List;
import java.util.Map;

/**
 * @author 黄欧
 * @Date 2021-03-30 21:07
 */
@Service
@Transactional(readOnly = true)
public class HouseTypeServiceImpl implements HouseTypeService {
    @Autowired
    private HouseTypeMapper mapper;

    @Override
    public List<HouseType> getHouseTypes() {
        return mapper.getHouseTypes();
    }

    @Override
    public Map<Byte, HouseType> getHouseTypeMap() {
        return mapper.getHouseTypeMap();
    }
}
