package com.continuation.manager.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author tangxu
 * @Title: 选择问题详情
 * @date 2018/8/1417:45
 */
@Data
public class ChoiceQuestionDTO implements Serializable {

    /**
     * 问题
     */
    private String title;
    /**
     * 选项
     */
    private List<String> options;
}
