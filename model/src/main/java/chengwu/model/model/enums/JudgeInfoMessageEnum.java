package chengwu.model.model.enums;

public enum JudgeInfoMessageEnum {
    WAITING("等待中", "Waiting"),
    RUNNING("运行中", "Running"),
    ACCEPTED("成功", "Accepted"),
    WRONG_ANSWER("答案错误", "Wrong Answer"),
    TIME_LIMIT_EXCEEDED("超时", "Time Limit Exceeded"),
    MEMORY_LIMIT_EXCEEDED("内存超限", "Memory Limit Exceeded"),
    RUNTIME_ERROR("运行时错误", "Runtime Error"),
    COMPILE_ERROR("编译错误", "Compile Error"),
    SYSTEM_ERROR("系统错误", "System Error"),
    OUTPUT_LIMIT_EXCEEDED("输出超限", "Output Limit Exceeded"),
    PRESENTATION_ERROR("格式错误", "Presentation Error"),
    UNKNOWN_ERROR("未知错误", "Unknown Error")
    ;
    final String text;
    final String value;

    JudgeInfoMessageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public static JudgeInfoMessageEnum getEnumByValue(String language) {
        for (JudgeInfoMessageEnum value : JudgeInfoMessageEnum.values()) {
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
