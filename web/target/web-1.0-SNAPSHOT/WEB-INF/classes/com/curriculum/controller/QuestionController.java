package com.curriculum.controller;

import com.curriculum.constant.WebCodeEnum;
import com.curriculum.domain.PageBean;
import com.curriculum.domain.Question;
import com.curriculum.domain.QuestionFilter;
import com.curriculum.domain.User;
import com.curriculum.interceptor.annotation.LoginRequired;
import com.curriculum.service.impl.KnowledgePointServiceImpl;
import com.curriculum.service.impl.KnowledgeServiceImpl;
import com.curriculum.service.impl.QuestionServiceImpl;
import com.curriculum.service.impl.QuestionTypeServiceImpl;
import com.curriculum.util.ReturnJacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
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
public class QuestionController
{

    @Autowired
    QuestionServiceImpl questionService;

    @Autowired
    KnowledgeServiceImpl knowledgeService;

    @Autowired
    KnowledgePointServiceImpl knowledgePointService;

    @Autowired
    QuestionTypeServiceImpl questionTypeService;

    @RequestMapping(value={"admin/question-list/{knowledgeId}-{knowledgePointId}-{questionType}-{searchParam}-{currentPage}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView toQuestionList(@PathVariable("knowledgeId") long knowledgeId, @PathVariable("knowledgePointId") String knowledgePointId, @PathVariable("questionType") long questionType, @PathVariable("searchParam") int searchParam, @PathVariable("currentPage") int currentPage)
    {
        ModelAndView view = new ModelAndView("admin/question-list");
        if (searchParam == 1) {
            view = new ModelAndView("admin/paper-add-question");
        }
        QuestionFilter questionFilter = new QuestionFilter(knowledgeId, knowledgePointId, questionType);
        int questionCount = this.questionService.getQuestionCount();
        PageBean pageBean = new PageBean(currentPage, 10, questionCount);
        List questionList = this.questionService.getQuestionsByFilter(questionFilter, pageBean);
        view.addObject("questionList", questionList);
        List knowledgeList = this.knowledgeService.getAllKnowledge();
        view.addObject("knowledgeList", knowledgeList);
        List knowledgePointList = this.knowledgePointService.getAllPoints();
        view.addObject("knowledgePointList", knowledgePointList);
        List questionTypeList = this.questionTypeService.getAllTypes();
        view.addObject("questionTypeList", questionTypeList);
        view.addObject("questionFilter", questionFilter);
        view.addObject("pageBean", pageBean);
        return view;
    }
    @RequestMapping(value={"admin/question-add"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView toAddQuestion() {
        ModelAndView view = new ModelAndView("admin/question-add");
        List questionTypeList = this.questionTypeService.getAllTypes();
        view.addObject("questionTypeList", questionTypeList);
        List knowledgeList = this.knowledgeService.getAllKnowledge();
        view.addObject("knowledgeList", knowledgeList);
        return view;
    }
    @ResponseBody
    @RequestMapping(value={"admin/question-add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public String addQuestion(@RequestBody Question question, HttpSession session) throws JsonProcessingException { question.setCreator(((User)session.getAttribute("user")).getUsername());
        this.questionService.addQuestion(question);
        return ReturnJacksonUtil.resultOk(); }

    @RequestMapping(value={"admin/question-preview/{questionId}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView toQuestionPreview(@PathVariable("questionId") int questionId) {
        ModelAndView view = new ModelAndView("admin/question-preview");
        Question question = this.questionService.getQuestionById(questionId);
        view.addObject("question", question);
        return view;
    }
    @ResponseBody
    @RequestMapping(value={"/admin/delete-question/{questionId}"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public String deleteQuestion(@PathVariable("questionId") int questionId) throws JsonProcessingException { int num = this.questionService.deleteQuestionById(questionId);
        if (num != 1) {
            return ReturnJacksonUtil.resultWithFailed(WebCodeEnum.DELETE_QUESTION_ERROR);
        }
        return ReturnJacksonUtil.resultOk(); }
    @RequestMapping(value={"admin/question-import"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView toQuestionImport() {
        return new ModelAndView("admin/question-import");
    }
    @RequestMapping(value={"admin/question-import"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public String questionImport() {
        return "";
    }
}