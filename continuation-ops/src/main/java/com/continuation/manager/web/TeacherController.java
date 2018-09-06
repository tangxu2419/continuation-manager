package com.continuation.manager.web;

import com.continuation.manager.domain.po.mongo.SingleChoiceQuestionPO;
import com.continuation.manager.domain.po.mysql.TeacherPO;
import com.continuation.manager.exception.RequestParamException;
import com.continuation.manager.service.TeacherInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author tangxu
 * @date 2018/8/2010:51
 */
@RestController
@RequestMapping("/teacher")
@Slf4j
@AllArgsConstructor
public class TeacherController {

    private final TeacherInfoService teacherInfoService;

    @ResponseBody
    @GetMapping("/search")
    public ResponseEntity searchTeacher(@Param("query") String query, int page, int size, String sortBy, String order
    ) throws IOException {
        log.info("收到教师信息查询请求：[query:{}]", query);
        Page<TeacherPO> list = teacherInfoService.search(query, page - 1, size, sortBy, order);
        return ResponseEntity.ok(list);
    }

    @ResponseBody
    @GetMapping("/searchById")
    public ResponseEntity searchById(@Param("id") String id) {
        log.info("收到教师信息查询请求：[id：{}]",id);
        TeacherPO po = teacherInfoService.searchById(id);
        return ResponseEntity.ok(po);
    }

    @ResponseBody
    @PutMapping("/update")
    public ResponseEntity updateTeacher(@RequestBody TeacherPO teacherPO) throws Exception {
        log.info("收到教师信息更新请求：{}", teacherPO.toString());
        teacherInfoService.updateTeacher(teacherPO);
        return ResponseEntity.ok().build();
    }

}
