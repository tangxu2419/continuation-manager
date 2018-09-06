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
 * @date 2018/8/1315:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@DynamicUpdate
@Entity
@Table(name = "t_student_info")
@ConditionalOnBean(DataSourceProperties.class)
public class StudentPO extends BaseEntity {

    /**
     * 学号
     */
    @Indexed
    @Column(length = 20)
    private String studentNumber;

    /**
     * 密码
     */
    @Column(length = 32)
    private String password;

    /**
     * 班级主键
     */
    @Column(length = 20)
    private String classId;

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
     * 家庭联系电话
     */
    @Column(length = 20)
    private String contactNumber;

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
     * 联系人关系
     */
    @Column(length = 10)
    private String contactRelationship;

    /**
     * 联系人手机号
     */
    @Column(length = 20)
    private String contactTelephone;

    /**
     * 备用联系人姓名
     */
    @Column(length = 20)
    private String standbyContactName;

    /**
     * 备用联系人关系
     */
    @Column(length = 10)
    private String standbyContactRelationship;

    /**
     * 备用联系人手机号
     */
    @Column(length = 20)
    private String standbyContactTelephone;

    /**
     * 逻辑删除字段
     */
    @Column(length = 1)
    private boolean voided;

}
