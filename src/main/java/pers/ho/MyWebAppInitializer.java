package pers.ho;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import pers.ho.config.AppConfig;
import pers.ho.config.MPConfig;
import pers.ho.config.RootConfig;
import pers.ho.filter.MyEncodingFilter;

import javax.servlet.Filter;

/**
 * 在web容器启动的时候创建对象，且在整个创建的过程中，会调用相应方法来初始化容器以及前端控制器。<br/>
 * 编写好该类之后，就相当于是在以前我们配置好了web.xml文件
 * @author 黄欧
 * @Date 2021/10/25 21:24
 */
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Filter[] getServletFilters() {
        //自定义字符编码过滤器f1：要在f2前面，因为f2先获取请求参数_method会让编码失效
        MyEncodingFilter encodingFilter = new MyEncodingFilter();
        //支持REST风格的过滤器f2：可以将POST请求转换为PUT或DELETE请求
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        return new Filter[]{encodingFilter, hiddenHttpMethodFilter};
    }

    //获取根容器的配置类，然后创建出一个父容器
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class, MPConfig.class};
    }

    //获取web容器的配置类，然后创建出一个子容器
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    // 获取DispatcherServlet的映射信息
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
