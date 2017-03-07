package com.curriculum.controller;

import com.curriculum.domain.Knowledge;
import com.curriculum.service.impl.KnowledgePointServiceImpl;
import com.curriculum.service.impl.KnowledgeServiceImpl;
import com.curriculum.util.ReturnJacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class KnowledgeController
{

    @Autowired
    KnowledgeServiceImpl knowledgeService;

    @Autowired
    KnowledgePointServiceImpl knowledgePointService;

    @RequestMapping(value={"admin/knowledge-list"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView toKnowledgeList()
    {
        ModelAndView view = new ModelAndView("admin/knowledge-list");
        List knowledgeList = this.knowledgeService.getAllKnowledge();
        view.addObject("knowledgeList", knowledgeList);
        return view;
    }
    @RequestMapping(value={"admin/add-knowledge"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView toAddKnowledge() {
        ModelAndView view = new ModelAndView("admin/add-knowledge");
        return view;
    }
    @RequestMapping(value={"admin/add-knowledge"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public String addKnowledge(@RequestBody Knowledge knowledge) throws JsonProcessingException { this.knowledgeService.addKnowledge(knowledge);
        return ReturnJacksonUtil.resultOk();
    }
}