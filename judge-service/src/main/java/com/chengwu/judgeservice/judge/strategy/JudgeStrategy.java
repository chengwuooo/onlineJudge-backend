package com.chengwu.judgeservice.judge.strategy;


import chengwu.model.model.codeSandbox.JudgeInfo;

public interface JudgeStrategy {
    JudgeInfo doJudge(JudgeContext judgeContext);
}
