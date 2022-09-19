package pers.ho.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import pers.ho.bean.House;
import pers.ho.bean.Order;
import pers.ho.bean.User;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author 黄欧
 * @Date 2021/10/27 21:18
 */
@MapperScan("pers.ho.mapper")   //扫描所有的mapper接口
public class MPConfig {

    //相当于mybatis全局配置文件
    @Bean
    public MybatisConfiguration configuration() {
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);    //开启驼峰命名自动映射
        configuration.setAutoMappingBehavior(AutoMappingBehavior.FULL); //自动映射任意复杂的结果集（无论是否嵌套）
        configuration.setLogImpl(Log4jImpl.class);          //所用日志的具体实现
        configuration.setJdbcTypeForNull(JdbcType.NULL);    //为空值指定 JDBC 类型
        configuration.setCacheEnabled(true);                //开启所有Mapper文件中已配置的任何缓存
        TypeAliasRegistry typeAliasRegistry = configuration.getTypeAliasRegistry();
        typeAliasRegistry.registerAlias("house", House.class);
        typeAliasRegistry.registerAlias("user", User.class);
        typeAliasRegistry.registerAlias("order", Order.class);
        return configuration;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, MybatisConfiguration configuration) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setConfiguration(configuration);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(
                "classpath:pers/ho/mapper/impl/*.xml"));    //指定MyBatis Mapper XML文件的位置
        //配置分页插件
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        pageInterceptor.setProperties(properties);
        factoryBean.setPlugins(pageInterceptor);
        return factoryBean.getObject();
    }

}
