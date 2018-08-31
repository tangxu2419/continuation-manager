package com.continuation.manager.web;

import com.continuation.manager.domain.po.mysql.StudentPO;
import com.continuation.manager.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity update(@RequestBody StudentPO student) {
        return ResponseEntity.ok().build();
    }

}
