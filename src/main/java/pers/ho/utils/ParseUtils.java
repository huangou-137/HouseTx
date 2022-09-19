package pers.ho.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author 黄欧
 * @Date 2021-08-30 15:08
 */
@Slf4j
public class ParseUtils {

    private static void printErr(String typeName, Exception e, String errTip) {
        log.info("********************************");
        log.info(errTip);
        log.info("转换类型【{}】失败————{}" , typeName, e.toString());
        log.info("********************************");
    }

    /**
     * 将字符串转换成为Byte类型的数据，失败返回null
     */
    public static Byte parseByte(String byteStr) {
        try {
            return Byte.parseByte(byteStr);
        } catch (Exception e) {
            printErr("Byte", e , null);
        }
        return null;
    }

    /**
     * 将字符串转换成为Integer类型的数据，失败返回null
     */
    public static Integer parseInt(String IntStr) {
        return parseInt(IntStr, null ,null);
    }

    /**
     * 将字符串转换成为Integer类型的数据
     * @param defaultVal 转换时失败返回的默认Integer值
     * @param errTip 转换异常时的提示信息
     */
    public static Integer parseInt(String IntStr, Integer defaultVal, String errTip) {
        try {
            return Integer.parseInt(IntStr);
        } catch (Exception e) {
            printErr("Integer", e, errTip);
        }
        return defaultVal;
    }

    /**
     * 从<b>req</b>请求中获取名为<b>paramName</b>的参数的值，并将其转换成为Integer类型
     * @param defaultVal 转换时失败返回的默认Integer值
     */
    public static Integer parseInt(HttpServletRequest req, String paramName, Integer defaultVal) {
        return parseInt(req.getParameter(paramName), defaultVal, "请求参数【" + paramName + "】数据出错！");
    }

    /**
     * 将字符串转换成为Short类型的数据
     * @param defaultValue 转换时失败返回的默认Short值
     * @param errTip 转换异常时的提示信息
     */
    public static Short parseShort(String shortStr, Short defaultValue, String errTip) {
        try {
            return Short.parseShort(shortStr);
        } catch (Exception e) {
            printErr("Short", e, errTip);
        }
        return defaultValue;
    }

    /**
     * 将字符串转换成为Double类型的数据
     * @param defaultValue 转换时失败返回的默认Double值
     * @param errTip 转换异常时的提示信息
     */
    public static Double parseDouble(String doubleStr, Double defaultValue, String errTip) {
        try {
            return Double.parseDouble(doubleStr);
        } catch (Exception e) {
            printErr("Double", e, errTip);
        }
        return defaultValue;
    }

    /**
     * 将LocalDateTime类字符串转换成Timestamp类型的数据
     * @param defaultValue 转换时失败返回的默认Timestamp值
     * @param errTip 转换异常时的提示信息
     */
    public static Timestamp parseTimestamp(String str, Timestamp defaultValue, String  errTip) {
        try {
            return Timestamp.valueOf(LocalDateTime.parse(str));
        } catch (Exception e) {
            printErr("Timestamp", e, errTip);
        }
        return defaultValue;
    }

    /**
     * 从<b>req</b>请求中获取名为<b>paramName</b>的参数的值，并将其转换成为Timestamp类型
     * @param defaultVal 转换时失败返回的默认Integer值
     */
    public static Timestamp parseTimestamp(HttpServletRequest req, String paramName, Timestamp defaultVal) {
        return parseTimestamp(req.getParameter(paramName), defaultVal, "请求参数【" + paramName + "】数据出错！");
    }
}
