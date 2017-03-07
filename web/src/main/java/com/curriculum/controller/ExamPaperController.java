package com.curriculum.controller;

import com.curriculum.constant.WebCodeEnum;
import com.curriculum.domain.Exampaper;
import com.curriculum.domain.Question;
import com.curriculum.domain.User;
import com.curriculum.service.impl.ExampaperServiceImpl;
import com.curriculum.service.impl.QuestionServiceImpl;
import com.curriculum.util.ExampaperUtil;
import com.curriculum.util.ReturnJacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExamPaperController
{

    @Autowired
    ExampaperServiceImpl exampaperService;

    @Autowired
    QuestionServiceImpl questionService;

    @RequestMapping(value={"admin/exampaper-list"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView toPaperList()
    {
        ModelAndView view = new ModelAndView("admin/exampaper-list");
        List exampaperList = this.exampaperService.getAllPapers();
        view.addObject("exampaperList", exampaperList);
        return view;
    }
    @RequestMapping(value={"admin/exampaper-add"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView toAddPaperPage() {
        ModelAndView view = new ModelAndView("admin/exampaper-add");
        return view;
    }
    @ResponseBody
    @RequestMapping(value={"admin/exampaper-add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public String addPaper(@RequestBody Exampaper exampaper, HttpSession session) throws JsonProcessingException { exampaper.setCreator(((User)session.getAttribute("user")).getUsername());
        this.exampaperService.addPaper(exampaper);
        System.out.println(exampaper.getId());
        return ReturnJacksonUtil.resultOk(); }

    @RequestMapping(value={"admin/exampaper-edit/{paperId}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView editPaper(@PathVariable("paperId") int paperId) {
        ModelAndView view = new ModelAndView("admin/exampaper-edit");
        Exampaper exampaper = this.exampaperService.getPaperById(paperId);
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
    @RequestMapping(value={"admin/exampaper-preview/{paperId}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView paperPreView(@PathVariable("paperId") int paperId) {
        ModelAndView view = new ModelAndView("admin/exampaper-preview");
        Exampaper exampaper = this.exampaperService.getPaperById(paperId);
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
    @RequestMapping(value={"admin/paper-delete"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public String paperDelete(@RequestBody int pageId) throws JsonProcessingException { int num = this.exampaperService.deletePaper(pageId);
        if (num != 1) {
            return ReturnJacksonUtil.resultWithFailed(WebCodeEnum.DELETE_PAPER_ERROR);
        }
        return ReturnJacksonUtil.resultOk(); }
    @ResponseBody
    @RequestMapping(value={"admin/changePaperStatus"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public String changePaperStatus(@RequestBody Map<String, String> map) throws JsonProcessingException {
        int paperId = Integer.parseInt((String)map.get("pageId"));
        int status = Integer.parseInt((String)map.get("status"));
        this.exampaperService.changePaperStatus(paperId, status);
        return ReturnJacksonUtil.resultOk();
    }
    @ResponseBody
    @RequestMapping(value={"admin/add-paper-question/{paperId}"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public String addPaperQuestion(@RequestBody Integer[] ids, @PathVariable("paperId") int paperId) throws JsonProcessingException { Exampaper exampaper = this.exampaperService.getPaperById(paperId);
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
        this.exampaperService.changePaperQuestionIds(questionIds, paperId);
        if ((addQuestionIds == null) || (addQuestionIds.size() == 0)) {
            return ReturnJacksonUtil.resultOk();
        }
        List questionList = this.questionService.getQuestionByIds(addQuestionIds);
        if ((content == null) || ("".equals(content)))
            content = ExampaperUtil.questionListToXml(questionList);
        else {
            content = content + "#" + ExampaperUtil.questionListToXml(questionList);
        }
        this.exampaperService.changePaperContent(content, paperId);
        return ReturnJacksonUtil.resultOk(); }
    @ResponseBody
    @RequestMapping(value={"admin/delete-paper-question"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public String addPaperQuestion(@RequestBody Map<String, String> map) throws JsonProcessingException {
        int paperId = Integer.parseInt((String)map.get("paperId"));
        Exampaper exampaper = this.exampaperService.getPaperById(paperId);
        String questionIds = exampaper.getQuestionIds();
        String content = exampaper.getContent();
        String questionId = (String)map.get("questionId");
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
        this.exampaperService.changePaperQuestionIds(questionIds, paperId);
        this.exampaperService.changePaperContent(content, paperId);
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