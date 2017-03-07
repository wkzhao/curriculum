package com.curriculum.controller;

import com.curriculum.domain.KnowledgePoint;
import com.curriculum.service.impl.KnowledgePointServiceImpl;
import com.curriculum.service.impl.KnowledgeServiceImpl;
import com.curriculum.util.ReturnJacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class KnowledgePointController
{

    @Autowired
    KnowledgePointServiceImpl knowledgePointService;

    @Autowired
    KnowledgeServiceImpl knowledgeService;

    @RequestMapping(value={"admin/point-list-{knowledgeId}-{pageId}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView toPointList(@PathVariable("knowledgeId") int knowledgeId)
    {
        ModelAndView view = new ModelAndView("admin/point-list");
        List knowledgePointList = null;
        if (knowledgeId == 0)
            knowledgePointList = this.knowledgePointService.getAllPoints();
        else {
            knowledgePointList = this.knowledgePointService.getPointsByKnowledgeId(knowledgeId);
        }
        view.addObject("knowledgePointList", knowledgePointList);
        List knowledgeList = this.knowledgeService.getAllKnowledge();
        view.addObject("knowledgeList", knowledgeList);
        view.addObject("knowledgeId", Integer.valueOf(1));
        return view;
    }
    @ResponseBody
    @RequestMapping(value={"admin/get-knowledge-point/{knowledgeId}"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public String getPointByKnowledgeId(@PathVariable("knowledgeId") int knowledgeId) throws JsonProcessingException { List knowledgePointList = this.knowledgePointService.getPointsByKnowledgeId(knowledgeId);
        return ReturnJacksonUtil.resultOk(knowledgePointList, Locale.CHINA); }

    @RequestMapping(value={"admin/add-point"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView toAddPoint() {
        ModelAndView view = new ModelAndView("admin/add-point");
        List knowledgeList = this.knowledgeService.getAllKnowledge();
        view.addObject("knowledgeList", knowledgeList);
        return view;
    }
    @RequestMapping(value={"admin/add-point"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public String addPoint(@RequestBody KnowledgePoint knowledgePoint) throws JsonProcessingException { this.knowledgePointService.addPoint(knowledgePoint);
        return ReturnJacksonUtil.resultOk();
    }
}