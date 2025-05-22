package io.github.alancavalcante_dev.desafio_picpay_backend.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:devdb");
        config.setDriverClassName("org.h2.Driver");
        config.setUsername("sa");
        config.setPassword("");

        // HikariCP settings
        config.setConnectionTimeout(30000);            // 30 segundos
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setInitializationFailTimeout(-1);     // 3 segundos

        return new HikariDataSource(config);
    }
}