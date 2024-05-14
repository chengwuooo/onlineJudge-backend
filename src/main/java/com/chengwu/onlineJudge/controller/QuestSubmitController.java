package com.chengwu.onlineJudge.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengwu.onlineJudge.common.BaseResponse;
import com.chengwu.onlineJudge.common.ErrorCode;
import com.chengwu.onlineJudge.common.ResultUtils;
import com.chengwu.onlineJudge.exception.BusinessException;
import com.chengwu.onlineJudge.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.chengwu.onlineJudge.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.chengwu.onlineJudge.model.entity.QuestionSubmit;
import com.chengwu.onlineJudge.model.entity.User;
import com.chengwu.onlineJudge.model.vo.QuestionSubmitVO;
import com.chengwu.onlineJudge.service.QuestionSubmitService;
import com.chengwu.onlineJudge.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


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
        long result = questionsubmitService.doQuestionSubmit(questionsubmitAddRequest, loginUser);
        return ResultUtils.success(Math.toIntExact(result));
    }

    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        if (questionSubmitQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int pageSize = questionSubmitQueryRequest.getPageSize();
        int pageNum = questionSubmitQueryRequest.getCurrent();
        if (pageSize <= 0 || pageNum <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<QuestionSubmit> questionSubmitPage = questionsubmitService.page(new Page<>(pageNum, pageSize),
                questionsubmitService.getQueryWrapper(questionSubmitQueryRequest));

        Page<QuestionSubmitVO> questionSubmitVOPage = questionsubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser);
        return ResultUtils.success(questionSubmitVOPage);
    }
}
