package dev.afecioru.ecomm.app.config.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DbConfig {
    @Bean
    @Profile("local")
    public DataSource getLocalDataSource() {
        log.info("Configuring database connection for local environment");

        String dbHostName = System.getenv("DB_HOSTNAME");
        Integer dbPort = Integer.parseInt(System.getenv("DB_PORT"));
        String dbName = System.getenv("DB_NAME");
        String dbUser = System.getenv("DB_USERNAME");
        String dbPass = System.getenv("DB_PASSWORD");
        String dbUrl = String.format("jdbc:postgresql://%1$s:%2$d/%3$s", dbHostName, dbPort, dbName);

        return buildDataSource(dbUrl, dbUser, dbPass);
    }

    private DataSource buildDataSource(String dbUrl, String dbUser, String dbPass) {
        final DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url(dbUrl);
        dataSourceBuilder.username(dbUser);
        dataSourceBuilder.password(dbPass);

        return dataSourceBuilder.build();
    }
}
