package com.continuation.manager.web;

import com.continuation.manager.domain.po.mysql.StudentPO;
import com.continuation.manager.exception.LoginTimeOutException;
import com.continuation.manager.service.RedisService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.continuation.manager.service.RedisService.TOKEN_HEADER;

/**
 * @author tangxu
 * @date 2018/8/159:52
 */
@Slf4j
@AllArgsConstructor
public class BaseController {

    private final RedisService redisService;

    StudentPO chickUserLogin(String loginToken, long timeLive) throws LoginTimeOutException {
        StudentPO studentPO = redisService.getSet(TOKEN_HEADER.concat(loginToken),timeLive, StudentPO.class);
        if( null == studentPO ){
            throw new LoginTimeOutException("用户登录超时，清重新登录");
        }
        return studentPO;
    }

}
