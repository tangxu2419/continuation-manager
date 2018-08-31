package com.continuation.manager.service;

import com.continuation.manager.config.ApplicationProperties;
import com.continuation.manager.domain.dto.BaseExaminationPaperDTO;
import com.continuation.manager.domain.dto.SingleChoicePaperDTO;
import com.continuation.manager.domain.po.mongo.SingleChoiceQuestionRepository;
import com.continuation.manager.domain.po.mysql.StudentPO;
import com.continuation.manager.enums.QuestionTypeEnum;
import com.continuation.manager.exception.QuestionBankEmptyException;
import com.continuation.manager.exception.RequestParamException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static com.continuation.manager.service.RedisService.TEST_PAPER_HEADER;


/**
 * @author tangxu
 * @Title: 单元测试相关
 * @date 2018/8/1420:02
 */
@Slf4j
@Service
@AllArgsConstructor
public class UnitTestService {

    private final ApplicationProperties properties;

    private final RedisService redisService;

    private final SingleChoiceQuestionRepository singleChoiceQuestionRepository;

    /**
     * 获取单元测试试卷
     *
     * @param subject 科目
     * @param unit    单元
     * @param type    考试类型
     * @param student 考生
     */
    public BaseExaminationPaperDTO testPaperQueryRequest(String subject, String unit, QuestionTypeEnum type, StudentPO student) throws Exception {
        Assert.notNull(type, "题型不能为空");
        Assert.hasText(subject, "科目不能为空");
        Assert.hasText(unit, "测试单元不能为空");
        switch (type) {
            case SINGLE_CHOICE:
                return generateSingleChoiceExaminationPaper(subject, unit, student);
            default:
                throw new RequestParamException("未知的题型");
        }
    }

    /**
     * 生成单选测试题
     *
     * @param subject 科目
     * @param unit    单元
     * @param student 考生
     */
    private SingleChoicePaperDTO generateSingleChoiceExaminationPaper(String subject, String unit, StudentPO student) throws QuestionBankEmptyException {
        // 1、从缓存中获取所有符合条件的测试题
        List list = redisService.get(TEST_PAPER_HEADER.concat(subject).concat(unit), ArrayList.class, true);
        if (null == list || list.isEmpty()) {
            list = singleChoiceQuestionRepository.findAllBySubjectAndUnitAndVoidedFalse(subject, unit);
            if (list.isEmpty()) {
                throw new QuestionBankEmptyException("当前测试单元，题库为空");

            }
            redisService.set(TEST_PAPER_HEADER.concat(subject).concat(unit), list, properties.getQuestionLiveTime().getSeconds());
        }
        // 2、随机抽取*条试题
        List randomList = this.getRandomList(list, properties.getSingleChoiceTestNumber());
        // 3、封装响应参数
        return new SingleChoicePaperDTO(randomList, student);
    }


    /**
     * 从list中随机抽取若干不重复元素
     *
     * @param list:被抽取list
     * @param count:抽取元素的个数
     */
    private List getRandomList(List list, int count) {
        List<Integer> tempList = new ArrayList<>();
        List<Object> listNew = new ArrayList<>();
        if (list.size() <= count) {
            return list;
        } else {
            while (tempList.size() < count) {
                int random = (int) (Math.random() * list.size());
                if (!tempList.contains(random)) {
                    tempList.add(random);
                    listNew.add(list.get(random));
                }
            }
            return listNew;
        }
    }

}
