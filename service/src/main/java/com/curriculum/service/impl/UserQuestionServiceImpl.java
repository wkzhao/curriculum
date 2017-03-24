 package com.curriculum.service.impl;
 
 import com.curriculum.dao.QuestionDao;
 import com.curriculum.dao.UserQuestionDao;
 import com.curriculum.domain.Analysis;
 import com.curriculum.domain.KnowledgePoint;
 import com.curriculum.domain.User;
 import com.curriculum.domain.UserQuestion;
 import com.curriculum.service.UserQuestionService;
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 
 @Service
 public class UserQuestionServiceImpl
   implements UserQuestionService
 {
 
   @Autowired
   UserQuestionDao userQuestionDao;
 
   @Autowired
   QuestionDao questionDao;
 
   public int addUserQuestions(User user, Map<String, String> questionIdAnswermap)
   {
     int userId = user.getId();
     Set<String> questionIds = questionIdAnswermap.keySet();
     List userQuestionList = null;
     UserQuestion temp = null;
 
     for (String questionId : questionIds) {
       if (userQuestionList == null) {
         userQuestionList = new ArrayList();
       }
       String answer = questionDao.getQuestionAnswerById(Integer.parseInt(questionId));
       String[] uAnswer = (questionIdAnswermap.get(questionId)).split("#");
       temp = new UserQuestion();
       temp.setUserId(userId);
       temp.setUserAnswer(uAnswer[0]);
       temp.setQuestionPoints(uAnswer[1]);
       temp.setQuestionId(Integer.parseInt(questionId));
       if (answer.equals(uAnswer[0]))
         temp.setWrongOrRight(1);
       else {
         temp.setWrongOrRight(0);
       }
       userQuestionList.add(temp);
     }
     return userQuestionDao.addUserQuestions(userQuestionList, null);
   }
 
   public List<Integer> getErrorQuestionIds(int userId, String points)
   {
     List questionsIds = userQuestionDao.getErrorQuestionIds(userId, points);
     return questionsIds == null ? Collections.emptyList() : questionsIds;
   }
 
   public List<Analysis> getAnalysisByPoints(int userId, List<KnowledgePoint> knowledgePointList)
   {
     List analysisList = new ArrayList();
     for (KnowledgePoint knowledgePoint : knowledgePointList) {
       Analysis analysis = new Analysis();
       analysis.setKnowledgePointName(knowledgePoint.getName());
       analysis.setWrongTimes(userQuestionDao.getCountByPointId(knowledgePoint.getId() + "", userId, 0));
       analysis.setRightTimes(userQuestionDao.getCountByPointId(knowledgePoint.getId() + "", userId, 1));
       int totalNum = questionDao.getCountByPoints(knowledgePoint.getId() + "");
       analysis.setTotalNums(totalNum);
       analysisList.add(analysis);
     }
     return analysisList;
   }
 }

