package com.continuation.manager.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author tangxu
 * @date 2018/9/617:44
 */
@Configuration
@ConfigurationProperties("continuation")
@Getter
@Setter
public class ApplicationProperties {

    private String loginUrl;
    private String loginInitUrl;
}
