package com.chengwu.judgeservice.judge.codeSandbox.impl;


import chengwu.model.model.codeSandbox.ExecuteCodeRequest;
import chengwu.model.model.codeSandbox.ExecuteCodeResponse;
import com.chengwu.judgeservice.judge.codeSandbox.CodeSandbox;

// 示例代码沙箱
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
     String code = executeCodeRequest.getCode();
     String language = executeCodeRequest.getLanguage();
//     List<String> input = executeCodeRequest.getInput();



        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
//        executeCodeResponse.setOutput();
//        executeCodeResponse.setStatus();
//        executeCodeResponse.setJudgeInfo();

        return null;
    }
}
