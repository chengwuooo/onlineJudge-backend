package com.chengwu.onlineJudge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengwu.onlineJudge.model.entity.Question;
import com.chengwu.onlineJudge.mapper.QuestionMapper;
import com.chengwu.onlineJudge.service.QuestionService;
import org.springframework.stereotype.Service;


@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService {

}




