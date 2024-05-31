package com.chengwu.onlineJudge.judge.strategy;

import com.chengwu.onlineJudge.judge.codeSandbox.model.JudgeInfo;

public interface JudgeStrategy {
    JudgeInfo doJudge(JudgeContext judgeContext);
}
