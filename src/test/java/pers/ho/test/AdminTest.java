package pers.ho.test;

import org.junit.Test;
import pers.ho.bean.Admin;
import pers.ho.service.AdminService;
import pers.ho.utils.CommonUtils;

/**
 * @author 黄欧
 * @Date 2021/11/18 0:19
 */
public class AdminTest {
    static AdminService service = CommonUtils.getBean(AdminService.class);

    @Test
    public void testLogin(){
        Admin admin = service.login("管理员001", "gpass1234");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>");
        System.out.println(admin);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>");
    }

}
