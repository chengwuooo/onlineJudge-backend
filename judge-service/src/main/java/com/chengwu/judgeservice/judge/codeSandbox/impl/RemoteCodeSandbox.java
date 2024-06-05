package com.chengwu.judgeservice.judge.codeSandbox.impl;

import chengwu.model.model.codeSandbox.ExecuteCodeRequest;
import chengwu.model.model.codeSandbox.ExecuteCodeResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.chengwu.judgeservice.judge.codeSandbox.CodeSandbox;


//远程沙箱
public class RemoteCodeSandbox implements CodeSandbox {
    public static final String AUTH_REQUEST_HEADER = "auth";
    // 密钥
    public static final String AUTH_REQUEST_SECRET = "secretKey";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        // 请求头


        System.out.println("远程沙箱执行代码");
        System.out.println(executeCodeRequest);
        String url = "code-sandbox.chengwu.icu/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        System.out.println(json);

        String responseStr = HttpUtil.createPost(url).
                header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET).
                body(json).
                execute().
                body();
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
