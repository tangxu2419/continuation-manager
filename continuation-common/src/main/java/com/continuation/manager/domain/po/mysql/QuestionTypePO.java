package com.continuation.manager.domain.po.mysql;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author tangxu
 * @Title: 题目类型
 * @date 2018/8/1420:09
 */
@Data
@Entity
@Table(name = "t_question_type")
@ConditionalOnBean(DataSourceProperties.class)
public class QuestionTypePO extends BaseEntity{


    /**
     * 题型
     */
    @Column(length = 20)
    private String typeCode;

    /**
     * 题型名称
     */
    @Column(length = 20)
    private String typeName;

    /**
     * 逻辑删除字段
     */
    @Column(length = 1)
    private boolean voided;

}
