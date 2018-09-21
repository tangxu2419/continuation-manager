package com.continuation.manager.domain.dto;

import lombok.Data;

/**
 * @author tangxu
 * @date 2018/8/1317:14
 */
@Data
public class LoginRequestDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String verCode;

    /**
     * 用户类型
     */
    private String type;

}
