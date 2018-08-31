package com.continuation.manager.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tangxu
 * @date 2018/8/1317:39
 */
@Data
@AllArgsConstructor
public class BaseResponseDTO {

    private String code;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public BaseResponseDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
