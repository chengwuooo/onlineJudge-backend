package com.chengwu.onlineJudge.model.enums;

import com.chengwu.onlineJudge.model.entity.QuestionSubmit;

public enum QuestionSubmitStatusEnum {
    WAITING(0, "等待中"),
    SUCCESS(1, "成功"),
    FAILED(2, "失败"),
    RUNNING(3, "运行中");

    ;
    private final int value;
    private final String text;


    QuestionSubmitStatusEnum(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public static QuestionSubmitStatusEnum getEnumByValue(Integer status) {
        for (QuestionSubmitStatusEnum questionSubmitStatusEnum : QuestionSubmitStatusEnum.values()) {
            if (questionSubmitStatusEnum.getValue() == status) {
                return questionSubmitStatusEnum;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
