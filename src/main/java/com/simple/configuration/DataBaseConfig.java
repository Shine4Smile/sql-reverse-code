package com.simple.configuration;

import com.simple.bean.DataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * 配置JdbcTemplate
 *
 * @author Simple
 */
@Configuration
public class DataBaseConfig {
    @Bean
    public DataSource sqliteDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:src/main/resources/generate.db");
        return dataSource;
    }

    @Bean
    public JdbcTemplate sqliteJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(sqliteDataSource());
    }

    public static DataSource builtDataSource(DataSourceConfig config) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DBConnect.getDriverClass(config.getDbType()));
        dataSource.setUsername(config.getUserName());
        dataSource.setPassword(config.getPassword());
        dataSource.setUrl(DBConnect.formatJdbcUrl(config));
        return dataSource;
    }

    /**
     * 为在程序运行中根据用户选择的数据源创建jdbcTemplate模板，进行数据库操作
     *
     * @param config 数据库配置
     * @return JdbcTemplate
     */
    public static JdbcTemplate otherJdbcTemplate(DataSourceConfig config) {
        return new JdbcTemplate(builtDataSource(config));
    }
}
