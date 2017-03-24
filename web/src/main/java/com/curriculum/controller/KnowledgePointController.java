package com.curriculum.controller;

import com.curriculum.constant.Constants;
import com.curriculum.domain.KnowledgePoint;
import com.curriculum.domain.PageBean;
import com.curriculum.interceptor.annotation.LoginRequired;
import com.curriculum.service.impl.KnowledgePointServiceImpl;
import com.curriculum.service.impl.KnowledgeServiceImpl;
import com.curriculum.util.ReturnJacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@LoginRequired
public class KnowledgePointController
{

    @Autowired
    KnowledgePointServiceImpl knowledgePointService;

    @Autowired
    KnowledgeServiceImpl knowledgeService;

    @RequestMapping(value={"admin/point-list-{knowledgeId}-{currentPage}"}, method = RequestMethod.GET)
    public ModelAndView toPointList(@PathVariable("knowledgeId") int knowledgeId,@PathVariable("currentPage") int currentPage)
    {
        ModelAndView view = new ModelAndView("admin/point-list");
        int count = knowledgePointService.getPointCount(knowledgeId);
        PageBean pageBean = new PageBean(currentPage, Constants.PAGE_SIZE,count);
        List knowledgePointList = knowledgePointService.getPointsByPage(knowledgeId,pageBean);
        view.addObject("knowledgePointList", knowledgePointList);
        List knowledgeList = knowledgeService.getAllKnowledge();
        view.addObject("knowledgeList", knowledgeList);
        view.addObject("knowledgeId", knowledgeId);
        view.addObject("pageBean",pageBean);
        return view;
    }
    @ResponseBody
    @RequestMapping(value={"admin/get-knowledge-point/{knowledgeId}"}, method = RequestMethod.POST)
    public String getPointByKnowledgeId(@PathVariable("knowledgeId") int knowledgeId) throws JsonProcessingException { List knowledgePointList = knowledgePointService.getPointsByKnowledgeId(knowledgeId);
        return ReturnJacksonUtil.resultOk(knowledgePointList, Locale.CHINA); }

    @RequestMapping(value={"admin/add-point"}, method = RequestMethod.GET)
    public ModelAndView toAddPoint() {
        ModelAndView view = new ModelAndView("admin/add-point");
        List knowledgeList = knowledgeService.getAllKnowledge();
        view.addObject("knowledgeList", knowledgeList);
        return view;
    }
    @RequestMapping(value={"admin/add-point"}, method = RequestMethod.POST)
    @ResponseBody
    public String addPoint(@RequestBody KnowledgePoint knowledgePoint) throws JsonProcessingException { knowledgePointService.addPoint(knowledgePoint);
        return ReturnJacksonUtil.resultOk();
    }

    @RequestMapping(value = "admin/changeKnowledgePointProperty",method = RequestMethod.POST)
    @ResponseBody
    public String changeProperty(@RequestBody KnowledgePoint knowledgePoint) throws JsonProcessingException {
        knowledgePointService.changeProperty(knowledgePoint);
        return ReturnJacksonUtil.resultOk();
    }
}