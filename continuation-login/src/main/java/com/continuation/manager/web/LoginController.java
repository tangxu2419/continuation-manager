package com.continuation.manager.web;

import com.continuation.manager.domain.dto.BaseResponseDTO;
import com.continuation.manager.domain.dto.LoginRequestDTO;
import com.continuation.manager.serivce.LoginService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author tangxu
 * @date 2018/8/1317:06
 */
@Slf4j
@RestController
@RequestMapping("/continuation/login")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/init")
    public ResponseEntity<?> initVerificationCode(@RequestParam String username) throws Exception {
        log.info("收到用户登录初始化请求：{}", username);
        BaseResponseDTO responseDTO = loginService.initVerificationCode(username);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/")
    public ResponseEntity<?> login(
            @RequestHeader(value = "login_token") String loginToken,
            @RequestBody LoginRequestDTO dto
    ) throws Exception {
        log.info("收到用户登录请求：{}", dto.toString());
        BaseResponseDTO responseDTO = loginService.login(dto, loginToken);
        return ResponseEntity.ok(responseDTO);
    }

}
