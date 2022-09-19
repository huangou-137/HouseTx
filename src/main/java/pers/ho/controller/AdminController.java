package pers.ho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.ho.bean.Admin;
import pers.ho.service.AdminService;
import pers.ho.utils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author 黄欧
 * @create 2021-08-10 20:25
 */
@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 获取其它管理员的信息（用户也可用）
     */
    @RequestMapping("/other/admin/{id}")
    public String otherAdmin(@PathVariable("id")Short adminId, Map<String,Object> model){
        if (adminId == null) {
            model.put("msg", "管理员ID异常！");
        } else {
            Admin otherAdmin = adminService.getOtherAdmin(adminId);
            if (otherAdmin == null) {
                model.put("msg", "该管理员不存在！");
            }  else {
                model.put("otherAdmin", otherAdmin);
            }
        }
        return "other/otherAdmin";
    }

    /**
     * 处理管理员登录的功能
     */
    @RequestMapping(value = "/login", params = "loginType=admin")
    public String login(@RequestParam String adminName, @RequestParam String pass,
                        Map<String,Object> model, HttpServletRequest req) {
        // 1、调用登录处理业务
        Admin loginAdmin = adminService.login(adminName, pass);
        if (loginAdmin == null) {
            // 2、登录失败!重新跳回登录页面
            model.put("adminName", adminName);
            model.put("msg", "管理员名或密码错误，请重新输入！");
            return "client/login";
        } else {
            // 2、登录成功，先使之前的会话无效化，再获取新的会话
            req.getSession().invalidate();
            HttpSession session = req.getSession(true);
            // 3、保存管理员ID和名、登录类型到新的Session域中
            session.setAttribute("loginAdminId", loginAdmin.getId());
            session.setAttribute("loginAdminName", loginAdmin.getAdminName());
            session.setAttribute("loginType","admin");
            return "admin/login_success";
        }
    }

    /**
     * 从session域中取出本次会话的登录管理员ID
     */
    public static Short getLoginAdminId(HttpServletRequest req) {
        return (Short) req.getSession().getAttribute("loginAdminId");
    }
    /**
     * 从session域中取出本次会话的登录管理员名称
     */
    public static String getLoginAdminName(HttpServletRequest req) {
        return (String) req.getSession().getAttribute("loginAdminName");
    }
    public static void setLoginAdminName(HttpServletRequest req, String adminName) {
        req.getSession().setAttribute("loginAdminName", adminName);
    }

    /**
     * 返回Json对象：管理员名是否存在/可用
     */
    @ResponseBody
    @RequestMapping("/ajax/existsAdminName")
    public boolean ajaxExistsAdminName(@RequestParam String adminName) {
        return WebUtils.nullOrTrimEmpty(adminName) || adminService.existsAdminName(adminName);
    }

}
