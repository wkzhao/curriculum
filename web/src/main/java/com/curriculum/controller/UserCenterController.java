package com.curriculum.controller;

import com.curriculum.domain.User;
import com.curriculum.interceptor.annotation.LoginRequired;
import com.curriculum.service.impl.KnowledgePointServiceImpl;
import com.curriculum.service.impl.UserPaperServiceImpl;
import com.curriculum.service.impl.UserQuestionServiceImpl;
import com.curriculum.service.impl.UserServiceImpl;
import com.curriculum.util.ReturnJacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@LoginRequired
public class UserCenterController
{

    @Autowired
    KnowledgePointServiceImpl knowledgePointService;

    @Autowired
    UserQuestionServiceImpl userQuestionService;

    @Autowired
    UserPaperServiceImpl userPaperService;

    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value={"student/user-center"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView toUserCenter()
    {
        return new ModelAndView("student/user-center");
    }
    @RequestMapping(value={"student/analysis"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView analysis(HttpSession session) {
        ModelAndView view = new ModelAndView("student/analysis");
        User user = (User)session.getAttribute("user");
        List knowledgePointList = this.knowledgePointService.getAllPoints();
        List analysisList = this.userQuestionService.getAnalysisByPoints(user.getId(), knowledgePointList);
        view.addObject("analysisList", analysisList);
        return view;
    }
    @RequestMapping(value={"student/exam-history"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView examHistory(HttpSession session) {
        ModelAndView view = new ModelAndView("student/exam-history");
        User user = (User)session.getAttribute("user");
        List userPaperList = this.userPaperService.getUserPaperByUserId(user.getId());
        view.addObject("userPaperList", userPaperList);
        return view;
    }
    @RequestMapping(value={"student/setting"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView setting() { return new ModelAndView("student/setting"); }

    @RequestMapping(value={"student/change-password"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView changePwd() {
        return new ModelAndView("student/change-password");
    }
    @RequestMapping(value={"student/changeUserInfo"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public String changeUserInfo(@RequestBody User u, HttpSession session) throws JsonProcessingException { User currentUser = (User)session.getAttribute("user");
        User user = this.userService.findUserByUsername(currentUser.getUsername());
        if (u.getEmail() != null) {
            currentUser.setEmail(u.getEmail());
        }
        if (u.getTel() != null) {
            currentUser.setTel(u.getTel());
        }
        if (u.getPassword() != null) {
            currentUser.setPassword(u.getPassword());
        }
        this.userService.changeUserInfo(user);
        session.setAttribute("user", currentUser);
        return ReturnJacksonUtil.resultOk();
    }
}