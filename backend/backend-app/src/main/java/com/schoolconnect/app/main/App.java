package com.schoolconnect.app.main;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * spring-boot main app class
 *
 */
@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan (basePackages = "com.schoolconnect")
@EnableJpaRepositories(basePackages = "com.schoolconnect.app.repository")
@EntityScan("com.schoolconnect.app.entity")
public class App
{
	@Value("${name}")
	static String name;
	
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class, args);
    }
}
