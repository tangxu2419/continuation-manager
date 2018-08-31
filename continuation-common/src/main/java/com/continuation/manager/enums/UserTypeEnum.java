package com.continuation.manager.enums;

/**
 * @author tangxu
 * @date 2018/8/149:07
 */
public enum UserTypeEnum {
    /**
     * 学生
     */
    STUDENT("S"),
    /**
     * 教师
     */
    TEACHER("T"),
    /**
     * 管理员
     */
    ADMIN("A");
    String type;

    UserTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
