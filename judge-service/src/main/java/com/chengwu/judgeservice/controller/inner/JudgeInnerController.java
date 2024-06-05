package com.chengwu.judgeservice.controller.inner;

import chengwu.model.model.entity.QuestionSubmit;
import com.chengwu.judgeservice.judge.JudgeService;
import com.chengwu.serviceclient.service.JudgeFeignClient;
import feign.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("/inner")
public class JudgeInnerController implements JudgeFeignClient {
    @Resource
    private JudgeService judgeService;
    /**
     * 执行判题
     * @param questionSubmitId
     * @return
     */
    @GetMapping("/do")
    @Override
    public QuestionSubmit doJudge(@Param("questionSubmitId") long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    }
}
