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
}
