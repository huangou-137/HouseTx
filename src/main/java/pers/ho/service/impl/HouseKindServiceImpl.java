package pers.ho.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ho.bean.HouseKind;
import pers.ho.mapper.HouseKindMapper;
import pers.ho.service.HouseKindService;

import java.util.List;
import java.util.Map;

/**
 * @author 黄欧
 * @Date 2021-03-30 20:50
 */
@Service
@Transactional(readOnly = true)
public class HouseKindServiceImpl implements HouseKindService {
    @Autowired
    private HouseKindMapper mapper;

    @Override
    public List<HouseKind> getHouseKinds() {
        return mapper.getHouseKinds();
    }

    @Override
    public Map<Byte, HouseKind> getHouseKindMap() {
        return mapper.getHouseKindMap();
    }
}
