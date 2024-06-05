package com.chengwu.judgeservice.judge.codeSandbox;


import chengwu.model.model.codeSandbox.ExecuteCodeRequest;
import chengwu.model.model.codeSandbox.ExecuteCodeResponse;

public interface CodeSandbox {
    /**
     * 执行代码
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);

}
