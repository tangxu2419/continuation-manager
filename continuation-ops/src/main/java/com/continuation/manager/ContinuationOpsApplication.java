package com.continuation.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author tangxu
 * @date 2018/8/1317:02
 */
@EnableJpaAuditing
@SpringBootApplication
public class ContinuationOpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContinuationOpsApplication.class,args);
    }

}
