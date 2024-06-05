package com.chengwu.judgeservice.judge.impl;

import chengwu.model.model.codeSandbox.ExecuteCodeRequest;
import chengwu.model.model.codeSandbox.ExecuteCodeResponse;
import chengwu.model.model.codeSandbox.JudgeInfo;
import chengwu.model.model.dto.question.JudgeCase;
import chengwu.model.model.entity.Question;
import chengwu.model.model.entity.QuestionSubmit;
import chengwu.model.model.enums.QuestionSubmitStatusEnum;
import cn.hutool.json.JSONUtil;
import com.chengwu.common.common.ErrorCode;
import com.chengwu.common.exception.BusinessException;
import com.chengwu.judgeservice.judge.JudgeService;
import com.chengwu.judgeservice.judge.codeSandbox.CodeSandbox;
import com.chengwu.judgeservice.judge.codeSandbox.CodeSandboxFactory;
import com.chengwu.judgeservice.judge.codeSandbox.CodeSandboxProxy;
import com.chengwu.judgeservice.judge.strategy.DefaultJudgeStrategy;
import com.chengwu.judgeservice.judge.strategy.JudgeContext;

import java.util.List;
import java.util.stream.Collectors;
import com.chengwu.serviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class JudgeServiceImpl implements JudgeService {
    @Resource
    private QuestionFeignClient questionFeignClient;

    @Value("${codeSandbox.type}")
    private String type;

    /**
     * 对题目提交进行判题处理。
     *
     * @param questionSubmitId 题目提交的ID，用于标识唯一的题目提交记录。
     * @return 返回经过判题后的题目提交信息。
     * @throws BusinessException 如果题目提交不存在、题目不存在、题目已经判题过、更新状态失败或判题失败时，抛出此异常。
     */
    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {

        // 通过ID获取题目提交记录
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目提交不存在");
        }
        long questionId = questionSubmit.getQuestionId();

        // 获取题目信息
        Question question = questionFeignClient.getQuestionById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }

        // 检查题目提交状态，仅处理等待判题的提交
        if (questionSubmit.getStatus() != QuestionSubmitStatusEnum.WAITING.getValue()) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目已经判题过了");
        }

        // 更新题目提交状态为正在判题
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "更新题目提交状态失败");
        }

        // 准备判题输入，包括题目所配置的输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());

        // 构建执行代码的请求
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .inputList(inputList)
                .code(questionSubmit.getCode())
                .language(questionSubmit.getLanguage())
                .build();

        // 调用沙箱环境执行代码并获取执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);

        // 使用判题策略处理沙箱执行结果，生成判题信息
        JudgeContext judgeContext = JudgeContext.builder().question(question).executeCodeResponse(executeCodeResponse).build();
        DefaultJudgeStrategy strategy = new DefaultJudgeStrategy();
        JudgeInfo judgeInfo = strategy.doJudge(judgeContext);

        // 根据判题结果更新题目提交状态为成功，并保存判题信息
        String statusText = judgeInfo.getMessage();
        if (statusText.equals(QuestionSubmitStatusEnum.SUCCEED.getText())) {
            questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
            question.setAcceptedNum(question.getAcceptedNum() + 1);
            questionFeignClient.updateQuestionById(question);
        } else {
            questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.FAILED.getValue());
        }
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "更新题目提交状态失败");
        }
        // 返回更新后的题目提交信息
        return questionFeignClient.getQuestionSubmitById(questionSubmitId);
    }

}
