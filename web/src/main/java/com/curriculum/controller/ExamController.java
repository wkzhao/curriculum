package com.curriculum.controller;

import com.curriculum.domain.Exampaper;
import com.curriculum.domain.User;
import com.curriculum.interceptor.annotation.LoginRequired;
import com.curriculum.service.impl.ExampaperServiceImpl;
import com.curriculum.service.impl.KnowledgePointServiceImpl;
import com.curriculum.service.impl.UserPaperServiceImpl;
import com.curriculum.service.impl.UserQuestionServiceImpl;
import com.curriculum.util.ExampaperUtil;
import com.curriculum.util.ReturnJacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.List;
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
public class ExamController
{

    @Autowired
    ExampaperServiceImpl exampaperService;

    @Autowired
    UserQuestionServiceImpl userQuestionService;

    @Autowired
    KnowledgePointServiceImpl knowledgePointService;

    @Autowired
    UserPaperServiceImpl userPaperService;

    @RequestMapping(value={"start-exam"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView startExam()
    {
        ModelAndView view = new ModelAndView("start-exam");
        List exampaperList = this.exampaperService.getSimplePapers();
        view.addObject("exampaperList", exampaperList);
        List knowledgePointList = this.knowledgePointService.getAllPoints();
        view.addObject("knowledgePointList", knowledgePointList);
        return view;
    }
    @RequestMapping(value={"student/examing/{paperId}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView exam(@PathVariable("paperId") int paperId, HttpSession session) {
        User user = (User)session.getAttribute("user");
        int count = this.userPaperService.getCountByUserAndPaper(user.getId(), paperId);
        if (count > 0) {
            return new ModelAndView("has-examed");
        }
        ModelAndView view = new ModelAndView("student/examing");
        Exampaper exampaper = this.exampaperService.getPaperById(paperId);
        Map map = ExampaperUtil.XmlToQuestionMap(exampaper.getContent());
        view.addObject("singleQuestions", map.get(Integer.valueOf(1)));
        view.addObject("multiQuestions", map.get(Integer.valueOf(2)));
        view.addObject("judgeQuestions", map.get(Integer.valueOf(3)));
        List otherQuestions = new ArrayList();

        for (int i = 4; i < 7; i++) {
            List questionList = (List)map.get(Integer.valueOf(i));
            if ((questionList != null) && (questionList.size() != 0)) {
                otherQuestions.addAll(questionList);
            }
        }
        view.addObject("otherQuestions", otherQuestions);
        view.addObject("paperId", Integer.valueOf(paperId));
        view.addObject("time", Integer.valueOf(exampaper.getTime() * 60));
        return view;
    }
    @RequestMapping(value={"student/exam-finished"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public String examFinished(@RequestBody Map<String, Object> map, HttpSession session) throws JsonProcessingException { int paperId = Integer.parseInt((String)map.get("paperId"));
        Map questionIdAnswerMap = (Map)map.get("answer");
        User user = (User)session.getAttribute("user");
        this.userPaperService.addUserPaper(user.getId(), paperId);
        this.userQuestionService.addUserQuestions(user, questionIdAnswerMap);
        return ReturnJacksonUtil.resultOk();
    }
}