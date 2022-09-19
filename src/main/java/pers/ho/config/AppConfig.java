package pers.ho.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.*;

/**
 * web容器的配置类，该配置类就类似于Spring MVC的配置文件
 * @author 黄欧
 * @Date 2021/10/25 21:33
 * 不要使用@EnableWebMvc
 */
//useDefaultFilters=false 禁用默认的过滤规则；
@ComponentScan(value = "pers.ho.controller",
    includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, ControllerAdvice.class})
    }, useDefaultFilters = false)
@EnableWebMvc   //相当于<mvc:annotation-driven/>，开启mvc注解驱动,能支持SpringMVC更高级的功能
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/pages/", ".jsp");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        //相当于<mvc:default-servlet-handler/>，可开放对静态资源的访问,即将SpringMVC不能处理的请求交给Tomcat
        configurer.enable();
    }
}
