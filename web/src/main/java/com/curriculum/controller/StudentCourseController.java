package com.curriculum.controller;

import com.curriculum.constant.Constants;
import com.curriculum.domain.Course;
import com.curriculum.domain.KnowledgePoint;
import com.curriculum.domain.PageBean;
import com.curriculum.service.impl.CourseServiceImpl;
import com.curriculum.service.impl.KnowledgePointServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class StudentCourseController {

    @Autowired
    CourseServiceImpl courseService;

    @Autowired
    KnowledgePointServiceImpl knowledgePointService;

    @RequestMapping(value = "student/course-list-{knowledgePointId}-{currentPage}",method = RequestMethod.GET)
    public ModelAndView courseList(@PathVariable("knowledgePointId") int knowledgePointId,@PathVariable("currentPage") int currentPage){
        ModelAndView view = new ModelAndView("student/course-list");
        int count = courseService.getCountByPointId(knowledgePointId);
        PageBean pageBean = new PageBean(currentPage, Constants.PAGE_SIZE,count);
        List<Course> courseList = courseService.getCourseByPointId(knowledgePointId,pageBean);
        view.addObject("courseList",courseList);
        List<KnowledgePoint> knowledgePointList = knowledgePointService.getPointsByPage(0,null);
        view.addObject("knowledgePointList",knowledgePointList);
        view.addObject("pageBean",pageBean);
        view.addObject("knowledgePointId",knowledgePointId);
        return  view;
    }

    @RequestMapping(value = "student/course-view/{courseId}",method = RequestMethod.GET)
    public ModelAndView courseList(@PathVariable("courseId") int courseId){
        ModelAndView view = new ModelAndView("student/course-view");
        Course course = courseService.getCourseById(courseId);
        view.addObject("course",course);
        return  view;
    }
}
