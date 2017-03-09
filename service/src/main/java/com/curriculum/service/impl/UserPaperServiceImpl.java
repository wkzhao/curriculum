package com.curriculum.service.impl;

import com.curriculum.dao.UserPaperDao;
import com.curriculum.domain.Question;
import com.curriculum.domain.UserPaper;
import com.curriculum.service.UserPaperService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.curriculum.util.ExampaperUtil;
import com.curriculum.util.Object2Xml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPaperServiceImpl
        implements UserPaperService
{

    @Autowired
    UserPaperDao userPaperDao;

    @Autowired
    QuestionServiceImpl questionService;

    public List<UserPaper> getUserPaperByUserId(int userId)
    {
        List userPaperList = userPaperDao.getUserPaperByUserId(userId);
        return userPaperList == null ? Collections.emptyList() : userPaperList;
    }

    @Override
    public UserPaper getUserPaperByUserIdAndPaperId(int userId, int paperId) {
        return userPaperDao.getUserPaperByUserIdAndPaperId(userId,paperId);
    }

    public int addUserPaper(int userId, int paperId,Map<String,String> questionIdAnswerMap)
    {
        List<Question> questionList = new ArrayList<>();
        List<Integer> questionsIds = new ArrayList<>();
        Question question = null ;
        String[] ans;
        for(String questionId :questionIdAnswerMap.keySet()){
            questionsIds.add(Integer.parseInt(questionId));
            String answer = questionIdAnswerMap.get(questionId);
            ans = answer.split("#");
            question = new Question();
            question.setId(Integer.parseInt(questionId));
            question.setuAnswer(ans[0]);
            question.setPoints(ans[1]);
            questionList.add(question);
        }
        List<Question> questions = questionService.getQuestionByIds(questionsIds);
        Question question1;
        Question question2;
        for( int i = 0 ;i < questions.size() ;i++){
            question1 = questions.get(i);
            question2 = questionList.get(i);
            question1.setuAnswer(question2.getuAnswer());
            question1.setPoints(question2.getPoints());
            if(question1.getQuestionTypeId() <4 && question1.getAnswer().equals(question2.getuAnswer())){
                question1.setWrongOrRight(1);
            }else{
                question1.setWrongOrRight(0);
            }
        }
        String content = ExampaperUtil.questionListToXml(questions);
        return userPaperDao.addUserPaper(userId, paperId,content);
    }

    public int getCountByUserAndPaper(int userId, int paperId)
    {
        return userPaperDao.getCountByUserAndPaper(userId, paperId);
    }
}