package com.continuation.manager.web;

import com.continuation.manager.config.ApplicationProperties;
import com.continuation.manager.constants.ReasonCodeConstants;
import com.continuation.manager.domain.dto.BaseExaminationPaperDTO;
import com.continuation.manager.domain.dto.BaseResponseDTO;
import com.continuation.manager.domain.po.mysql.StudentPO;
import com.continuation.manager.enums.QuestionTypeEnum;
import com.continuation.manager.service.RedisService;
import com.continuation.manager.service.UnitTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author tangxu
 * @Title: 单元测试
 * @date 2018/8/1419:51
 */
@Slf4j
@RestController
@RequestMapping("/continuation/test")
public class UnitTestController extends BaseController{

    private final UnitTestService unitTestService;

    private final ApplicationProperties properties;

    public UnitTestController(RedisService redisService, UnitTestService unitTestService,  ApplicationProperties properties) {
        super(redisService);
        this.unitTestService = unitTestService;
        this.properties = properties;
    }

    @GetMapping("/getTestPaper")
    public BaseResponseDTO testPaperQueryRequest(
            @RequestHeader(value = "login_token")String loginToken,
            @RequestParam(value = "subject") String subject,
            @RequestParam(value = "unit") String unit,
            @RequestParam(value = "type") QuestionTypeEnum type
    ) throws Exception {
        log.info("收到获取单元测试试卷请求：[subject:{},unit:{},type:{}]",subject,unit,type.name());
        StudentPO studentPO = this.chickUserLogin(loginToken, properties.getLoginLiveTime().getSeconds());
        BaseExaminationPaperDTO paper = unitTestService.testPaperQueryRequest(subject, unit, type, studentPO);
        return new BaseResponseDTO(ReasonCodeConstants.RETURN_CODE_SUCCESS,"调用成功",paper);
    }

}
