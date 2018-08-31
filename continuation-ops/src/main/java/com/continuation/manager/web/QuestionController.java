package com.continuation.manager.web;

import com.continuation.manager.domain.po.mongo.SingleChoiceQuestionPO;
import com.continuation.manager.service.QuestionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2018/8/2117:28
 */
@Slf4j
@RestController
@RequestMapping("/question")
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @ResponseBody
    @PutMapping("/update/singleChoice")
    public ResponseEntity updateSingleChoice(@RequestBody SingleChoiceQuestionPO questionPO) {
        log.info("收到更新单项选择请求：{}",questionPO.toString());
        questionService.updateSingleChoice(questionPO);
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @GetMapping("/search/singleChoice")
    public ResponseEntity searchSingleChoice(@Param("query") String query, int page, int size, String sortBy, String order
    ) throws IOException {
        log.info("收到单项选择题条件查询：[{}]",query);
        Page<SingleChoiceQuestionPO> list = questionService.searchSingleChoice(query, page - 1, size, sortBy, order);
        return ResponseEntity.ok(list);
    }

    @ResponseBody
    @GetMapping("/search/singleChoiceById")
    public ResponseEntity searchSingleChoiceById(@Param("id") String id) {
        log.info("收到单项选择题主键查询：[id：{}]",id);
        SingleChoiceQuestionPO po = questionService.searchSingleChoice(id);
        return ResponseEntity.ok(po);
    }


}
