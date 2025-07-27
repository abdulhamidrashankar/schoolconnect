package com.schoolconnect.config.db;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@Profile("h2-db-file")
@PropertySources({
    @PropertySource("classpath:h2-db-file-connection.properties")
})
public class H2dbFileConfig {
	// Only loaded if profile is "h2"
}
