package com.curriculum.controller;

import com.curriculum.constant.Constants;
import com.curriculum.constant.WebCodeEnum;
import com.curriculum.domain.Exampaper;
import com.curriculum.domain.PageBean;
import com.curriculum.domain.Question;
import com.curriculum.domain.User;
import com.curriculum.service.impl.ExampaperServiceImpl;
import com.curriculum.service.impl.QuestionServiceImpl;
import com.curriculum.util.ExampaperUtil;
import com.curriculum.util.ReturnJacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.PrintStream;
import java.util.*;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExamPaperController
{

    @Autowired
    ExampaperServiceImpl exampaperService;

    @Autowired
    QuestionServiceImpl questionService;

    @RequestMapping(value={"admin/exampaper-list-{paperStatus}-{currentPage}"}, method = RequestMethod.GET)
    public ModelAndView toPaperList(@PathVariable("paperStatus") int status,@PathVariable("currentPage") int currentPage)
    {
        ModelAndView view = new ModelAndView("admin/exampaper-list");
        int recordCount = exampaperService.getPaperCount(status);
        PageBean pageBean = new PageBean(currentPage, Constants.PAGE_SIZE,recordCount);
        List exampaperList = exampaperService.getExamPaperByPage(status,pageBean);
        view.addObject("exampaperList", exampaperList);
        view.addObject("pageBean",pageBean);
        view.addObject("status",status);
        return view;
    }
    @RequestMapping(value={"admin/exampaper-add"}, method = RequestMethod.GET)
    public ModelAndView toAddPaperPage() {
        ModelAndView view = new ModelAndView("admin/exampaper-add");
        return view;
    }
    @ResponseBody
    @RequestMapping(value={"admin/exampaper-add"}, method = RequestMethod.POST)
    public String addPaper(@RequestBody Exampaper exampaper, HttpSession session) throws JsonProcessingException {
        exampaper.setCreator(((User)session.getAttribute("user")).getUsername());
        exampaperService.addPaper(exampaper);
        return ReturnJacksonUtil.resultOk(exampaper, Locale.CHINA);
    }

    @RequestMapping(value={"admin/exampaper-edit/{paperId}"}, method = RequestMethod.GET)
    public ModelAndView editPaper(@PathVariable("paperId") int paperId) {
        ModelAndView view = new ModelAndView("admin/exampaper-edit");
        Exampaper exampaper = exampaperService.getPaperById(paperId);
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
        return view;
    }
    @RequestMapping(value={"admin/exampaper-preview/{paperId}"}, method = RequestMethod.GET)
    public ModelAndView paperPreView(@PathVariable("paperId") int paperId) {
        ModelAndView view = new ModelAndView("admin/exampaper-preview");
        Exampaper exampaper = exampaperService.getPaperById(paperId);
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
        return view;
    }
    @ResponseBody
    @RequestMapping(value={"admin/paper-delete"}, method = RequestMethod.POST)
    public String paperDelete(@RequestBody int paperId) throws JsonProcessingException {
        Exampaper exampaper = exampaperService.getPaperById(paperId);
        if ( exampaper == null ) {
            return ReturnJacksonUtil.resultWithFailed(WebCodeEnum.NO_PAPER_ERROR);
        }else if( exampaper.getStatus() != 0 ){
            return ReturnJacksonUtil.resultWithFailed(WebCodeEnum.DELETE_PAPER_ERROR);
        }else{
            exampaperService.deletePaper(paperId);
        }
        return ReturnJacksonUtil.resultOk();
    }

    @ResponseBody
    @RequestMapping(value={"admin/changePaperStatus"}, method = RequestMethod.POST)
    public String changePaperStatus(@RequestBody Map<String, String> map) throws JsonProcessingException {
        int paperId = Integer.parseInt(map.get("paperId"));
        int status = Integer.parseInt(map.get("status"));
        exampaperService.changePaperStatus(paperId, status);
        return ReturnJacksonUtil.resultOk();
    }

    @ResponseBody
    @RequestMapping(value = "admin/changePaperProperty",method = RequestMethod.POST)
    public String changePaperProperty(@RequestBody Exampaper exampaper) throws JsonProcessingException {
        exampaperService.changePaperProperty(exampaper);
        return  ReturnJacksonUtil.resultOk();
    }


    @ResponseBody
    @RequestMapping(value={"admin/add-paper-question/{paperId}"}, method = RequestMethod.POST)
    public String addPaperQuestion(@RequestBody Integer[] ids, @PathVariable("paperId") int paperId) throws JsonProcessingException { Exampaper exampaper = exampaperService.getPaperById(paperId);
        String questionIds = exampaper.getQuestionIds();
        String content = exampaper.getContent();
        List<Integer> addQuestionIds = new ArrayList();
        if ((questionIds == null) || ("".equals(questionIds))) {
            addQuestionIds.addAll(Arrays.asList(ids));
            questionIds = "";
        } else {
            questionIds = questionIds.substring(0, questionIds.length() - 1);
            String[] qIds = questionIds.split(",");
            for (int i = 0; i < ids.length; i++) {
                if (findStrInArr(qIds, ids[i] + "") == -1) {
                    addQuestionIds.add(ids[i]);
                }
            }
        }
        for (int id : addQuestionIds ) {
            questionIds = questionIds + "," + id;
        }
        questionIds = questionIds + ",";
        exampaperService.changePaperQuestionIds(questionIds, paperId);
        if ((addQuestionIds == null) || (addQuestionIds.size() == 0)) {
            return ReturnJacksonUtil.resultOk();
        }
        List questionList = questionService.getQuestionByIds(addQuestionIds);
        if ((content == null) || ("".equals(content)))
            content = ExampaperUtil.questionListToXml(questionList);
        else {
            content = content + "#" + ExampaperUtil.questionListToXml(questionList);
        }
        exampaperService.changePaperContent(content, paperId);
        return ReturnJacksonUtil.resultOk();
    }

    @ResponseBody
    @RequestMapping(value={"admin/delete-paper-question"}, method = RequestMethod.POST)
    public String addPaperQuestion(@RequestBody Map<String, String> map) throws JsonProcessingException {
        int paperId = Integer.parseInt((String)map.get("paperId"));
        Exampaper exampaper = exampaperService.getPaperById(paperId);
        String questionIds = exampaper.getQuestionIds();
        String content = exampaper.getContent();
        String questionId = map.get("questionId");
        List questionList = ExampaperUtil.xmlToQuestionList(content);
        ListIterator questionListIterator = questionList.listIterator();

        while (questionListIterator.hasNext()) {
            Question question = (Question)questionListIterator.next();
            if (question.getId() == Integer.parseInt(questionId)) {
                questionListIterator.remove();
            }
        }
        content = ExampaperUtil.questionListToXml(questionList);
        questionIds = questionIds.replace("," + questionId + ",", ",");
        exampaperService.changePaperQuestionIds(questionIds, paperId);
        exampaperService.changePaperContent(content, paperId);
        return ReturnJacksonUtil.resultOk();
    }

    private int findStrInArr(String[] arr, String str)
    {
        if ((arr == null) || (str == null)) {
            return -1;
        }
        for (int i = 0; i < arr.length; i++) {
            if (str.equals(arr[i])) {
                return i;
            }
        }
        return -1;
    }
}