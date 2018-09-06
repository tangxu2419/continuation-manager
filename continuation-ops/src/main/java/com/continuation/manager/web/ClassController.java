package com.continuation.manager.web;

import com.continuation.manager.domain.po.mysql.ClassPO;
import com.continuation.manager.service.ClassService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2018/9/417:33
 */
@Slf4j
@RestController
@RequestMapping("/class")
@AllArgsConstructor
public class ClassController {

    private final ClassService classService;

    @ResponseBody
    @GetMapping("/findByYear")
    public ResponseEntity findByYear(@Param("year") String year) {
        log.info("收到根据年份查询班级请求：[year：{}]", year);
        List<ClassPO> list = classService.findByYear(year);
        return ResponseEntity.ok(list);
    }

    @ResponseBody
    @GetMapping("/findAll")
    public ResponseEntity findAll() {
        log.info("收到查询所有班级请求");
        List<ClassPO> list = classService.findAll();
        return ResponseEntity.ok(list);
    }

}
