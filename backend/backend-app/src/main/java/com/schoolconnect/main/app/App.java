package com.schoolconnect.main.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * spring-boot main app class
 *
 */
@SpringBootApplication
@ComponentScan (basePackages = "com.schoolconnect")
public class App
{
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class, args);
    }
}
