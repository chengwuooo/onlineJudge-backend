package com.chengwu.onlineJudge.judge.codeSandbox.model;

import com.chengwu.onlineJudge.model.vo.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {
    // 代码执行结果
    private List<String> executeOutput;

    // 代码执行状态
    private String status;

    // 代码执行信息
    private JudgeInfo judgeInfo;
}
