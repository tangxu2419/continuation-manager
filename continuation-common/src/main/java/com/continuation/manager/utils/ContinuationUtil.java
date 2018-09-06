package com.continuation.manager.utils;

import java.util.UUID;

/**
 * @author tangxu
 * @date 2018/8/1410:14
 */
public class ContinuationUtil {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    public static String autoGenericCode(String code, int num) {
        return String.format("%0" + num + "d", Integer.parseInt(code) + 1);
    }
}
