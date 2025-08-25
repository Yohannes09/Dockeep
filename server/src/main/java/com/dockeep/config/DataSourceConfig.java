package com.dockeep.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Profile("prod")
    @Bean("productionDataSource")
    public DataSource productionDataSource(
            @Value("${db.jdbc-url}")String jdbcUrl,
            @Value("${db.username}")String dbUsername,
            @Value("${db.password}")String dbPassword,
            @Value("${db.driver}")String dbDriver,
            @Value("${db.max-pool-size:10}")int dbMaxPoolSize,
            @Value("${db.min-idle-connections:5}")int dbMinIdleConnections,
            @Value("${db.idle-timeout-ms:30000}")long dbIdleTimeoutMs,
            @Value("${db.max-lifetime-ms:1800000}")long dbMaxLifetimeMs
    ){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(dbUsername);
        config.setPassword(dbPassword);
        config.setDriverClassName(dbDriver);
        config.setMaximumPoolSize(dbMaxPoolSize);
        config.setMinimumIdle(dbMinIdleConnections);
        config.setIdleTimeout(dbIdleTimeoutMs);
        config.setMaxLifetime(dbMaxLifetimeMs);

        return new HikariDataSource(config);
    }

    @Profile("dev")
    @Bean("developmentDataSource")
    public DataSource developmentDataSource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/dockeep_dev");
        config.setUsername("postgres");
        config.setPassword("");
        config.setDriverClassName("org.postgresql.Driver");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(10);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(1800000);

        return new HikariDataSource(config);
    }

}
