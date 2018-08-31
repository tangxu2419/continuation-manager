package com.continuation.manager.web;

import com.continuation.manager.constants.ReasonCodeConstants;
import com.continuation.manager.domain.dto.BaseResponseDTO;
import com.continuation.manager.exception.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author tangxu
 * @date 2018/8/1320:06
 */
@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class ExceptionTranslator {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponseDTO handleBadRequestError(IllegalArgumentException ex) {
        log.error("请求体参数有误。{}", ex.getMessage());
        return new BaseResponseDTO(ReasonCodeConstants.RETURN_CODE_FAIL,ex.getMessage());
    }

    @ExceptionHandler(InitVerifyCodeException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public BaseResponseDTO initVerifyCodeException(InitVerifyCodeException ex) {
        log.error("初始化图片验证码失败：{}", ex.getMessage());
        return new BaseResponseDTO(ReasonCodeConstants.RETURN_CODE_FAIL,ex.getMessage());
    }

    @ExceptionHandler(RequestParamException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponseDTO initVerifyCodeException(RequestParamException ex) {
        log.error("请求参数错误：{}", ex.getMessage());
        return new BaseResponseDTO(ReasonCodeConstants.RETURN_CODE_FAIL,ex.getMessage());
    }

    @ExceptionHandler(LoginTimeOutException.class)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public BaseResponseDTO initVerifyCodeException(LoginTimeOutException ex) {
        log.error("用户登陆超时：{}", ex.getMessage());
        return new BaseResponseDTO(ReasonCodeConstants.LOGIN_TIME_OUT,ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public BaseResponseDTO initVerifyCodeException(UserNotFoundException ex) {
        log.error("用户未找到：{}", ex.getMessage());
        return new BaseResponseDTO(ReasonCodeConstants.RETURN_CODE_FAIL,ex.getMessage());
    }

    @ExceptionHandler(QuestionBankEmptyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public BaseResponseDTO initVerifyCodeException(QuestionBankEmptyException ex) {
        log.error("题库为空：{}", ex.getMessage());
        return new BaseResponseDTO(ReasonCodeConstants.RETURN_CODE_FAIL,ex.getMessage());
    }

}
