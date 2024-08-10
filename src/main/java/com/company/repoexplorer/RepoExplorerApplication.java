package com.company.repoexplorer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties
public class RepoExplorerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RepoExplorerApplication.class, args);
    }

}
