package com.schoolconnect.config.db;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@Profile("pgsql-db")
@PropertySources({
    @PropertySource("classpath:pgsql-db-connection.properties")
})
public class PQSQLdbConfig {
	// Only loaded if profile is "pgsql"
}
