package com.chengwu.judgeservice.judge;


import chengwu.model.model.entity.QuestionSubmit;

public interface JudgeService {
    public QuestionSubmit doJudge(long questionId);
}
