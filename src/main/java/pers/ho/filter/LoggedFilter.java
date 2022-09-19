package pers.ho.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 黄欧
 * @create 2021-03-27 16:01
 * 负责登录检查，普通用户和管理员登录后都能访问
 */
@WebFilter(urlPatterns = {"/pages/other/*", "/other/*"})
public class LoggedFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        HttpSession session = httpServletRequest.getSession();

        //获取登录用户类型
        String loginType = (String) session.getAttribute("loginType");
        if (loginType == null || "".equals(loginType)){
            System.out.println("LoggedFilter：未登录！");
            httpServletRequest.getRequestDispatcher("/pages/client/login.jsp").forward(req,resp);
        } else if (session.getAttribute("loginUid") == null && session.getAttribute("loginAdminId") == null) {
            System.out.println("LoggedFilter：登录状态已过期！");
            httpServletRequest.getRequestDispatcher("/pages/client/login.jsp").forward(req, resp);
        } else {
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {}
}
