package com.continuation.manager.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tangxu
 * @Title: 试卷对象
 * @date 2018/8/159:20
 */
@Data
public class BaseExaminationPaperDTO implements Serializable {

    /**
     * 试卷流水号
     */
    protected String transactionId;

    /**
     * 答题人ID
     */
    protected String answerId;

    /**
     * 总分值
     */
    protected Integer totalScore;

    /**
     * 得分
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Integer score;

    /**
     * 总提数
     */
    protected Integer titleNumber;

    /**
     * 答题数
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Integer answerNumber;

    /**
     * 测试时间:单位/秒
     */
    protected Long testTime;


}
