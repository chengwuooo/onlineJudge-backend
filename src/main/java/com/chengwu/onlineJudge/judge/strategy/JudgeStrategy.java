package com.chengwu.onlineJudge.judge.strategy;

import com.chengwu.onlineJudge.model.vo.JudgeInfo;

public interface JudgeStrategy {
    JudgeInfo doJudge(JudgeContext judgeContext);
}
