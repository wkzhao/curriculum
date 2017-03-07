package com.curriculum.service;

import com.curriculum.domain.Analysis;
import com.curriculum.domain.KnowledgePoint;
import com.curriculum.domain.User;
import java.util.List;
import java.util.Map;

public interface UserQuestionService
{
   int addUserQuestions(User paramUser, Map<String, String> paramMap);

   List<Integer> getErrorQuestionIds(int paramInt, String paramString);

   List<Analysis> getAnalysisByPoints(int paramInt, List<KnowledgePoint> paramList);
}
