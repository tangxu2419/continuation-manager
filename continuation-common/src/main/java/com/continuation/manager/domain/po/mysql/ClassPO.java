package com.continuation.manager.domain.po.mysql;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author tangxu
 * @date 2018/8/1315:43
 */
@Data
@Entity
@Table(name = "t_class_info")
@ConditionalOnBean(DataSourceProperties.class)
public class ClassPO extends BaseEntity {


    /**
     * 班主任-Id
     */
    @Column(length = 20)
    private String headmasterId;

    /**
     * 班主任-Id
     */
    @Column(length = 10)
    private String schoolYear;

    /**
     * 年级
     */
    @Column(length = 10)
    private String grade;

    /**
     * 班级类型
     */
    @Column(length = 10)
    private String classType;

    /**
     * 主科目
     */
    @Column(length = 10)
    private String subject;

    /**
     * 班级总人数
     */
    @Column(length = 3)
    private Integer totalNumber;

    /**
     * 班级状态
     */
    @Column(length = 4)
    private String type;

    /**
     * 逻辑删除字段
     */
    @Column(length = 1)
    private boolean voided;

}
