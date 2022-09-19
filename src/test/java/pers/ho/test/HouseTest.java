package pers.ho.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import pers.ho.bean.House;
import pers.ho.beancol.HouseCol;
import pers.ho.mapper.HouseMapper;
import pers.ho.service.ClientHouseService;
import pers.ho.service.HouseManagerService;
import pers.ho.service.HouseService;
import pers.ho.utils.CommonUtils;

public class HouseTest {
    static HouseMapper mapper = CommonUtils.getBean(HouseMapper.class);
    static HouseService houseService = CommonUtils.getBean(HouseService.class);
    static ClientHouseService clientService = CommonUtils.getBean(ClientHouseService.class);
    static HouseManagerService managerService = CommonUtils.getBean(HouseManagerService.class);

    public static void main(String[] args) {
        Integer[] houseIds = {1,13,null,5,1,13,1};
        for (Integer houseId:houseIds) {
            House house = getHouse(houseId);
            System.out.println("\n########################################################################");
            System.out.println(house);
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");
        }
    }

    @Test
    public void listTest(){
//        System.out.println(clientService.getHouse(14));

        houseService.myBoughtHouseOrders(14).forEach(System.out::println);
    }

    @Test
    public void testMapperSelect(){
//        mapper.selectMaps(null).forEach(System.out::println);
        House house = new House();
        house.setUid(9);
        mapper.selectList(new QueryWrapper<>(house)).forEach(System.out::println);
     }

    private static House getHouse(Integer houseId) {
        return managerService.getHouse(houseId);
    }

    private static void addTest(){
        House house;
//        Integer id = 1001;
//        Integer uid = 3;
//        Short areaId = (short)1127;
//        Byte kindId = 3;
//        Byte typeId = 5;
//        Short size = (short)88;
//        Double price = 886777.5;
//        String desc = "环境优美，适宜居住………………";
//        Byte state = House.DEFAULT_STATE;
//        Timestamp addTime = new Timestamp( System.currentTimeMillis() - 887779999L);
//        String ad1 = CommonUtils.getRandChineseString(2) + "街道";
//        String ad2 = CommonUtils.getRandChineseString(3) + "小区";
//        String ad3 = "5栋452房";
//        String address = ad1 + ad2 + ad3;
//
//        house = new House(id,uid,areaId,address,kindId,typeId,size,price,desc,state,addTime);
//        System.out.println(houseMapper.addHouse(house));
    }

    @Test
    public void testUpdCol(){
        System.out.println(getHouse(11));
        mapper.updColById(11, HouseCol.SIZE, 266);
        System.out.println(getHouse(11));
    }

}
