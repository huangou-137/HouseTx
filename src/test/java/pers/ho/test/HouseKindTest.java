package pers.ho.test;

import pers.ho.bean.HouseKind;
import pers.ho.service.HouseKindService;
import pers.ho.utils.CommonUtils;

import java.util.List;

/**
 * @author 黄欧
 * @create 2021-10-01 21:43
 */
public class HouseKindTest {
    static HouseKindService service = CommonUtils.getBean(HouseKindService.class);

    public static void main(String[] args) {
        List<HouseKind> list = service.getHouseKinds();
        System.out.println("###################");
        list.forEach(System.out::println);
    }

}
