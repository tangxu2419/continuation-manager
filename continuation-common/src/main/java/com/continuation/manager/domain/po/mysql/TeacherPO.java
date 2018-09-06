package com.continuation.manager.domain.po.mysql;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author tangxu
 * @date 2018/8/1316:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@DynamicUpdate
@Entity
@Table(name = "t_teacher_info")
@ConditionalOnBean(DataSourceProperties.class)
public class TeacherPO extends BaseEntity {

    /**
     * 工号
     */
    @Indexed
    @Column(length = 20)
    private String workNumber;

    /**
     * 密码
     */
    @Column(length = 20)
    private String password;

    /**
     * 级别
     */
    @Column(length = 10)
    private String level;

    /**
     * 科目
     */
    @Column(length = 10)
    private String subject;

    /**
     * 姓名
     */
    @Column(length = 20)
    private String name;

    /**
     * 性别
     */
    @Column(length = 1)
    private String sex;

    /**
     * 身份证号
     */
    @Column(length = 20)
    private String identityNumber;

    /**
     * 家庭电话
     */
    @Column(length = 20)
    private String homePhone;

    /**
     * 手机号码
     */
    @Column(length = 20)
    private String telephone;

    /**
     * 家庭住址
     */
    @Column(length = 128)
    private String homeAddress;

    /**
     * 联系人姓名
     */
    @Column(length = 20)
    private String contactName;

    /**
     * 联系人手机号
     */
    @Column(length = 20)
    private String contactTelephone;

    /**
     * 联系人关系
     */
    @Column(length = 10)
    private String contactRelationship;

    /**
     * 逻辑删除字段
     */
    @Column(length = 1)
    private boolean voided;


}
