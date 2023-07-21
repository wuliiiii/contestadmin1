package org.algorithmcontestdatacollect.crawlerendpoint2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
public class CrawlerEndpoint2Application {
    public static void main(String[] args) {
        SpringApplication.run(CrawlerEndpoint2Application.class, args);
    }

}
