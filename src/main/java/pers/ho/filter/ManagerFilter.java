package pers.ho.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 黄欧
 * @create 2021-03-27 18:01
 * 负责管理员登录检查，管理员登录后能访问，普通用户没权限
 */
@WebFilter(urlPatterns = {"/pages/manager/*","/manager/*","/pages/admin/*","/admin/*"})
public class ManagerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        HttpSession session = httpServletRequest.getSession();

        //获取登录用户类型
        String loginType = (String) session.getAttribute("loginType");
        if (loginType == null || "".equals(loginType)){
            System.out.println("ManagerFilter：未登录！");
            httpServletRequest.getRequestDispatcher("/pages/client/login.jsp").forward(req,resp);
        }else if (loginType.equals("user")) {
            System.out.println("ManagerFilter：普通用户无权访问!!!");
            httpServletRequest.getRequestDispatcher("/pages/error/error401_4.jsp");
        } else {
            if (loginType.equals("admin") && session.getAttribute("loginAdminId") != null) {
                chain.doFilter(req, resp);
            } else {
                System.out.println("ManagerFilter：非管理员登录，或登录状态可能已过期");
                httpServletRequest.getRequestDispatcher("/pages/client/login.jsp").forward(req, resp);
            }
        }
    }

    @Override
    public void destroy() {}
}
