package org.main.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {
        "core",
        "org.main.application.controller",
        "org.main.application.service",
        "org.main.application.config"
})
public class Application {

    static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
