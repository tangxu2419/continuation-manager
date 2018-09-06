package com.continuation.manager.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2018/8/1514:42
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/teacherInfo")
    public String teacher() {
        return "teacherInfo";
    }

    @GetMapping("/studentInfo")
    public String studentInfo() {
        return "studentInfo";
    }

    @GetMapping("/student")
    public String student() {
        return "student";
    }

    @GetMapping("/questionSingleChoice")
    public String questionSingleChoice() {
        return "questionSingleChoice";
    }

}
