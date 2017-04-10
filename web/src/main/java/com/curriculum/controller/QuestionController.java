package com.curriculum.controller;

import com.curriculum.constant.Constants;
import com.curriculum.constant.WebCodeEnum;
import com.curriculum.domain.Knowledge;
import com.curriculum.domain.PageBean;
import com.curriculum.domain.Question;
import com.curriculum.domain.QuestionFilter;
import com.curriculum.domain.User;
import com.curriculum.interceptor.annotation.LoginRequired;
import com.curriculum.service.impl.KnowledgePointServiceImpl;
import com.curriculum.service.impl.KnowledgeServiceImpl;
import com.curriculum.service.impl.QuestionServiceImpl;
import com.curriculum.service.impl.QuestionTypeServiceImpl;
import com.curriculum.util.POIReadExcelTool;
import com.curriculum.util.ReturnJacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @RequestMapping(value={"admin/question-list/{knowledgeId}-{knowledgePointId}-{questionType}-{searchParam}-{currentPage}"}, method = RequestMethod.GET)
    public ModelAndView toQuestionList(@PathVariable("knowledgeId") long knowledgeId, @PathVariable("knowledgePointId") String knowledgePointId, @PathVariable("questionType") long questionType, @PathVariable("searchParam") int searchParam, @PathVariable("currentPage") int currentPage)
    {
        ModelAndView view = new ModelAndView("admin/question-list");
        if (searchParam == 1) {
            view = new ModelAndView("admin/paper-add-question");
        }
        QuestionFilter questionFilter = new QuestionFilter(knowledgeId, knowledgePointId, questionType);
        int questionCount = questionService.getQuestionCount();
        PageBean pageBean = new PageBean(currentPage, Constants.PAGE_SIZE, questionCount);
        List questionList = questionService.getQuestionsByFilter(questionFilter, pageBean);
        view.addObject("questionList", questionList);
        List knowledgeList = knowledgeService.getAllKnowledge();
        view.addObject("knowledgeList", knowledgeList);
        List knowledgePointList = knowledgePointService.getPointsByPage(0,null);
        view.addObject("knowledgePointList", knowledgePointList);
        List questionTypeList = questionTypeService.getAllTypes();
        view.addObject("questionTypeList", questionTypeList);
        view.addObject("questionFilter", questionFilter);
        view.addObject("pageBean", pageBean);
        return view;
    }
    @RequestMapping(value={"admin/question-add"}, method = RequestMethod.GET)
    public ModelAndView toAddQuestion() {
        ModelAndView view = new ModelAndView("admin/question-add");
        List questionTypeList = questionTypeService.getAllTypes();
        view.addObject("questionTypeList", questionTypeList);
        List knowledgeList = knowledgeService.getAllKnowledge();
        view.addObject("knowledgeList", knowledgeList);
        return view;
    }
    @ResponseBody
    @RequestMapping(value={"admin/question-add"}, method = RequestMethod.POST)
    public String addQuestion(@RequestBody Question question, HttpSession session) throws JsonProcessingException {
        question.setCreator(((User)session.getAttribute("user")).getUsername());
        questionService.addQuestion(question);
        return ReturnJacksonUtil.resultOk();
    }

    @RequestMapping(value={"admin/question-preview/{questionId}"}, method = RequestMethod.GET)
    public ModelAndView toQuestionPreview(@PathVariable("questionId") int questionId) {
        ModelAndView view = new ModelAndView("admin/question-preview");
        Question question = questionService.getQuestionById(questionId);
        view.addObject("question", question);
        return view;
    }
    @ResponseBody
    @RequestMapping(value={"/admin/delete-question/{questionId}"}, method = RequestMethod.POST)
    public String deleteQuestion(@PathVariable("questionId") int questionId) throws JsonProcessingException { int num = questionService.deleteQuestionById(questionId);
        if (num != 1) {
            return ReturnJacksonUtil.resultWithFailed(WebCodeEnum.DELETE_QUESTION_ERROR);
        }
        return ReturnJacksonUtil.resultOk();
    }

    @RequestMapping(value = "admin/question-import",method = RequestMethod.GET)
    public ModelAndView toQuestionImport(){
        ModelAndView view = new ModelAndView("admin/question-import");
        List<Knowledge> knowledgeList = knowledgeService.getAllKnowledge();
        view.addObject("knowledgeList",knowledgeList);
        return view;
    }

    @RequestMapping(value = "admin/question-import",method = RequestMethod.POST)
    @ResponseBody
    public String questionImport(@RequestParam("upload") MultipartFile file,HttpSession session) throws Exception {
        List<Question> questionList = POIReadExcelTool.readXls(file.getInputStream());
        for( Question question : questionList){
            question.setCreator(((User) session.getAttribute("user")).getUsername());
        }
        int code = questionService.addQuestionList(questionList);
        if(code == -1){
           return ReturnJacksonUtil.resultWithFailed(WebCodeEnum.UNKNOWN_ERROR);
        }
        return ReturnJacksonUtil.resultOk();
    }
}