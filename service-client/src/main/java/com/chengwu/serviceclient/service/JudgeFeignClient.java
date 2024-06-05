package com.chengwu.serviceclient.service;


import chengwu.model.model.entity.QuestionSubmit;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 判题服务接口
 */
@FeignClient(name = "judge-service", path = "/api/judge/inner")
public interface JudgeFeignClient {
    /**
     * 执行判题
     *
     * @param questionSubmitId
     * @return
     */
    @GetMapping("/do")
    QuestionSubmit doJudge(@Param("questionSubmitId") long questionSubmitId);
}
