package com.chengwu.onlineJudge.controller;

import com.chengwu.onlineJudge.common.BaseResponse;
import com.chengwu.onlineJudge.common.ErrorCode;
import com.chengwu.onlineJudge.common.ResultUtils;
import com.chengwu.onlineJudge.exception.BusinessException;
import com.chengwu.onlineJudge.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.chengwu.onlineJudge.model.entity.User;
import com.chengwu.onlineJudge.service.QuestionSubmitService;
import com.chengwu.onlineJudge.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestSubmitController {
    @Resource
    private QuestionSubmitService questionsubmitService;

    @Resource
    private UserService userService;

    @PostMapping("/")
    public BaseResponse<Integer> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionsubmitAddRequest,
            HttpServletRequest request) {
        if (questionsubmitAddRequest == null || questionsubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能提交
        final User loginUser = userService.getLoginUser(request);
        long postId = questionsubmitAddRequest.getQuestionId();
        int result = (int) questionsubmitService.doQuestionSubmit(questionsubmitAddRequest, loginUser);
        return ResultUtils.success(result);
    }

}
