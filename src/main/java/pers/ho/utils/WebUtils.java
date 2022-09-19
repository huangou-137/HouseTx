package pers.ho.utils;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WebUtils {

    /**
     * 数据检验出错后，将校验结果按对应属性保存至model中的错误Map中
     * @param pojoName 实体名称，如用户、房产……
     */
    public static void afterValidError(Errors errors, Map<String, Object> model, String pojoName) {
        System.out.println("JSR303数据校验出错！");
        List<FieldError> fieldErrors = errors.getFieldErrors();
        Map<String, String> errorMap = new HashMap<>();
        for(FieldError fieldError : fieldErrors){
            System.out.println(fieldError.getField() + " - " + fieldError.getDefaultMessage());
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        model.put("errorMap", errorMap);
        model.put("msg", "JSR303校验"+ pojoName +"数据出错!");
    }

    /**
     * 往model添加成功页面所需请求域对象，并且设置相关视图信息<br/>
     * doName（操作名称，如更新房产） ，countDownTime（倒计时/s），<br/>
     * href（自动跳转的地址），hrefDesc（地址描述）<br/>
     * 可选请求域对象有：
     * pojoId（操作实体的ID），news（操作成功其它提示信息）
     * @return 成功页面的视图名
     */
    public static String afterSuccess(Map<String, Object> model, String doName, int countDownTime,
                                            String href, String  hrefDesc, Integer pojoId, String news) {
        model.put("doName", doName);
        if (countDownTime <= 0) {
            countDownTime = 3;
        }
        model.put("countDownTime", countDownTime);
        model.put("href", href);
        model.put("hrefDesc", hrefDesc);
        if (pojoId != null) {
            model.put("pojoId", pojoId);
        }
        if (news != null) {
            model.put("news", news);
        }
        return "common/success";
    }

    /**
     * 判断字符串是否为null或空串
     */
    public static boolean nullOrEmpty(String str) {
        return str == null || "".equals(str);
    }
    /**
     * 判断字符串是否为null 或 去除前导和尾随空格后是否为空串
     */
    public static boolean nullOrTrimEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * 如果参数（param）名和值不为空, 则追加对应参数到地址中
     */
    public static StringBuilder addUrlParam(StringBuilder url, String paramName, Object paramValue){
        if (nullOrTrimEmpty(paramName) || paramValue == null) {
            return url;
        }
        String valueStr = paramValue.toString();
        if ("".equals(valueStr.trim())) {
            return url;
        }
        int endChar = url.charAt(url.length() - 1);
        if (endChar != '?' && endChar != '&') {
            url.append('&');
        }
        return url.append(paramName).append('=').append(valueStr);
    }

    /**
     * @param charset   字符集
     * @param len       长度，若为负数或0将默认为4
     * @return 根据字符集随机生成长度为len的字符串
     */
    public static String getRandString(String charset, int len) {
        if (len <= 0) {
            len = 4;
        }
        int setLen = charset.length();
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            //产生0到setLen-1的随机值
            int index = rand.nextInt(setLen);
            char c = charset.charAt(index);
            sb.append(c);
        }
        return sb.toString();
    }

}
