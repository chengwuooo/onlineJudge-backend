package com.chengwu.onlineJudge.judge.codeSandbox.impl;

import com.chengwu.onlineJudge.judge.codeSandbox.CodeSandbox;
import com.chengwu.onlineJudge.judge.codeSandbox.model.ExecuteCodeRequest;
import com.chengwu.onlineJudge.judge.codeSandbox.model.ExecuteCodeResponse;

//远程沙箱
public class RemoteCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse excuteCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程沙箱执行代码");
        return null;
    }
}
