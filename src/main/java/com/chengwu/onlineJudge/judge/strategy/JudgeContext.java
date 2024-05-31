package com.chengwu.onlineJudge.judge.strategy;

import com.chengwu.onlineJudge.judge.codeSandbox.model.ExecuteCodeResponse;
import com.chengwu.onlineJudge.model.entity.Question;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JudgeContext {
    private Question question;
    private ExecuteCodeResponse executeCodeResponse;
}
