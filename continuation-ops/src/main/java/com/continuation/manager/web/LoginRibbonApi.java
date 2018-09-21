package com.continuation.manager.web;

import com.continuation.manager.config.ApplicationProperties;
import com.continuation.manager.domain.dto.LoginRequestDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author tangxu
 * @date 2018/9/616:52
 */
@Slf4j
@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginRibbonApi {

    private final ApplicationProperties properties;
    private final RestTemplate restTemplate;


    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity login(
            @RequestHeader(value = "login_token") String loginToken,
            @RequestBody LoginRequestDTO dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("login_token", loginToken);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        dto.setType("T");
        HttpEntity<Object> requestEntity = new HttpEntity<>(dto, headers);
        return restTemplate.exchange(properties.getLoginUrl(), HttpMethod.POST, requestEntity, Object.class);
    }

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public ResponseEntity initLogin(@Param(value = "username") String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<>("username=".concat(username), headers);
        return restTemplate.exchange(properties.getLoginInitUrl(), HttpMethod.POST, requestEntity, Object.class);
    }


}
