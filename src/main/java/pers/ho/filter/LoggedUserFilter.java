package pers.ho.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 黄欧
 * @create 2021-03-27 16:01
 * 负责登录检查，普通用户登录后能访问，管理员没权限
 */
@WebFilter(urlPatterns = {"/pages/user/*", "/user/*", "/pages/house/*", "/house/*", "/pages/order/*", "/order/*"})
public class LoggedUserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        HttpSession session = httpServletRequest.getSession();

        //获取登录用户类型
        String loginType = (String) session.getAttribute("loginType");
        if (loginType == null || "".equals(loginType)){
            System.out.println("LoggedUserFilter：未登录！");
            httpServletRequest.getRequestDispatcher("/pages/client/login.jsp").forward(req, resp);
        }else if (loginType.equals("admin")) {
            System.out.println("LoggedUserFilter：管理员无权访问!!!");
            httpServletRequest.getRequestDispatcher("/pages/error/error401_4.jsp").forward(req, resp);
        } else {
            if (loginType.equals("user") && session.getAttribute("loginUid") != null) {
                chain.doFilter(req, resp);
            } else {
                System.out.println("LoggedUserFilter：不是普通用户登录，或登录状态可能已过期！！！");
                httpServletRequest.getRequestDispatcher("/pages/client/login.jsp").forward(req, resp);
            }
        }
    }

    @Override
    public void destroy() {}
}
