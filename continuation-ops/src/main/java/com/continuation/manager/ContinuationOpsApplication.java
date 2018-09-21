package com.continuation.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

/**
 * @author tangxu
 * @date 2018/8/1317:02
 */
@EnableDiscoveryClient
@EnableJpaAuditing
@SpringBootApplication
public class ContinuationOpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContinuationOpsApplication.class,args);
    }

    /**
     * Spring提供的用于访问Rest服务的客户端
     */
    @Bean
//    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
