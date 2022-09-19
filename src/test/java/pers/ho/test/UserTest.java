package pers.ho.test;

import com.github.pagehelper.PageInfo;
import org.junit.Test;
import pers.ho.bean.User;
import pers.ho.beancol.UserCol;
import pers.ho.mapper.UserMapper;
import pers.ho.service.UserManagerService;
import pers.ho.service.UserService;
import pers.ho.utils.CommonUtils;

public class UserTest {

    static UserMapper mapper = CommonUtils.getBean(UserMapper.class);
    static UserService service = CommonUtils.getBean(UserService.class);
//    static UserManagerService service = CommonUtils.getBean(UserManagerService.class);

    public static void main(String[] args) {
        testGet();
    }

    public static void testGet() {
        User user1 = service.getUser(11);
        User user3 = service.getUser(11);
        User user2 = service.getOtherUser(11);
        System.out.println("\n\n\n#################");
        System.out.println(user1);
        System.out.println("\n#################");
        System.out.println(user2);
        System.out.println("\n#################");
        System.out.println(user3);
    }

//    public static void  testGets(){
//        PageInfo<User> pageInfo = service.pageUsers(1,5);
//        pageInfo.getList().forEach(System.out::println);
//    }

    @Test
    public void testUpd(){
        User user = mapper.selectById(17);
        user.setEmail("ho1919@house2.com");
        System.out.println(mapper.updateById(user));
     }

     @Test
     public void testGet2(){
         System.out.println(mapper.getColValByUid(11, UserCol.LOGIN_PASS));
         System.out.println(mapper.getColValByUid(11, UserCol.TX_PASS));
         System.out.println(mapper.getColValByUid(11, UserCol.USERNAME));
      }

}
