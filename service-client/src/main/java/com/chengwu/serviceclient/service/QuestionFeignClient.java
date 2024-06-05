package com.chengwu.serviceclient.service;

import chengwu.model.model.dto.question.QuestionQueryRequest;
import chengwu.model.model.entity.Question;

import chengwu.model.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 针对表【question(题目)】的数据库操作Service
 */
@FeignClient(name = "question-service", path = "/api/question/inner")
public interface QuestionFeignClient{

    @GetMapping("/get/id")
    Question getQuestionById(@Param("questionId") long questionId);

    @GetMapping("/question_submit/get/id")
    QuestionSubmit getQuestionSubmitById(@Param("questionSubmitId") long questionSubmitId);

    @PostMapping("/update")
    Boolean updateQuestionById(@RequestBody Question question);

    @PostMapping("/question_submit/update")
    Boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);

}
