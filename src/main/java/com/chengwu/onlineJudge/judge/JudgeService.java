package com.chengwu.onlineJudge.judge;

import com.chengwu.onlineJudge.model.entity.QuestionSubmit;

public interface JudgeService {
    public QuestionSubmit doJudge(long questionId);

}
