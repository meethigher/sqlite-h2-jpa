package top.meethigher.sqliteh2jpa.config;

import org.h2.server.web.WebServlet;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * h2配置
 *
 * @author chenchuancheng github.com/meethigher
 * @since 2023/1/15 17:39
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryH2",//配置连接工厂 entityManagerFactory
        transactionManagerRef = "transactionManagerH2", //配置 事物管理器  transactionManager
        basePackages = {"top.meethigher.sqliteh2jpa.entity.h2"}//设置持久层所在位置
)
public class H2Config {

    @Resource
    private DataSource h2DataSource;

    @Resource
    private JpaProperties jpaProperties;

    // 获取对应的数据库方言
    @Value("${spring.jpa.properties.dialect.h2}")
    private String h2Dialect;

    @Resource
    private HibernateProperties hibernateProperties;


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryH2(EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
        Map<String, String> map = new HashMap<>();
        // 设置对应的数据库方言
        map.put("hibernate.dialect", h2Dialect);
        jpaProperties.setProperties(map);
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());
        return entityManagerFactoryBuilder
                //设置数据源
                .dataSource(h2DataSource)
                //设置数据源属性
                .properties(properties)
                //设置实体类所在位置.扫描所有带有 @Entity 注解的类
                .packages("top.meethigher.sqliteh2jpa.entity.h2")
                // Spring会将EntityManagerFactory注入到Repository之中.有了 EntityManagerFactory之后,
                // Repository就能用它来创建 EntityManager 了,然后 EntityManager 就可以针对数据库执行操作
                .persistenceUnit("h2PersistenceUnit")
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManagerH2(@Qualifier("entityManagerFactoryH2") LocalContainerEntityManagerFactoryBean entityManagerFactoryH2) {
        return new JpaTransactionManager(entityManagerFactoryH2.getObject());
    }


    /**
     * h2数据库开启服务器模式，可以被远程连接
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "8888");
    }

    /**
     * h2数据库控制台
     * @see H2ConsoleAutoConfiguration
     */
    @Bean
    public ServletRegistrationBean h2Servlet() {
        ServletRegistrationBean h2Servlet = new ServletRegistrationBean(new WebServlet(), "/h2-console/*");
        h2Servlet.setLoadOnStartup(1);
        h2Servlet.addInitParameter("webAllowOthers", "false");
        h2Servlet.addInitParameter("webAdminPassword", "test");
//        h2Servlet.addInitParameter("trace", "true");
        h2Servlet.setName("H2Console");
        return h2Servlet;
    }


}
