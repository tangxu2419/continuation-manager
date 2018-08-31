package com.continuation.manager.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2018/8/3115:15
 */
@Configuration
@ConfigurationProperties("base.conf")
@Getter
@Setter
public class QuestionInfoConfig {

    private List<String> levels;

    private List<Map<String,List>> subject;
}
