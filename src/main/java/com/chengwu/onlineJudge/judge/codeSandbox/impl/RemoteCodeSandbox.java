package com.chengwu.onlineJudge.judge.codeSandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.chengwu.onlineJudge.common.ErrorCode;
import com.chengwu.onlineJudge.exception.BusinessException;
import com.chengwu.onlineJudge.judge.codeSandbox.CodeSandbox;
import com.chengwu.onlineJudge.judge.codeSandbox.model.ExecuteCodeRequest;
import com.chengwu.onlineJudge.judge.codeSandbox.model.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;

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
        String url = "http://code-sandbox.chengwu.icu:8083/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        System.out.println(json);

        String responseStr = HttpUtil.createPost(url).
                header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET).
                body(json).
                execute().
                body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.OPERATION_REMOTE_ERROR);
        }
        System.out.println(responseStr);


        ExecuteCodeResponse executeCodeResponse = JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);

        return executeCodeResponse;
    }
}
