package com.curriculum.service;

import com.curriculum.domain.PageBean;
import com.curriculum.domain.Question;
import com.curriculum.domain.QuestionFilter;
import java.util.List;

public interface QuestionService {
   int addQuestion(Question question);

   List<Question> getQuestionsByFilter(QuestionFilter questionFilter, PageBean pageBean);

   List<Question> getQuestionByKnowledgePoints( List<Integer> knowledgePointIds);

   Question getQuestionById(int id);

   int deleteQuestionById(int id);

   List<Question> getQuestionByIds(List<Integer> ids);

   String getQuestionAnswerById(int id);

   int getQuestionCount();
}