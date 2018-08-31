package com.continuation.manager.web;

import com.continuation.manager.config.QuestionInfoConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2018/8/3115:43
 */
@Slf4j
@RestController
@RequestMapping("/config")
@AllArgsConstructor
public class ConfigController {

    private QuestionInfoConfig questionInfoConfig;

    @GetMapping("/question")
    @ResponseBody
    public ResponseEntity questionInfoConfig(){
        HashMap<Object, Object> map = new HashMap<>(2) {{
            put("levels", questionInfoConfig.getLevels());
            put("subject", questionInfoConfig.getSubject());
        }};
        return ResponseEntity.ok(map);
    }


}
