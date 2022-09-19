package pers.ho.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ho.beancol.AdminCol;
import pers.ho.mapper.AdminMapper;
import pers.ho.bean.Admin;
import pers.ho.service.AdminService;

/**
 * @author 黄欧
 * @Date 2021-03-23 21:28
 */
@Service
@Transactional(readOnly = true)     //除了更新管理员业务之外，其它的都是只读的
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper mapper;

    @Override
    public Admin login(String adminName, String pass) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.select(Admin.class, info -> !info.getColumn().equals(AdminCol.PASS.getName()))  //不查询密码
                .eq(AdminCol.ADMIN_NAME.getName(), adminName)
                .eq(AdminCol.PASS.getName(), pass);
        return mapper.selectOne(wrapper);
    }

    @Override
    public Admin getOtherAdmin(Short id) {
        Admin admin = mapper.selectById(id);
        if (admin != null) {
            admin.setPass(null);
        }
        return admin;
    }

    @Override
    public Admin getAdmin(Short id) {
        return mapper.selectById(id);
    }

    @Override
    public boolean existsAdminName(String adminName) {
        return mapper.selectCount(new QueryWrapper<Admin>().eq(AdminCol.ADMIN_NAME.getName(), adminName)) > 0;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public boolean updateAdmin(Admin admin, Short id) {
        admin.setId(id);
        return mapper.updateById(admin) > 0;
    }

}