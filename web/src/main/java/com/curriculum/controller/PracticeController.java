package com.curriculum.controller;

import com.curriculum.domain.KnowledgePoint;
import com.curriculum.domain.Question;
import com.curriculum.domain.QuestionFilter;
import com.curriculum.domain.User;
import com.curriculum.interceptor.annotation.LoginRequired;
import com.curriculum.service.impl.KnowledgePointServiceImpl;
import com.curriculum.service.impl.QuestionServiceImpl;
import com.curriculum.service.impl.UserQuestionServiceImpl;
import com.curriculum.util.ReturnJacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@LoginRequired
public class PracticeController
{

    @Autowired
    UserQuestionServiceImpl userQuestionService;

    @Autowired
    QuestionServiceImpl questionService;

    @Autowired
    KnowledgePointServiceImpl knowledgePointService;

    @RequestMapping(value={"student/practice-incorrect/{knowledgePointId}"}, method = RequestMethod.GET)
    public ModelAndView practiceIncorrect(HttpSession session, @PathVariable("knowledgePointId") int pointId)
    {
        ModelAndView view = new ModelAndView("student/practice-incorrect");
        User user = (User)session.getAttribute("user");
        List questionIds = userQuestionService.getErrorQuestionIds(user.getId(), pointId + "");
        List questionList = questionService.getQuestionByIds(questionIds);
        view.addObject("questionList", questionList);
        view.addObject("amount", questionList.size());
        KnowledgePoint knowledgePoint = knowledgePointService.getPointById(pointId);
        view.addObject("knowledgePointName", knowledgePoint.getName());
        return view;
    }
    @RequestMapping(value={"student/practice-improve/{knowledgePointId}"}, method = RequestMethod.GET)
    public ModelAndView toPracticeImprove(HttpSession session, @PathVariable("knowledgePointId") int pointId) {
        ModelAndView view = new ModelAndView("student/practice-improve");
        User user = (User)session.getAttribute("user");
        QuestionFilter questionFilter = new QuestionFilter(0L, pointId + "", 0L);
        List questionList = questionService.getQuestionsByFilter(questionFilter, null);
        KnowledgePoint knowledgePoint = knowledgePointService.getPointById(pointId);
        view.addObject("knowledgePointName",knowledgePoint.getName());
        view.addObject("questionList", questionList);
        view.addObject("amount", questionList.size());
        return view;
    }
    @RequestMapping(value={"student/practice-improve"}, method = RequestMethod.POST)
    @ResponseBody
    public String practiceImprove(HttpSession session, @RequestBody Map<String, String> map) throws JsonProcessingException { User user = (User)session.getAttribute("user");
        Map questionIdAnswerMap = new HashMap();
        String questionId = (String)map.get("questionId");
        questionIdAnswerMap.put(questionId, map.get("uAnswer"));
        userQuestionService.addUserQuestions(user, questionIdAnswerMap);
        Question question = questionService.getQuestionById(Integer.parseInt(questionId));
        return ReturnJacksonUtil.resultOk(question, Locale.CHINA);
    }
}