package com.curriculum.service;

import com.curriculum.domain.PageBean;
import com.curriculum.domain.Question;
import com.curriculum.domain.QuestionFilter;
import java.util.List;

public interface QuestionService {
   int addQuestion(Question paramQuestion);

   List<Question> getQuestionsByFilter(QuestionFilter paramQuestionFilter, PageBean paramPageBean);

   Question getQuestionById(int paramInt);

   int deleteQuestionById(int paramInt);

   List<Question> getQuestionByIds(List<Integer> paramList);

   String getQuestionAnswerById(int paramInt);

   int getQuestionCount();
}