package com.curriculum.service.impl;

import com.curriculum.dao.PaperDao;
import com.curriculum.domain.Exampaper;
import com.curriculum.domain.PageBean;
import com.curriculum.domain.Question;
import com.curriculum.service.ExampaperService;
import com.curriculum.util.ExampaperUtil;
import com.curriculum.util.Object2Xml;
import com.curriculum.util.Roulette;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExampaperServiceImpl
        implements ExampaperService
{

    @Autowired
    PaperDao paperDao;

    @Autowired
    QuestionServiceImpl questionService;


    public List<Exampaper>  getExamPaperByPage(int status, PageBean pageBean)
    {
        List exampaperList = paperDao.getExamPaperByPage(status,pageBean);
        return exampaperList == null ? Collections.emptyList() : exampaperList;
    }

    @Override
    public Exampaper createExamPaper(Exampaper exampaper) {
        if( exampaper == null ){
            return null;
        }
        List<Integer> knowledgePointIds = exampaper.getKnowledgePoints();
        List<Question> questionList = questionService.getQuestionByKnowledgePoints(knowledgePointIds);
        Map<Integer,List<Question>>  questionTypeMap = new HashMap<>();
        List<Question> partQuestionList = null;
        StringBuilder stringBuilder = new StringBuilder(",");
        for(Question question :questionList){
            int questionTypeId = question.getQuestionTypeId();
            partQuestionList = questionTypeMap.get(questionTypeId);
            if( partQuestionList == null){
                partQuestionList = new ArrayList<>();
            }
            partQuestionList.add(question);
            questionTypeMap.put(questionTypeId,partQuestionList);
        }
        Map<Integer,Integer> pointsPossibilityMap = new LinkedHashMap<>();
        DecimalFormat df   =new   java.text.DecimalFormat("#.00");
        for( int pointId : knowledgePointIds){
            double rate = Double.parseDouble(df.format((1.0/knowledgePointIds.size())));
            pointsPossibilityMap.put(pointId,(int) (rate*100));
        }
        int[] result;
        List<Question> exampaperQuestions = new ArrayList<>();
        for(int questionType :exampaper.getQuestionTypeNumMap().keySet()){
            int num = exampaper.getQuestionTypeNumMap().get(questionType);
            result = new int[num];
            result = Roulette.roulette(pointsPossibilityMap,result);
            partQuestionList = questionTypeMap.get(questionType);
            if(partQuestionList == null){
                break;
            }
            for(int i = 0 ;i< result.length ;i++){
                ListIterator<Question> questionListIterator = partQuestionList.listIterator();
                while(questionListIterator.hasNext()){
                    Question question = questionListIterator.next();
                    if(question.getPoints().contains(","+result[i]+",")){
                        exampaperQuestions.add(question);
                        stringBuilder.append(question.getId()+",");
                        questionListIterator.remove();
                        break;
                    }
                }
            }
        }
        exampaper.setContent(ExampaperUtil.questionListToXml(exampaperQuestions));
        exampaper.setQuestionIds(stringBuilder.toString());
        return exampaper;
    }

    @Override
    public int getPaperCount(int status) {
        return paperDao.getPaperCount(status);
    }

    public List<Exampaper> getSimplePapers()
    {
        List exampaperList = paperDao.getSimplePapers();
        return exampaperList == null ? Collections.emptyList() : exampaperList;
    }

    public int addPaper(Exampaper exampaper)
    {
        return paperDao.addPaper(exampaper);
    }

    public Exampaper getPaperById(int id)
    {
        return paperDao.getPaperById(id);
    }

    public int deletePaper(int id)
    {
        if (getPaperById(id) == null) {
            return 0;
        }
        return paperDao.deletePaper(id);
    }

    public int changePaperStatus(int id, int status)
    {
        return paperDao.changePaperStatus(id, status);
    }

    public int changePaperContent(String content, int id)
    {
        return paperDao.changePaperContent(content, id);
    }

    public int changePaperQuestionIds(String questionIds, int id)
    {
        return paperDao.changePaperQuestionIds(questionIds, id);
    }

    @Override
    public int changePaperProperty(Exampaper exampaper) {
        return paperDao.changePaperProperty(exampaper);
    }
}