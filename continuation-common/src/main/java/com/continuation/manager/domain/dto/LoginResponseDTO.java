package com.continuation.manager.domain.dto;

import com.continuation.manager.domain.po.mysql.StudentPO;
import com.continuation.manager.domain.po.mysql.TeacherPO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.invoke.ParameterMappingException;

import static com.continuation.manager.enums.UserTypeEnum.STUDENT;
import static com.continuation.manager.enums.UserTypeEnum.TEACHER;

/**
 * @author tangxu
 * @date 2018/8/149:42
 */
@Slf4j
@Data
public class LoginResponseDTO {

    /**
     * 用户登陆令牌
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;
    /**
     * 用户名
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String username;
    /**
     * 用户类型
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userType;
    /**
     * 工号
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userCode;

    public LoginResponseDTO(StudentPO studentPO, String token) {
        this.token = token;
        this.username = studentPO.getName();
        this.userType = STUDENT.getType();
        this.userCode = studentPO.getStudentNumber();
    }

    public LoginResponseDTO(TeacherPO teacherPO, String token) {
        this.token = token;
        this.username = teacherPO.getName();
        this.userType = TEACHER.getType();
        this.userCode = teacherPO.getWorkNumber();
    }


}
