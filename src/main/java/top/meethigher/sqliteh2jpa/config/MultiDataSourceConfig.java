package top.meethigher.sqliteh2jpa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 多数据源配置
 *
 * @author chenchuancheng github.com/meethigher
 * @since 2023/1/15 17:37
 */
@Configuration
public class MultiDataSourceConfig {


    @ConfigurationProperties("spring.datasource.h2")
    @Bean
    public DataSource h2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @ConfigurationProperties("spring.datasource.sqlite")
    @Bean
    @Primary
    public DataSource sqliteDataSource() {
        return DataSourceBuilder.create().build();
    }
}
