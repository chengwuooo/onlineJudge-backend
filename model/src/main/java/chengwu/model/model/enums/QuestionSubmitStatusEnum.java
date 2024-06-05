package chengwu.model.model.enums;

public enum QuestionSubmitStatusEnum {
    WAITING(0, "等待中"),
    RUNNING(1, "运行中"),
    SUCCEED(3, "成功"),
    FAILED(2, "失败")

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
