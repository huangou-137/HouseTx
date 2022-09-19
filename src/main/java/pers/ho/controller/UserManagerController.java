package pers.ho.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pers.ho.bean.User;
import pers.ho.service.UserManagerService;
import pers.ho.utils.ParseUtils;
import pers.ho.utils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/manager")
public class UserManagerController {
    @Autowired
    private UserManagerService userManagerService;

    /**
     * 获取用户列表（分页），可选请求参数：页码、显示条数
     */
    @RequestMapping("/users")
    public String page(HttpServletRequest req, Map<String, Object> model) {
        int pageNo = ParseUtils.parseInt(req,"pageNo", 1);
        int pageSize = ParseUtils.parseInt(req,"pageSize", 10);
        PageInfo<User> pageInfo = userManagerService.pageUsers(pageNo, pageSize);
        model.put("pageInfo", pageInfo);
        model.put("pageUrl", "manager/users?");
        return "manager/user/page";
    }

    /**
     * 获取某个用户的详情信息，只响应<b>GET</b>请求
     */
    @GetMapping("/user/{uid}")
    public String userDetails(@PathVariable Integer uid, Map<String, Object> model) {
        if (uid == null) {
            model.put("msg", "用户uid异常！");
        } else {
            User user = userManagerService.getUser(uid);
            if (user == null) {
                model.put("msg", "没有找到该用户！");
            } else {
                model.put("user", user);
            }
        }
        return "manager/user/details";
    }

    /**
     * 处理删除用户的请求，只响应<b>DEL</b>请求
     */
    @DeleteMapping("/user/{uid}")
    public String del(@PathVariable Integer uid, Map<String, Object> model) {
        if (userManagerService.delUser(uid)) {
            // 设置成功页面所需请求域对象，并跳转至操作成功页面
            return WebUtils.afterSuccess(model, "删除用户", 5, "manager/users"
                    , "用户列表页面", uid, null);
        } else {
            // 删除失败，重新获取用户详情
            model.put("msg","删除用户失败!");
            return userDetails(uid, model);
        }
    }

    /**
     * 修改用户前的准备————获取用户最新信息
     */
    @RequestMapping("/user/toUpd/{uid}")
    public String toUpd(@PathVariable Integer uid, Map<String, Object> model) {
        if (uid == null) {
            model.put("msg", "用户uid异常!！");
        } else {
            User user = userManagerService.getUser(uid);
            if (user == null) {
                model.put("msg", "没有找到该用户！");
            } else {
                model.put("user", user);
            }
        }
        return "manager/user/upd";
    }

    /**
     * 处理修改用户的请求，只响应<b>PUT</b>请求
     */
    @PutMapping("/user/{uid}")
    public String upd(@PathVariable Integer uid, @Valid User user, Errors errors,
                            Map<String, Object> model) {
        if (errors.getErrorCount() > 0) {
            WebUtils.afterValidError(errors, model, "用户");
            model.put("user", user);
            // 0、JSR303数据校验出错，重新返回修改页面
            return "manager/user/upd";
        }

        if (userManagerService.updateUser(user, uid)) {
            // 设置成功页面所需请求域对象，并跳转至操作成功页面
            return WebUtils.afterSuccess(model, "修改用户", 5, "manager/user/" + uid,
                    "用户详情页面", uid, null);
        } else {
            // 修改失败，重新进行 修改用户信息前的准备
            return toUpd(uid, model);
        }
    }

    /**
     * 处理添加用户的请求，只响应<b>POST</b>请求
     */
    @PostMapping("/user")
    public String add(@Valid User user, Errors errors, Map<String, Object> model) {
        if (errors.getErrorCount() > 0) {
            WebUtils.afterValidError(errors, model, "用户");
            model.put("user", user);
            // 0、JSR303数据校验出错，重新返回添加页面
            return "manager/user/add";
        }

        Integer uid = userManagerService.addUser(user);
        if (uid != null) {
            // 设置成功页面所需请求域对象，并跳转至操作成功页面
            return WebUtils.afterSuccess(model, "添加用户", 7, "manager/user/" + uid,
                    "用户详情页面", null, "用户UID为：" + uid);
        } else {
            // 添加失败则把回显信息保存到Request域中，并返回添加页面
            model.put("user", user);
            model.put("msg", "添加用户失败！");
            return "manager/user/add";
        }
    }

}