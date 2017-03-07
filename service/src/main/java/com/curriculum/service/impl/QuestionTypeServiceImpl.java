/*    */ package com.curriculum.service.impl;
/*    */ 
/*    */ import com.curriculum.dao.QuestionTypeDao;
/*    */ import com.curriculum.domain.QuestionType;
/*    */ import com.curriculum.service.QuestionTypeService;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ @Service
/*    */ public class QuestionTypeServiceImpl
/*    */   implements QuestionTypeService
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   QuestionTypeDao questionTypeDao;
/*    */ 
/*    */   public List<QuestionType> getAllTypes()
/*    */   {
/* 23 */     List questionTypeList = this.questionTypeDao.getAllTypes();
/* 24 */     return questionTypeList == null ? Collections.emptyList() : questionTypeList;
/*    */   }
/*    */ }

/* Location:           C:\Users\wenkun.zhao\Desktop\service-1.0-SNAPSHOT.jar
 * Qualified Name:     com.curriculum.service.impl.QuestionTypeServiceImpl
 * JD-Core Version:    0.6.2
 */