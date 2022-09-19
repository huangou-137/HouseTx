package pers.ho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.ho.bean.User;
import pers.ho.service.UserService;
import pers.ho.utils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author 黄欧
 * @create 2021-08-21 17:21
 * 负责登录后的用户请求
 */
@Controller
@RequestMapping("/user")
public class LoggedUserController {
    @Autowired
    private UserService userService;

    /**
     * 获取本登录用户ID，据此查询得到用户信息后保存至请求域中
     */
    @ModelAttribute
    public void getMyInfo(HttpServletRequest req, Map<String,Object> model) {
        User user = userService.getUser(UserController.getLoginUid(req));
        model.put("user", user);
        // 更新登录用户名
        UserController.setLoginUsername(req, user.getUsername());
    }

    /**
     * 获取本登录用户的最新全部信息
     */
    @RequestMapping("/myInfo")
    public String myInfo() {
        return "user/myInfo";
    }

    /**
     * 前往修改个人用户信息页面前的准备————获取最新的登录用户信息
     */
    @RequestMapping("/toUpd")
    public String toUpd() {
        return "user/updInfo";
    }

    /**
     * 处理更新用户信息的请求
     */
    @RequestMapping("/upd")
    public String upd(@Valid User user, Errors errors, Map<String,Object> model, HttpServletRequest req) {
        if (errors.getErrorCount() > 0) {
            WebUtils.afterValidError(errors, model, "用户");
            model.put("user", user);
            // 0、JSR303数据校验出错，重新返回修改页面
            return "user/updInfo";
        }

        boolean success = false;        //是否修改成功
        boolean relogin = false;        //是否需要重新登录
        // 1、检查 用户名 是否可用（与登录用户名一致则无须验证可用）
        String username = user.getUsername();
        if (username.equals(UserController.getLoginUsername(req)) ||
                userService.existsUsername(username)) {
            // 2、判断管理员是否在更新个人信息页面填入了密码，即请求参数中的密码值不为空
            if (!WebUtils.nullOrTrimEmpty(req.getParameter("loginPass"))) {
                relogin = true;
                // 取出在ModelAttribute方法中从数据库获取到的登录密码值
                user.setLoginPass(((User)model.get("user")).getLoginPass());
            }
            if (!WebUtils.nullOrTrimEmpty(req.getParameter("txPass"))) {
                user.setTxPass(((User)model.get("user")).getTxPass());
            }
            // 3、用户名未发生更改或可用，调用更新用户信息业务方法
            success = userService.updateUser(user, UserController.getLoginUid(req));
            System.out.println("\n\n*****************分割线****************\n\n");
            if (!success) {
                model.put("msg", "用户信息修改失败！！");
            }
        } else {
            model.put("msg", "用户名已存在，请重新输入用户信息！！");
        }

        if (success) {
            // 4、修改成功，更新用户名，并设置相对应成功页面所需请求域对象，并跳转至操作成功页面
            UserController.setLoginUsername(req, username);
            if (relogin) {
                return WebUtils.afterSuccess(model, "修改用户信息",9,"reToLogin",
                        "登录页面",null,"因用户登录密码修改了，需要重新登录。");
            } else {
                return WebUtils.afterSuccess(model,"修改用户信息",6,"user/myInfo"
                        ,"用户个人资料页面",null,null);
            }
        } else {
            // 4、修改失败，重新进行 修改个人信息前的准备
            return "forward:/user/toUpd";
        }
    }

}