package com.chengwu.onlineJudge.judge.codeSandbox;

import com.chengwu.onlineJudge.judge.codeSandbox.impl.ExampleCodeSandbox;
import com.chengwu.onlineJudge.judge.codeSandbox.impl.RemoteCodeSandbox;
import com.chengwu.onlineJudge.judge.codeSandbox.impl.ThridPartyCodeSandbox;

/**
 * 代码沙箱工厂
 */
public class CodeSandboxFactory {
    /**
     * 获取对应的代码沙箱实现
     * @param type 沙箱类型
     */
    public static CodeSandbox newInstance(String type) {
        if ("example".equals(type)) {
            return new ExampleCodeSandbox();
        } else if ("remote".equals(type)) {
            return new RemoteCodeSandbox();
        } else if ("thirdParty".equals(type)) {
            return new ThridPartyCodeSandbox();
        }
        return null;
    }
}
