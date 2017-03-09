package com.curriculum.service;

import com.curriculum.domain.Analysis;
import com.curriculum.domain.KnowledgePoint;
import com.curriculum.domain.User;
import java.util.List;
import java.util.Map;

public interface UserQuestionService
{
   int addUserQuestions(User user, Map<String, String> questionIdAnswerMap);

   List<Integer> getErrorQuestionIds(int userId, String points);

   List<Analysis> getAnalysisByPoints(int userId, List<KnowledgePoint> knowledgePointList);
}
