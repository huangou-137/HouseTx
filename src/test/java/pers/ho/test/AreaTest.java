package pers.ho.test;

import org.junit.Test;
import pers.ho.bean.Area;

/**
 * @author 黄欧
 * @Date 2021/11/23 22:01
 */
public class AreaTest {

    @Test
    public void test(){
        Area area = new Area();
        area.setWholeName("hello");
        area.setType((byte) 2);
        area.setName("京都市");
        System.out.println(area);
    }

}
