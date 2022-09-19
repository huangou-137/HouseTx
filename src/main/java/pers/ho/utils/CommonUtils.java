package pers.ho.utils;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pers.ho.config.MPConfig;
import pers.ho.config.RootConfig;

import java.sql.Timestamp;

public class CommonUtils {

    private static AnnotationConfigApplicationContext ioc = new
            AnnotationConfigApplicationContext(RootConfig.class, MPConfig.class);

    /**
     * 从ioc容器中获取组件实例对象
     */
    public static <T> T getBean(Class<T> tClass) {
        return ioc.getBean(tClass);
    }

    /**
     * @return 由系统当前时间转换成的时间戳
     */
    public static Timestamp currentTimestamp(){
        return new Timestamp(System.currentTimeMillis());
    }

}
