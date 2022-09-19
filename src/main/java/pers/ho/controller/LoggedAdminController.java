package pers.ho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.ho.bean.Admin;
import pers.ho.service.AdminService;
import pers.ho.utils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author 黄欧
 * @create 2021-08-11 23:15
 * 负责登录后的管理员请求
 */
@Controller
@RequestMapping("/admin")
public class LoggedAdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 获取本登录管理员ID，据此查询得到管理员信息后保存至请求域中
     */
    @ModelAttribute
    public void getMyInfo(HttpServletRequest req, Map<String,Object> model) {
        Admin loginAdmin = adminService.getAdmin(AdminController.getLoginAdminId(req));
        model.put("admin", loginAdmin);
        // 更新登录管理员名
        AdminController.setLoginAdminName(req, loginAdmin.getAdminName());
    }

    /**
     * 获取本登录管理员的最新全部信息
     */
    @RequestMapping("/myInfo")
    public String myInfo() {
        return "admin/myInfo";
    }

    /**
     * 前往修改个人信息页面前———须获取最新的登录管理员信息
     */
    @RequestMapping("/toUpd")
    public String toUpd() {
        return "admin/updInfo";
    }

    /**
     * 处理更新管理员信息的请求
     */
    @RequestMapping("/upd")
    public String upd(@Valid Admin admin, Errors errors, Map<String,Object> model, HttpServletRequest req) {
        if (errors.getErrorCount() > 0) {
            WebUtils.afterValidError(errors, model, "管理员");
            model.put("admin", admin);
            // 0、JSR303数据校验出错，重新返回修改个人信息页面
            return "admin/updInfo";
        }

        boolean success = false;        //是否修改成功
        boolean reLogin = false;        //是否需要重新登录
        // 1、检查 管理员名 是否可用（与登录管理员名一致则无须验证）
        String adminName = admin.getAdminName();
        if (WebUtils.nullOrTrimEmpty(adminName)) {
            model.put("msg", "管理员名为空，请重新输入相关信息！！");
        } else if (adminName.equals(AdminController.getLoginAdminName(req)) ||
                adminService.existsAdminName(adminName)) {
            // 2、判断管理员是否在更新个人信息页面填入了密码，即请求参数中的密码值不为空
            if (!WebUtils.nullOrTrimEmpty(req.getParameter("pass"))) {
                reLogin = true;
                // 取出在ModelAttribute方法中从数据库获取到的密码值
                admin.setPass(((Admin)model.get("admin")).getPass());
            }
            // 3、管理员名未发生更改或可用，调用更新管理员信息业务方法
            success = adminService.updateAdmin(admin, AdminController.getLoginAdminId(req));
            if (!success) {
                model.put("msg", "管理员信息修改失败！！");
            }
        } else {
            model.put("msg", "管理员名已被使用或不可用，请重新输入相关信息！！");
        }

        if (success) {
            // 4、修改成功，更新登录管理员名，并设置相对应所需请求域对象后跳转至操作成功页面
            AdminController.setLoginAdminName(req, adminName);
            if (reLogin) {
                return WebUtils.afterSuccess(model,"修改个人信息",9,"reToLogin",
                        "登录页面",null,"因密码修改了，需要重新登录。");
            } else {
                return WebUtils.afterSuccess(model,"修改个人信息",6,"admin/myInfo"
                        ,"管理员个人资料页面",null,null);
            }
        } else {
            // 4、修改失败，重新进行 修改个人信息前准备
            return "forward:/admin/toUpd";
        }
    }

}