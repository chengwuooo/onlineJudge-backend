package com.chengwu.onlineJudge.judge.codeSandbox;

import com.chengwu.onlineJudge.judge.codeSandbox.model.ExecuteCodeRequest;
import com.chengwu.onlineJudge.judge.codeSandbox.model.ExecuteCodeResponse;

public interface CodeSandbox {
    /**
     * 执行代码
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);

}
