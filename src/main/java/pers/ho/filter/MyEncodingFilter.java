package pers.ho.filter;

import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 黄欧
 * @create 2021-08-26 21:40
 * 自制限定目录下的字符编码过滤器，可以忽略urlPatterns下的某些路径
 * 主要是为了忽略 静态资源的字符编码过滤
 */
public class MyEncodingFilter extends CharacterEncodingFilter {
    /**
     * 默认编码
     */
    public static final String defaultEncoding = "UTF-8";
    /**
     * 若请求路径中含有这些字符串(不区分大小写)，则忽略字符编码过滤
     */
    public static final String[] ignoreArr = {"/static/"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isIgnore(request)) {
            filterChain.doFilter(request, response);
        } else {
            if (getEncoding() == null) {
                setEncoding(defaultEncoding);
            }
            setForceEncoding(true);     //强制使用该字符编码
            super.doFilterInternal(request, response, filterChain);
        }
    }

    /**
     * 判断该请求是否被忽略字符编码过滤
     */
    protected boolean isIgnore(HttpServletRequest request) {
        String path=request.getRequestURI().toLowerCase();
        for(String ignore:ignoreArr) {
            if(path.contains(ignore.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
