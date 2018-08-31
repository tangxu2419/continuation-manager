package com.continuation.manager.domain.po.mongo;

import com.continuation.manager.domain.dto.ChoiceQuestionDTO;
import com.continuation.manager.enums.ChoiceAnswerEnum;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Duration;
import java.util.Date;

/**
 * @author tangxu
 * @Title: 单项选择题
 * @date 2018/8/1417:38
 */
@Data
@Document(collection = "single_choice_question")
public class SingleChoiceQuestionPO implements Serializable {

    /**
     *  主键
     */
    @Id
    private String id;

    /**
     * 出题人ID
     */
    private String subjectPersonId;

    /**
     * 出题人
     */
    private String subjectPerson;

    /**
     *  科目
     */
    @Indexed
    private String subject;

    /**
     *  单元
     */
    @Indexed
    private String unit;

    /**
     * 问题级别
     */
    private Integer level;

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

    /**
     * 答题时间
     */
    private Duration answerTime;

    /**
     *  记录已失效标志位
     */
    private boolean voided;

    @CreatedDate
    @Indexed(direction = IndexDirection.DESCENDING)
    private Date createdDate;
    @LastModifiedDate
    private Date updatedDate;

}
