package com.chengwu.onlineJudge.model.enums;

import lombok.Data;

public enum QuestionSubmitLanguageEnum {
    JAVA("Java", "java"),
    PYTHON("Python", "python"),
    GOLANG("Golang", "golang"),
    CPP("C++", "cpp"),
    C("C", "c"),
    JS("JavaScript", "js"),
    PHP("PHP", "php"),
    ;
    final String text;
    final String value;

    QuestionSubmitLanguageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public static QuestionSubmitLanguageEnum getEnumByValue(String language) {
        for (QuestionSubmitLanguageEnum value : QuestionSubmitLanguageEnum.values()) {
            if (value.getValue().equals(language)) {
                return value;
            }
        }
        return null;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }
}
