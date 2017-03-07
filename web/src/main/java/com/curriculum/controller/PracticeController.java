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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @RequestMapping(value={"student/practice-incorrect/{knowledgePointId}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView practiceIncorrect(HttpSession session, @PathVariable("knowledgePointId") int pointId)
    {
        ModelAndView view = new ModelAndView("student/practice-incorrect");
        User user = (User)session.getAttribute("user");
        List questionIds = this.userQuestionService.getErrorQuestionIds(user.getId(), pointId + "");
        List questionList = this.questionService.getQuestionByIds(questionIds);
        view.addObject("questionList", questionList);
        view.addObject("amount", Integer.valueOf(questionList.size()));
        KnowledgePoint knowledgePoint = this.knowledgePointService.getPointById(pointId);
        view.addObject("knowledgePointName", knowledgePoint.getName());
        return view;
    }
    @RequestMapping(value={"student/practice-improve/{knowledgePointId}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView toPracticeImprove(HttpSession session, @PathVariable("knowledgePointId") int pointId) {
        ModelAndView view = new ModelAndView("student/practice-improve");
        User user = (User)session.getAttribute("user");
        QuestionFilter questionFilter = new QuestionFilter(0L, pointId + "", 0L);
        List questionList = this.questionService.getQuestionsByFilter(questionFilter, null);
        view.addObject("questionList", questionList);
        return view;
    }
    @RequestMapping(value={"student/practice-improve"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public String practiceImprove(HttpSession session, @RequestBody Map<String, String> map) throws JsonProcessingException { User user = (User)session.getAttribute("user");
        Map questionIdAnswerMap = new HashMap();
        String questionId = (String)map.get("questionId");
        questionIdAnswerMap.put(questionId, map.get("uAnswer"));
        this.userQuestionService.addUserQuestions(user, questionIdAnswerMap);
        Question question = this.questionService.getQuestionById(Integer.parseInt(questionId));
        return ReturnJacksonUtil.resultOk(question, Locale.CHINA);
    }
}