package com.chengwu.onlineJudge.controller;

import com.chengwu.onlineJudge.common.BaseResponse;
import com.chengwu.onlineJudge.common.ErrorCode;
import com.chengwu.onlineJudge.common.ResultUtils;
import com.chengwu.onlineJudge.exception.BusinessException;
import com.chengwu.onlineJudge.model.dto.postthumb.QuestionsubmitAddRequest;
import com.chengwu.onlineJudge.model.entity.User;
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
    private QuestionsubmitService questionsubmitService;
    @Resource
    private UserService userService;

    @PostMapping("/")
    public BaseResponse<Integer> doThumb(@RequestBody QuestionsubmitAddRequest questionsubmitAddRequest,
            HttpServletRequest request) {
        if (questionsubmitAddRequest == null || questionsubmitAddRequest.getPostId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
        long postId = questionsubmitAddRequest.getPostId();
        int result = questionsubmitService.doQuestionsubmit(postId, loginUser);
        return ResultUtils.success(result);
    }

}
