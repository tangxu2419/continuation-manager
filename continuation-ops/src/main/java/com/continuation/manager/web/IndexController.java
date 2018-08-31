package com.continuation.manager.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2018/8/1514:42
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model){
        return "teacherInfo";
    }

    @GetMapping("/teacherInfo")
    public String teacher(Model model){
        return "teacherInfo";
    }

    @GetMapping("/student")
    public String student(Model model){
        return "student";
    }

    @GetMapping("/questionSingleChoice")
    public String questionSingleChoice(Model model){
        return "questionSingleChoice";
    }

}
