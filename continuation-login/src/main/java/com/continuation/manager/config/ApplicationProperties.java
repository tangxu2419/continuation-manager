package com.continuation.manager.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author tangxu
 * @date 2018/8/1319:31
 */
@Configuration
@ConfigurationProperties("continuation")
@Getter
@Setter
public class ApplicationProperties {

    private Duration verCodeLiveTime;
    private Duration loginLiveTime;

}
