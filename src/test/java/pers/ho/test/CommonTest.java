package pers.ho.test;

import pers.ho.utils.ParseUtils;

/**
 * @author 黄欧
 * @create 2021-03-24 17:32
 * 通用测试
 */
public class CommonTest {
    public static void main(String[] args) {
        String str = "19999999";
        System.out.println(ParseUtils.parseShort(str, (short)-106, "操，又出错了！！！"));
    }

}