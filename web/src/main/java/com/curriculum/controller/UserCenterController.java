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
import org.springframework.web.bind.annotation.*;
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