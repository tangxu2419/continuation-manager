package com.continuation.manager.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2018/9/1410:31
 */
public class FormatUtil {


    /**
     * 对象实例转字符串
     */
    public static String beanToStr(Object param) throws IllegalAccessException, UnsupportedEncodingException {
        Field[] declaredFields = param.getClass().getDeclaredFields();
        Arrays.sort(declaredFields, Comparator.comparing(Field::getName));
        StringBuilder sf = new StringBuilder();
        for (Field field : declaredFields) {
            if (null != field.get(param)) {
                sf.append(field.getName()).append("=").append(URLEncoder.encode(field.get(param).toString(), StandardCharsets.UTF_8.name())).append("&");
            }
        }
        return sf.substring(0, sf.length() - 1);
    }
}
