package com.chengwu.onlineJudge.judge.codeSandbox.impl;
import java.util.List;

import com.chengwu.onlineJudge.judge.codeSandbox.CodeSandbox;
import com.chengwu.onlineJudge.judge.codeSandbox.model.ExecuteCodeRequest;
import com.chengwu.onlineJudge.judge.codeSandbox.model.ExecuteCodeResponse;

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
