package com.continuation.manager.web;

import com.continuation.manager.domain.po.mysql.TeacherPO;
import com.continuation.manager.exception.RequestParamException;
import com.continuation.manager.service.TeacherInfoService;
import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.provider.digest.MD5;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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
    public ResponseEntity searchTeacher(
            @RequestParam(value = "workNumber", required = false) String workNumber,
            @RequestParam(value = "teacherLevel", required = false) String teacherLevel,
            @RequestParam(value = "voided", required = false) Boolean voided
    ) {
        log.info("收到教师信息查询请求：[workNumber:{},teacherLevel:{},voided:{}]", workNumber, teacherLevel, voided);
        List<TeacherPO> list = teacherInfoService.search(workNumber, teacherLevel, voided);
        return ResponseEntity.ok(list);
    }

    @ResponseBody
    @PutMapping("/update")
    public ResponseEntity updateTeacher(@RequestBody TeacherPO teacherPO) throws RequestParamException {
        log.info("收到教师信息更新请求：{}", teacherPO.toString());
        teacherInfoService.updateTeacher(teacherPO);
        return ResponseEntity.ok().build();
    }

}
