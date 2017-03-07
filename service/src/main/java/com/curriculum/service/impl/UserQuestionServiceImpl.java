/*    */ package com.curriculum.service.impl;
/*    */ 
/*    */ import com.curriculum.dao.QuestionDao;
/*    */ import com.curriculum.dao.UserQuestionDao;
/*    */ import com.curriculum.domain.Analysis;
/*    */ import com.curriculum.domain.KnowledgePoint;
/*    */ import com.curriculum.domain.User;
/*    */ import com.curriculum.domain.UserQuestion;
/*    */ import com.curriculum.service.UserQuestionService;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ @Service
/*    */ public class UserQuestionServiceImpl
/*    */   implements UserQuestionService
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   UserQuestionDao userQuestionDao;
/*    */ 
/*    */   @Autowired
/*    */   QuestionDao questionDao;
/*    */ 
/*    */   public int addUserQuestions(User user, Map<String, String> questionIdAnswermap)
/*    */   {
/* 30 */     int userId = user.getId();
/* 31 */     Set<String> questionIds = questionIdAnswermap.keySet();
/* 32 */     List userQuestionList = null;
/* 33 */     UserQuestion temp = null;
/*    */ 
/* 36 */     for (String questionId : questionIds) {
/* 37 */       if (userQuestionList == null) {
/* 38 */         userQuestionList = new ArrayList();
/*    */       }
/* 40 */       String answer = this.questionDao.getQuestionAnswerById(Integer.parseInt(questionId));
/* 41 */       String[] uAnswer = ((String)questionIdAnswermap.get(questionId)).split("#");
/* 42 */       temp = new UserQuestion();
/* 43 */       temp.setUserId(userId);
/* 44 */       temp.setUserAnswer(uAnswer[0]);
/* 45 */       temp.setQuestionPoints(uAnswer[1]);
/* 46 */       temp.setQuestionId(Integer.parseInt(questionId));
/* 47 */       if (answer.equals(uAnswer[0]))
/* 48 */         temp.setWrongOrRight(1);
/*    */       else {
/* 50 */         temp.setWrongOrRight(0);
/*    */       }
/* 52 */       userQuestionList.add(temp);
/*    */     }
/* 54 */     return this.userQuestionDao.addUserQuestions(userQuestionList, null);
/*    */   }
/*    */ 
/*    */   public List<Integer> getErrorQuestionIds(int userId, String points)
/*    */   {
/* 59 */     List questionsIds = this.userQuestionDao.getErrorQuestionIds(userId, points);
/* 60 */     return questionsIds == null ? Collections.emptyList() : questionsIds;
/*    */   }
/*    */ 
/*    */   public List<Analysis> getAnalysisByPoints(int userId, List<KnowledgePoint> knowledgePointList)
/*    */   {
/* 65 */     List analysisList = new ArrayList();
/*    */ 
/* 68 */     for (KnowledgePoint knowledgePoint : knowledgePointList) {
/* 69 */       Analysis analysis = new Analysis();
/* 70 */       analysis.setKnowledgePointName(knowledgePoint.getName());
/* 71 */       analysis.setWrongTimes(this.userQuestionDao.getCountByPointId(knowledgePoint.getId() + "", userId, 0));
/* 72 */       analysis.setRightTimes(this.userQuestionDao.getCountByPointId(knowledgePoint.getId() + "", userId, 1));
/* 73 */       int totalNum = this.questionDao.getCountByPoints(knowledgePoint.getId() + "");
/* 74 */       analysis.setTotalNums(totalNum);
/* 75 */       analysisList.add(analysis);
/*    */     }
/* 77 */     return analysisList;
/*    */   }
/*    */ }

/* Location:           C:\Users\wenkun.zhao\Desktop\service-1.0-SNAPSHOT.jar
 * Qualified Name:     com.curriculum.service.impl.UserQuestionServiceImpl
 * JD-Core Version:    0.6.2
 */