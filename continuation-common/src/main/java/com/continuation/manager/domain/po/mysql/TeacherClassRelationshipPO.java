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
 * @date 2018/8/1316:53
 */
@Data
@Entity
@Table(name = "t_teacher_class_relationship")
@ConditionalOnBean(DataSourceProperties.class)
public class TeacherClassRelationshipPO extends BaseEntity{


    /**
     * 教师ID
     */
    private String teacherId;

    /**
     * 班级ID
     */
    private String classId;

    /**
     * 逻辑删除字段
     */
    private boolean voided;

}
