package com.curriculum.service.impl;

import com.curriculum.constant.KnowledgePointEnum;
import com.curriculum.dao.QuestionDao;
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

    public int addQuestion(Question question)
    {
        question.setContent(Object2Xml.toXml(question.getQuestionContent()));
        return this.questionDao.addQuestion(question);
    }

    public List<Question> getQuestionsByFilter(QuestionFilter questionFilter, PageBean pageBean)
    {
        List<Question> questionList = this.questionDao.getQuestionsByFilter(questionFilter, pageBean);
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
        Question question = this.questionDao.getQuestionById(questionId);
        question.setQuestionContent(Object2Xml.toBean(question.getContent(), QuestionContent.class));
        setPointsName(question);
        return question;
    }

    public int deleteQuestionById(int id)
    {
        Question question = this.questionDao.getQuestionById(id);
        if (question == null) {
            return 0;
        }
        return this.questionDao.deleteQuestionById(id);
    }

    public List<Question> getQuestionByIds(List<Integer> ids)
    {
        List questionList = this.questionDao.getQuestionByIds(ids);
        setQuestionContent(questionList);
        return questionList == null ? Collections.emptyList() : questionList;
    }

    public String getQuestionAnswerById(int questionId)
    {
        return this.questionDao.getQuestionAnswerById(questionId);
    }

    public int getQuestionCount()
    {
        return this.questionDao.getQuestionCount();
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
            KnowledgePointEnum knowledgePointEnum = KnowledgePointEnum.getEnumById(points[i]);
            if (knowledgePointEnum != null) {
                pointsName = pointsName + " " + knowledgePointEnum.getTypeName();
            }
        }
        question.setPointsName(pointsName);
        return question;
    }
}