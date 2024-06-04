package com.chengwu.onlineJudge.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.chengwu.onlineJudge.judge.codeSandbox.model.ExecuteCodeResponse;
import com.chengwu.onlineJudge.model.dto.question.JudgeCase;
import com.chengwu.onlineJudge.model.dto.question.JudgeConfig;
import com.chengwu.onlineJudge.model.entity.Question;
import com.chengwu.onlineJudge.model.enums.JudgeInfoMessageEnum;
import com.chengwu.onlineJudge.judge.codeSandbox.model.JudgeInfo;
import com.chengwu.onlineJudge.service.QuestionService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultJudgeStrategy implements JudgeStrategy {
    /**
     * 执行判题逻辑。
     *
     * @param judgeContext 包含题目和执行代码后的响应信息的上下文对象
     * @return JudgeInfo 包含判题结果信息的对象，如判题状态、耗时、内存使用情况等
     */
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        // 获取题目和执行代码后的响应信息
        Question question = judgeContext.getQuestion();
        ExecuteCodeResponse executeCodeResponse = judgeContext.getExecuteCodeResponse();
        JudgeInfo judgeInfoResult = new JudgeInfo();

        // 初始化判题信息
        JudgeInfoMessageEnum judgeInfoMessage;

        //编译错误
        if (Objects.equals(executeCodeResponse.getMessage(), "编译错误")){
            judgeInfoMessage = JudgeInfoMessageEnum.COMPILE_ERROR;
            judgeInfoResult.setMessage(judgeInfoMessage.getText());
            return judgeInfoResult;
        }

        // 获取执行时间和内存信息
        JudgeInfo judgeInfo = executeCodeResponse.getJudgeInfo();
        Long time = judgeInfo.getTime();
        Long memory = judgeInfo.getMemory();
        // 若无执行时间或内存信息，则判为系统错误
        if (time == null || memory == null || time <= 0 || memory <= 0) {
            judgeInfoMessage = JudgeInfoMessageEnum.SYSTEM_ERROR;
            judgeInfoResult.setMessage(judgeInfoMessage.getText());
            return judgeInfoResult;
        }

        // 解析题目判题配置
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        // 判断执行时间是否超过限制
        Long timeLimit = judgeConfig.getTimeLimit();
        if (time > timeLimit) {
            judgeInfoMessage = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResult.setMessage(judgeInfoMessage.getText());
            return judgeInfoResult;
        }
        // 判断内存使用是否超过限制
        Long memoryLimit = judgeConfig.getMemoryLimit();
        if (memory > memoryLimit * 1024 * 1024) {
            judgeInfoMessage = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResult.setMessage(judgeInfoMessage.getText());
            return judgeInfoResult;
        }

        // 获取执行输出和题目期望输出
        List<String> executeOutput = executeCodeResponse.getExecuteOutput();
        List<String> questionOutput = JSONUtil.toList(question.getJudgeCase(), JudgeCase.class).stream().map(JudgeCase::getOutput).collect(Collectors.toList());
        // 检查输出行数是否一致，不一致则判为错误答案
        if (executeOutput.size() != questionOutput.size()) {
            judgeInfoMessage = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResult.setMessage(judgeInfoMessage.getText());
            return judgeInfoResult;
        }
        // 逐行比较执行输出和期望输出，不一致则判为错误答案
        for (int i = 0; i < executeOutput.size(); i++) {
            //如果答案最后是回车换行符，则去除
            if (executeOutput.get(i).endsWith("\n")) {
                executeOutput.set(i, executeOutput.get(i).substring(0, executeOutput.get(i).length() - 1));
            }
            if (!executeOutput.get(i).equals(questionOutput.get(i))) {
                judgeInfoMessage = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResult.setMessage(judgeInfoMessage.getText());
                return judgeInfoResult;
            }
        }

        // 设置判题结果为接受（ACCEPTED），并记录执行时间和内存使用
        judgeInfoMessage = JudgeInfoMessageEnum.ACCEPTED;
        judgeInfoResult.setTime(time);
        judgeInfoResult.setMemory(memory);
        judgeInfoResult.setMessage(judgeInfoMessage.getText());
        return judgeInfoResult;
    }
}
