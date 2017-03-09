package com.curriculum.controller;

import com.curriculum.domain.KnowledgePoint;
import com.curriculum.domain.User;
import com.curriculum.interceptor.annotation.LoginRequired;
import javax.servlet.http.HttpSession;

import com.curriculum.service.impl.KnowledgePointServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping({""})
public class HomeController
{

    @Autowired
    KnowledgePointServiceImpl knowledgePointService;

    @RequestMapping(value={"", "home"}, method = RequestMethod.GET)
    public ModelAndView toHome(HttpSession session)
    {
        User user = (User)session.getAttribute("user");
        ModelAndView view ;
        if ((user != null) && (user.getRoleId() == 0)) {
            view = new ModelAndView("redirect:admin/home");
        }else{
            view = new ModelAndView("home");
            List<KnowledgePoint> knowledgePointList = knowledgePointService.getPointsByPage(0,null);
            view.addObject("knowledgePointList",knowledgePointList);
        }
        return view;
    }

    @LoginRequired
    @RequestMapping(value={"admin/home"}, method = RequestMethod.GET)
    public String toAdminHome() {
        return "redirect:knowledge-list";
    }
}