package pers.ho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.ho.bean.User;
import pers.ho.service.UserService;
import pers.ho.utils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 返回Json对象：验证用户名是否存在/可用
     */
    @ResponseBody
    @RequestMapping("/ajax/existsUsername")
    public boolean ajaxExistsUsername(@RequestParam String username) {
        return WebUtils.nullOrTrimEmpty(username) || userService.existsUsername(username);
    }

    /**
     * 处理用户登录的功能
     */
    @RequestMapping(value = "/login", params = "loginType=user")
    public String login(@RequestParam String username, @RequestParam String loginPass,
                        Map<String,Object> model, HttpServletRequest req) {
        // 1、调用登录处理业务
        User loginUser = userService.login(username, loginPass);
        if (loginUser == null) {
            // 2、登录失败，重新跳回登录页面
            model.put("username", username);
            model.put("msg", "用户名或密码错误，请重新输入！");
            return "client/login";
        } else {
            // 2、登录成功，先使之前的会话无效化，再获取新的会话
            req.getSession().invalidate();
            HttpSession session = req.getSession(true);
            // 3、保存登录用户ID、用户名以及登录类型到新的Session域中
            session.setAttribute("loginUid", loginUser.getUid());
            session.setAttribute("loginUsername", loginUser.getUsername());
            session.setAttribute("loginType","user");
            return "user/login_success";
        }
    }

    /**
     * 根据请求对象从session域中取出本次会话的登录用户ID
     */
    public static Integer getLoginUid(HttpServletRequest req) {
        return (Integer) req.getSession().getAttribute("loginUid");
    }
    /**
     * 根据请求对象从session域中取出本次会话的登录用户名
     */
    public static String getLoginUsername(HttpServletRequest req) {
        return (String) req.getSession().getAttribute("loginUsername");
    }
    public static void setLoginUsername(HttpServletRequest req, String username) {
        req.getSession().setAttribute("loginUsername", username);
    }

    /**
     * 处理注册的功能
     */
    @RequestMapping("/regist")
    public String regist(@Valid User user, Errors errors, Map<String,Object> model, HttpServletRequest req) {
        if (errors.getErrorCount() > 0) {
            WebUtils.afterValidError(errors, model, "用户");
            model.put("user", user);
            // 0、JSR303数据校验出错，重新返回注册页面
            return "client/regist";
        }

        // 1、检查验证码是否正确
        if (CheckCodeController.verify(req)) {
            // 2、检查 用户名 是否可用
            if (userService.existsUsername(user.getUsername())) {
                // 3、用户已存在或不可用：
                model.put("msg", "用户名已存在，请重新输入用户信息！！");
            } else {
                // 4、用户名可用，调用service层的方法保存到数据库
                Integer uid = userService.registUser(user);
                if (uid != null) {
                    // 5、注册成功：设置成功页面所需请求域对象，并跳转至操作成功页面
                    return WebUtils.afterSuccess(model,"用户注册",6,"pages/client/login.jsp",
                            "登录页面", uid,"用户UID为：" + uid);
                } else {
                    model.put("msg", "注册失败！！");
                }
            }
        } else {
            // 3、验证码错误，保存错误信息到Request域中
            model.put("msg", "验证码错误！！");
        }

        // 5、注册失败或验证码错误：把回显信息保存到Request域中，并重新跳回注册页面
        model.put("user", user);
        return "client/regist";
    }

    /**
     * 获取其它用户的信息（管理员也可用）
     */
    @RequestMapping("/other/user/{id}")
    public String getOtherUser(@PathVariable("id")Integer uid, Map<String,Object> model) {
        if (uid == null) {
            model.put("msg","用户ID异常！");
        } else {
            User otherUser = userService.getOtherUser(uid);
            if (otherUser == null) {
                model.put("msg","该用户不存在！");
            } else {
                model.put("otherUser", otherUser);
            }
        }
        return "other/otherUser";
    }

}