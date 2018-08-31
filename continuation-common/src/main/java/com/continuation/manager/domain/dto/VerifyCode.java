package com.continuation.manager.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 验证码信息
 * @author tangxu
 * @date 2018年3月26日
 */
@Data
@AllArgsConstructor
public class VerifyCode implements Serializable {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String code;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String imageBase64;

}
