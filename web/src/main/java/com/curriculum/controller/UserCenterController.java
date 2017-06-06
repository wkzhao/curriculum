package com.curriculum.controller;


import com.curriculum.domain.User;
import com.curriculum.domain.UserPaper;
import com.curriculum.interceptor.annotation.LoginRequired;
import com.curriculum.service.KnowledgePointService;
import com.curriculum.service.UserPaperService;
import com.curriculum.service.UserQuestionService;
import com.curriculum.service.UserService;
import com.curriculum.util.ExampaperUtil;
import com.curriculum.util.ReturnJacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@LoginRequired
public class UserCenterController
{

    @Autowired
    KnowledgePointService knowledgePointService;

    @Autowired
    UserQuestionService userQuestionService;

    @Autowired
    UserPaperService userPaperService;

    @Autowired
    UserService userService;

    @RequestMapping(value={"student/user-center"}, method = RequestMethod.GET)
    public ModelAndView toUserCenter()
    {
        return new ModelAndView("student/user-center");
    }
    @RequestMapping(value={"student/analysis/{userId}"}, method = RequestMethod.GET)
    public ModelAndView analysis(@PathVariable("userId") int userId, @RequestParam(value = "isAdmin",required = false) boolean isAdmin) {
        ModelAndView view = new ModelAndView("student/analysis");
        List knowledgePointList = knowledgePointService.getPointsByPage(0,null);
        List analysisList = userQuestionService.getAnalysisByPoints(userId, knowledgePointList);
        view.addObject("analysisList", analysisList);
        if(isAdmin){
            view.addObject("isAdmin",true);
        }else{
            view.addObject("isAdmin",false);
        }
        return view;
    }
    @RequestMapping(value={"student/exam-history/{userId}"}, method = RequestMethod.GET)
    public ModelAndView examHistory(@PathVariable("userId") int userId,@RequestParam(value = "isAdmin",required = false) boolean isAdmin) {
        ModelAndView view = new ModelAndView("student/exam-history");
        List userPaperList = userPaperService.getUserPaperByUserId(userId);
        view.addObject("userPaperList", userPaperList);
        if(isAdmin){
            view.addObject("isAdmin",true);
        }
        return view;
    }

    @RequestMapping(value = "student/exam-report-{paperId}-{userId}")
    public ModelAndView examReport(@PathVariable("paperId") int paperId,@PathVariable("userId") int userId,
                                    @RequestParam(value = "isAdmin",required = false) boolean  isAdmin){
        ModelAndView view = new ModelAndView("student/exam-report");
        UserPaper userPaper = userPaperService.getUserPaperByUserIdAndPaperId(userId,paperId);
        Map map = ExampaperUtil.XmlToQuestionMap(userPaper.getContent());
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
        if(isAdmin){
            view.addObject("isAdmin",true);
            view.addObject("userId",userId);
        }else{
            view.addObject("isAdmin",false);
        }
        return view;
    }



    @RequestMapping(value={"student/setting"}, method = RequestMethod.GET)
    public ModelAndView setting() { return new ModelAndView("student/setting"); }

    @RequestMapping(value={"student/change-password"}, method = RequestMethod.GET)
    public ModelAndView changePwd() {
        return new ModelAndView("student/change-password");
    }
    @RequestMapping(value={"student/changeUserInfo"}, method = RequestMethod.POST)
    @ResponseBody
    public String changeUserInfo(@RequestBody User u, HttpSession session) throws JsonProcessingException { User currentUser = (User)session.getAttribute("user");
        User user = userService.findUserByUsername(currentUser.getUsername());
        if (u.getEmail() != null) {
            currentUser.setEmail(u.getEmail());
        }
        if (u.getTel() != null) {
            currentUser.setTel(u.getTel());
        }
        if (u.getPassword() != null) {
            currentUser.setPassword(u.getPassword());
        }
        userService.changeUserInfo(user);
        session.setAttribute("user", currentUser);
        return ReturnJacksonUtil.resultOk();
    }
}