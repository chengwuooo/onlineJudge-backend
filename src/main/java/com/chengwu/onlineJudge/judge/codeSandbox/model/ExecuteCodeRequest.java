package com.chengwu.onlineJudge.judge.codeSandbox.model;

import cn.hutool.json.JSONUtil;
import com.chengwu.onlineJudge.model.dto.question.JudgeCase;
import com.chengwu.onlineJudge.model.entity.Question;
import com.chengwu.onlineJudge.model.entity.QuestionSubmit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeRequest {
    private String code;
    private String language;
    private List<String> inputList;
}
