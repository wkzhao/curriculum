package com.curriculum.service.impl;

import com.curriculum.dao.QuestionDao;
import com.curriculum.domain.KnowledgePoint;
import com.curriculum.domain.PageBean;
import com.curriculum.domain.Question;
import com.curriculum.domain.QuestionContent;
import com.curriculum.domain.QuestionFilter;
import com.curriculum.service.QuestionService;
import com.curriculum.util.Object2Xml;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl
        implements QuestionService
{

    @Autowired
    QuestionDao questionDao;

    @Autowired
    KnowledgePointServiceImpl knowledgePointService;

    public int addQuestion(Question question)
    {
        question.setContent(Object2Xml.toXml(question.getQuestionContent()));
        return questionDao.addQuestion(question);
    }

    public List<Question> getQuestionsByFilter(QuestionFilter questionFilter, PageBean pageBean)
    {
        List<Question> questionList = questionDao.getQuestionsByFilter(questionFilter, pageBean);
        setQuestionContent(questionList);
        for (Question question : questionList) {
            setPointsName(question);
        }
        return questionList == null ? Collections.emptyList() : questionList;
    }

    @Override
    public List<Question> getQuestionByKnowledgePoints(List<Integer> knowledgePointIds) {
        List<Question> questionList = questionDao.getQuestionByKnowledgePoints(knowledgePointIds);
        for(Question question :questionList){
            question.setQuestionContent(Object2Xml.toBean(question.getContent(),QuestionContent.class));
        }
        return questionList == null ? Collections.emptyList() : questionList;
    }

    public Question getQuestionById(int questionId)
    {
        Question question = questionDao.getQuestionById(questionId);
        question.setQuestionContent(Object2Xml.toBean(question.getContent(), QuestionContent.class));
        setPointsName(question);
        return question;
    }

    public int deleteQuestionById(int id)
    {
        Question question = questionDao.getQuestionById(id);
        if (question == null) {
            return 0;
        }
        return questionDao.deleteQuestionById(id);
    }

    public List<Question> getQuestionByIds(List<Integer> ids)
    {
        if(ids == null || ids.size() == 0){
            return Collections.emptyList();
        }
        List questionList = questionDao.getQuestionByIds(ids);
        setQuestionContent(questionList);
        return questionList == null ? Collections.emptyList() : questionList;
    }

    public String getQuestionAnswerById(int questionId)
    {
        return questionDao.getQuestionAnswerById(questionId);
    }

    public int getQuestionCount()
    {
        return questionDao.getQuestionCount();
    }

    @Override
    public int  addQuestionList(List<Question> questionList) {
        if( questionList == null ){
            return -1;
        }
        for( Question question :questionList){
           question.setContent(Object2Xml.toXml(question.getQuestionContent()));
        }
        return questionDao.addQuestionList(questionList,null);
    }

    private List<Question> setQuestionContent(List<Question> questionList) {
        for (Question question : questionList) {
            question.setQuestionContent(Object2Xml.toBean(question.getContent(), QuestionContent.class));
        }
        return questionList;
    }

    private Question setPointsName(Question question) {
        if (question == null) {
            return null;
        }
        String[] points = question.getPoints().split(",");
        String pointsName = "";

        for (int i = 0; i < points.length; i++) {
            if("".equals(points[i])){
                continue;
            }
            KnowledgePoint knowledgePoint = knowledgePointService.getPointById(Integer.parseInt(points[i]));
            if (knowledgePoint != null) {
                pointsName = pointsName + " " + knowledgePoint.getName();
            }
        }
        question.setPointsName(pointsName);
        return question;
    }
}