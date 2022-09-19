package pers.ho.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.sql.DataSource;

/**
 * 根容器的配置类，该配置类就类似于Spring的配置文件
 * @author 黄欧
 * @Date 2021/10/25 21:33
 */
@ComponentScan(value = "pers.ho",
    excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, ControllerAdvice.class})
})
@PropertySource(value = "classpath:dbConfig.properties")   //加载外部的配置文件
@EnableTransactionManagement    //开启事务，相当于<tx:annotation-driven/>
public class RootConfig {

    @Value("${jdbc.driverClass}")   private String driverClass;
    @Value("${jdbc.url}")           private String jdbcUrl;
    @Value("${jdbc.user}")          private String user;
    @Value("${jdbc.password}")      private String password;

    @Bean
    public DataSource dataSource() throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(jdbcUrl);
        return dataSource;
    }

    //2、配置事务管理器
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
