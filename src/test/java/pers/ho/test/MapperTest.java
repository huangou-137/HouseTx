package pers.ho.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import pers.ho.bean.House;
import pers.ho.bean.Order;
import pers.ho.beancol.HouseCol;
import pers.ho.utils.CommonUtils;

/**
 * @author 黄欧
 * @create 2021-09-23 16:36
 */
public class MapperTest {

    static CommonUtils utils = new CommonUtils();

    public static void main(String[] args) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.select("null").eq(HouseCol.ID.getName(), 12).or()
                .eq(HouseCol.UID.getName(), 11).orderByAsc("Q").between("G",11,"wa");
        System.out.println("****************************");
        System.out.println(wrapper.getCustomSqlSegment());
        System.out.println(wrapper.getSqlSelect());
    }
}
