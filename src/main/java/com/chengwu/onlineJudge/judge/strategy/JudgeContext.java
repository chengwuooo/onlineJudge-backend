package com.chengwu.onlineJudge.judge.strategy;

import com.chengwu.onlineJudge.judge.codeSandbox.model.ExecuteCodeResponse;
import com.chengwu.onlineJudge.model.dto.question.JudgeCase;
import com.chengwu.onlineJudge.model.dto.question.JudgeConfig;
import com.chengwu.onlineJudge.model.entity.Question;
import com.chengwu.onlineJudge.model.vo.JudgeInfo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JudgeContext {
    private Question question;
    private ExecuteCodeResponse executeCodeResponse;
}
