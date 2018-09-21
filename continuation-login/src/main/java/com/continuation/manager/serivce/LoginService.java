package com.continuation.manager.serivce;

import com.continuation.manager.config.ApplicationProperties;
import com.continuation.manager.constants.ReasonCodeConstants;
import com.continuation.manager.domain.dto.BaseResponseDTO;
import com.continuation.manager.domain.dto.LoginRequestDTO;
import com.continuation.manager.domain.dto.LoginResponseDTO;
import com.continuation.manager.domain.dto.VerifyCode;
import com.continuation.manager.domain.po.mysql.StudentPO;
import com.continuation.manager.domain.po.mysql.StudentRepository;
import com.continuation.manager.domain.po.mysql.TeacherPO;
import com.continuation.manager.domain.po.mysql.TeacherRepository;
import com.continuation.manager.exception.InitVerifyCodeException;
import com.continuation.manager.exception.LoginTimeOutException;
import com.continuation.manager.exception.RequestParamException;
import com.continuation.manager.exception.UserNotFoundException;
import com.continuation.manager.service.RedisService;
import com.continuation.manager.utils.ContinuationUtil;
import com.continuation.manager.utils.VerifyCodeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import static com.continuation.manager.enums.UserTypeEnum.STUDENT;
import static com.continuation.manager.enums.UserTypeEnum.TEACHER;
import static com.continuation.manager.service.RedisService.TOKEN_HEADER;
import static com.continuation.manager.service.RedisService.VERIFY_HEADER;

/**
 * @author tangxu
 * @date 2018/8/1317:36
 */
@Slf4j
@Service
@AllArgsConstructor
public class LoginService {

    private final RedisService redisService;
    private final ApplicationProperties properties;

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    /**
     * 初始化登陆图片验证码
     *
     * @param username 登陆用户名
     * @return 响应对象
     */
    public BaseResponseDTO initVerificationCode(String username) throws InitVerifyCodeException {
        Assert.hasText(username, "用户名不能为空");
        VerifyCode randomCode = VerifyCodeUtil.getRandomCode(100, 30);
        redisService.set(VERIFY_HEADER.concat(username), randomCode, properties.getVerCodeLiveTime().toSeconds());
//        randomCode.setCode(null);
        return new BaseResponseDTO(ReasonCodeConstants.RETURN_CODE_SUCCESS, "调用成功", randomCode);
    }

    /**
     * 用户登录
     *
     * @param dto        登录信息
     * @param loginToken 登录令牌
     * @return 响应
     */
    public BaseResponseDTO login(LoginRequestDTO dto, String loginToken) throws Exception {
        Assert.hasText(dto.getVerCode(), "登陆验证码不能为空");
        Assert.hasText(dto.getType(), "用户类型不能为空");
        // 1.校验图片验证码
        VerifyCode verifyCode = redisService.get(VERIFY_HEADER.concat(dto.getUsername()), VerifyCode.class, false);
        if (null == verifyCode) {
            throw new RequestParamException("请先初始化登录后，再次登录");
        }
        if (!StringUtils.equals(dto.getVerCode().toUpperCase(), verifyCode.getCode())) {
            log.error("图片验证码校验失败：[username:{},verCode:{},verifyCode:{}]", dto.getUsername(),dto.getVerCode(),verifyCode);
            throw new RequestParamException("图片验证码校验失败");
        }
        if (StringUtils.isBlank(loginToken)) {
            log.debug("登陆令牌不为空，免密登录");
            if (StringUtils.equals(dto.getType(), STUDENT.getType())) {
                return secretLanding(loginToken, StudentPO.class);
            } else if (StringUtils.equals(dto.getType(), TEACHER.getType())) {
                return secretLanding(loginToken, TeacherPO.class);
            } else {
                log.error("未知的用户类型：{}", dto.getType());
                throw new RequestParamException("用户类型未知");
            }
        } else {
            log.debug("登陆令牌为空，使用用户名密码登陆");
            Assert.hasText(dto.getUsername(), "用户名不能为空");
            Assert.hasText(dto.getPassword(), "用户密码不能为空");
            Object result;
            if (StringUtils.equals(dto.getType(), STUDENT.getType())) {
                result = studentRepository.findFirstByStudentNumberAndPasswordAndVoidedFalse(dto.getUsername(), dto.getPassword())
                        .orElseThrow(() -> new UserNotFoundException("用户名或密码错误"));
            } else if (StringUtils.equals(dto.getType(), TEACHER.getType())) {
                result = teacherRepository.findFirstByWorkNumberAndPasswordAndVoidedFalse(dto.getUsername(), dto.getPassword())
                        .orElseThrow(() -> new UserNotFoundException("用户名或密码错误"));
            } else {
                log.error("未知的用户类型：{}", dto.getType());
                throw new RequestParamException("用户类型未知");
            }
            String uuid = ContinuationUtil.getUUID();
            redisService.set(TOKEN_HEADER.concat(uuid), result, properties.getLoginLiveTime().toSeconds());
            log.info("用户登录成功：[uuid:{},result:{}]", uuid, result.toString());
            return new BaseResponseDTO(ReasonCodeConstants.RETURN_CODE_SUCCESS, "登陆成功", loginResponse(result, uuid));
        }
    }

    private <T> LoginResponseDTO loginResponse(T user, String loginToken) throws Exception {
        if (user instanceof StudentPO){
            return new LoginResponseDTO((StudentPO)user,loginToken);
        }else if(user instanceof TeacherPO){
            return new LoginResponseDTO((TeacherPO)user,loginToken);
        }else{
            log.error("数据错误，未知的登陆对象");
            throw new Exception("数据错误，未知的登陆对象");
        }
    }

    private <T> BaseResponseDTO secretLanding(String loginToken, Class<T> clazz) throws Exception {
        T result = redisService.getSet(TOKEN_HEADER.concat(loginToken), properties.getLoginLiveTime().toSeconds(), clazz);
        if (result == null) {
            throw new LoginTimeOutException("登陆超时，请重新登陆");
        }
        return new BaseResponseDTO(ReasonCodeConstants.RETURN_CODE_SUCCESS, "登陆成功", loginResponse(result, loginToken));
    }
}
