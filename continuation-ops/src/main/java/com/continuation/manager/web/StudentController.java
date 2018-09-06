package com.continuation.manager.web;

import com.continuation.manager.domain.po.mysql.StudentPO;
import com.continuation.manager.service.StudentService;
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
 * @date 2018/8/2117:52
 */
@Slf4j
@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @ResponseBody
    @PutMapping("/update")
    public ResponseEntity updateStudent(@RequestBody StudentPO student) throws Exception {
        log.info("收到教师信息更新请求：{}", student.toString());
        studentService.updateStudent(student);
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @GetMapping("/search")
    public ResponseEntity searchStudent(@Param("query") String query, int page, int size, String sortBy, String order
    ) throws IOException {
        log.info("收到教师信息查询请求：[query:{}]", query);
        Page<StudentPO> list = studentService.search(query, page - 1, size, sortBy, order);
        return ResponseEntity.ok(list);
    }

    @ResponseBody
    @GetMapping("/searchById")
    public ResponseEntity searchById(@Param("id") String id) {
        log.info("收到教师信息查询请求：[id：{}]", id);
        StudentPO po = studentService.searchById(id);
        return ResponseEntity.ok(po);
    }

}
