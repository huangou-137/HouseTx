package pers.ho.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 黄欧
 * @create 2021-08-11 12:50
 */
@Controller
public class CommonController {

    /**
     * 退出登录（包括管理员和普通用户）
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        //1、销毁Session中的所有信息
        session.invalidate();
        //2、重定向到首页
        return "redirect:/index.jsp";
    }

    /**
     * 指退出当前用户/管理员的登录，重新返回登录页面
     */
    @RequestMapping("/reToLogin")
    public String reToLogin(HttpServletRequest req, HttpSession session) {
        // 1、获取登录类型
        String loginType = (String) session.getAttribute("loginType");
        if ("user".equals(loginType)) {
            // 2、获取登录用户名，并将其保存至请求作用域中
            String loginUsername = (String) session.getAttribute("loginUsername");
            req.setAttribute("username", loginUsername);
        } else if ("admin".equals(loginType)){
            // 2、获取登录管理员名，并将其保存至请求作用域中
            String loginAdminName = (String) session.getAttribute("loginAdminName");
            req.setAttribute("adminName", loginAdminName);
        }
        // 3、销毁Session中的所有信息
        session.invalidate();
        // 4、重新跳回登录页面
        return "client/login";
    }
}
