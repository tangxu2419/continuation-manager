package com.continuation.manager.utils;

import com.continuation.manager.exception.RequestParamException;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2018/8/2014:30
 */
public class IdCardUtil {

    /**
     * 根据身份证号获取用户性别
     *
     * @param identityCard 身份证号
     * @return 性别
     */
    public static String getSexByIDCard(String identityCard) throws RequestParamException {
        switch (identityCard.length()) {
            case 15:
                if (Integer.parseInt(identityCard.substring(14, 15)) % 2 == 0) {
                    return "女";
                } else {
                    return "男";
                }
            case 18:
                if (Integer.parseInt(identityCard.substring(16).substring(0, 1)) % 2 == 0) {
                    return "女";
                } else {
                    return "男";
                }
            default:
                throw new RequestParamException("身份证号不正确");
        }
    }

    /**
     * 根据身份证号获取用户年龄
     *
     * @param identityCard 身份证号
     * @return 性别
     */
    public static int getAgeByIDCard(String identityCard) throws RequestParamException {
        String year;
        String month;
        switch (identityCard.length()) {
            case 15:
                year = "19" + identityCard.substring(6, 8);
                month = identityCard.substring(8, 10);

                break;
            case 18:
                year = identityCard.substring(6).substring(0, 4);
                month = identityCard.substring(10).substring(0, 2);
                break;
            default:
                throw new RequestParamException("身份证号不正确");
        }
        Date date = new Date();
        int nowYear = DateUtils.toCalendar(date).get(Calendar.YEAR);
        int nowMonth = DateUtils.toCalendar(date).get(Calendar.MONTH) + 1;
        if (nowYear < Integer.parseInt(year)) {
            throw new RequestParamException("身份证识别失败");
        }
        if (Integer.parseInt(month) <= nowMonth) {
            return nowYear - Integer.parseInt(year) + 1;
        } else {
            return nowYear - Integer.parseInt(year);
        }
    }


    /**
     * 根据身份证的号码算出当前身份证持有者的性别和年龄 18位身份证
     *
     * @return 返回用户性别及年龄
     */
    public static Map<String, Object> getCarInfo(String cardCode) throws RequestParamException {
        String sex;
        String year;
        String month;
        switch (cardCode.length()) {
            case 15:
                year = "19" + cardCode.substring(6, 8);
                month = cardCode.substring(8, 10);
                if (Integer.parseInt(cardCode.substring(14, 15)) % 2 == 0) {
                    sex = "女";
                } else {
                    sex = "男";
                }
                break;
            case 18:
                year = cardCode.substring(6).substring(0, 4);
                month = cardCode.substring(10).substring(0, 2);
                if (Integer.parseInt(cardCode.substring(16).substring(0, 1)) % 2 == 0) {
                    sex = "女";
                } else {
                    sex = "男";
                }
                break;
            default:
                throw new RequestParamException("身份证号不正确");
        }
        Date date = new Date();
        int nowYear = DateUtils.toCalendar(date).get(Calendar.YEAR);
        int nowMonth = DateUtils.toCalendar(date).get(Calendar.MONTH) + 1;
        int age;
        if (nowYear < Integer.parseInt(year)) {
            throw new RequestParamException("身份证识别失败");
        }
        if (Integer.parseInt(month) <= nowMonth) {
            age = nowYear - Integer.parseInt(year) + 1;
        } else {
            age = nowYear - Integer.parseInt(year);
        }
        return new HashMap<>(2) {{
            put("sex", sex);
            put("age", age);
        }};
    }

}
