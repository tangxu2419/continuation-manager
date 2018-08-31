package com.continuation.manager.domain.dto;

import com.continuation.manager.domain.po.mongo.SingleChoiceQuestionPO;
import com.continuation.manager.enums.ChoiceAnswerEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tangxu
 * @Title: 单项选择试题
 * @date 2018/8/159:11
 */
@Data
public class SingleChoiceQuestionDTO implements Serializable {

    private String id;

    /**
     *  问题详情
     */
    private ChoiceQuestionDTO question;

    /**
     *  答案
     */
    private ChoiceAnswerEnum answer;

    /**
     *  答案明细
     */
    private String answerDesc;

    /**
     *  分值
     */
    private Integer score;


    public SingleChoiceQuestionDTO(SingleChoiceQuestionPO questionPO) {
        this.id = questionPO.getId();
        this.question = questionPO.getQuestion();
        this.answer = questionPO.getAnswer();
        this.answerDesc = questionPO.getAnswerDesc();
        this.score = questionPO.getScore();
    }
}
