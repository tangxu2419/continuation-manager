package com.continuation.manager.domain.dto;

import com.continuation.manager.domain.po.mongo.SingleChoiceQuestionPO;
import com.continuation.manager.domain.po.mysql.StudentPO;
import com.continuation.manager.utils.ContinuationUtil;
import lombok.Data;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tangxu
 * @Title: 单项选择试卷类型
 * @date 2018/8/1420:44
 */
@Data
public class SingleChoicePaperDTO extends BaseExaminationPaperDTO {

    /**
     * 试卷内容
     */
    private List<SingleChoiceQuestionDTO> questions;

    public SingleChoicePaperDTO(List<SingleChoiceQuestionPO> list, StudentPO student) {
        this.transactionId = ContinuationUtil.getUUID();
        this.answerId = student.getStudentNumber();
        this.titleNumber = list.size();
        ArrayList<SingleChoiceQuestionDTO> newList = new ArrayList<>();
        int countScore = 0;
        Duration countTime = Duration.ofSeconds(0);
        for (SingleChoiceQuestionPO question : list) {
            countScore += question.getScore();
            countTime = countTime.plus(question.getAnswerTime());
            newList.add(new SingleChoiceQuestionDTO(question));
        }
        this.totalScore = countScore;
        this.questions = newList;
        this.testTime = countTime.getSeconds();
    }

}
