package top.meethigher.sqliteh2jpa.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * sqlite
 *
 * @author chenchuancheng github.com/meethigher
 * @since 2023/1/15 21:39
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactorySQLite",//配置连接工厂 entityManagerFactory
        transactionManagerRef = "transactionManagerSQLite", //配置 事物管理器  transactionManager
        basePackages = {"top.meethigher.sqliteh2jpa.entity.sqlite"}//设置持久层所在位置
)
public class SQLiteConfig {

    @Resource
    private DataSource sqliteDataSource;

    @Resource
    private JpaProperties jpaProperties;

    // 获取对应的数据库方言
    @Value("${spring.jpa.properties.dialect.sqlite}")
    private String sqliteDialect;

    @Resource
    private HibernateProperties hibernateProperties;


    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactorySQLite(EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
        Map<String, String> map = new HashMap<>();
        // 设置对应的数据库方言
        map.put("hibernate.dialect", sqliteDialect);
        jpaProperties.setProperties(map);
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());
        return entityManagerFactoryBuilder
                //设置数据源
                .dataSource(sqliteDataSource)
                //设置数据源属性
                .properties(properties)
                //设置实体类所在位置.扫描所有带有 @Entity 注解的类
                .packages("top.meethigher.sqliteh2jpa.entity.sqlite")
                // Spring会将EntityManagerFactory注入到Repository之中.有了 EntityManagerFactory之后,
                // Repository就能用它来创建 EntityManager 了,然后 EntityManager 就可以针对数据库执行操作
                .persistenceUnit("sqlitePersistenceUnit")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManagerSQLite(@Qualifier("entityManagerFactorySQLite") LocalContainerEntityManagerFactoryBean entityManagerFactorySQLite) {
        return new JpaTransactionManager(entityManagerFactorySQLite.getObject());
    }
}
