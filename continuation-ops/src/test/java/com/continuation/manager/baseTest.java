package com.continuation.manager;

import com.continuation.manager.config.QuestionInfoConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2018/8/3115:29
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class baseTest {

    @Autowired
    private QuestionInfoConfig config;

    @Test
    public void test() throws Exception {
        System.out.println(config.getLevels());
        System.out.println(new ObjectMapper().writeValueAsString(config.getSubject()));
    }

}
