package com.chengwu.onlineJudge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengwu.onlineJudge.model.entity.QuestionSubmit;
import com.chengwu.onlineJudge.mapper.QuestionSubmitMapper;
import com.chengwu.onlineJudge.service.QuestionSubmitService;
import org.springframework.stereotype.Service;

/**
* 针对表【question_submit(题目提交)】的数据库操作Service实现
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService {

}




