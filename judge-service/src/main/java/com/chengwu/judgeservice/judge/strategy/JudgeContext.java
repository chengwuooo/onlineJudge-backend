package com.chengwu.judgeservice.judge.strategy;


import chengwu.model.model.codeSandbox.ExecuteCodeResponse;
import chengwu.model.model.entity.Question;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JudgeContext {
    private Question question;
    private ExecuteCodeResponse executeCodeResponse;
}
