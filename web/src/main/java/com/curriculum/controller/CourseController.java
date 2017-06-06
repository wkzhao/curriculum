package com.curriculum.controller;

import com.curriculum.constant.WebCodeEnum;
import com.curriculum.domain.Course;
import com.curriculum.domain.KnowledgePoint;
import com.curriculum.domain.PageBean;
import com.curriculum.domain.User;
import com.curriculum.interceptor.annotation.LoginRequired;
import com.curriculum.service.CourseService;
import com.curriculum.service.KnowledgePointService;
import com.curriculum.util.ReturnJacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@LoginRequired
public class CourseController
{

    @Autowired
    CourseService courseService;

    @Autowired
    KnowledgePointService knowledgePointService;

    @RequestMapping(value={"admin/course-list-{knowledgePointId}-{currentPage}"}, method = RequestMethod.GET)
    public ModelAndView getCourseList(@PathVariable("knowledgePointId") int knowledgePointId, @PathVariable("currentPage") int currentPage)
    {
        ModelAndView view = new ModelAndView("admin/course-list");
        int count = courseService.getCountByPointId(knowledgePointId);
        PageBean pageBean = new PageBean(currentPage, 10, count);
        List courseList = courseService.getCourseByPointId(knowledgePointId, pageBean);
        view.addObject("courseList", courseList);
        List<KnowledgePoint> knowledgePointList = knowledgePointService.getPointsByPage(0,null);
        view.addObject("knowledgePointList",knowledgePointList);
        view.addObject("pageBean", pageBean);
        view.addObject("knowledgePointId",knowledgePointId);
        return view;
    }

    @RequestMapping(value={"admin/add-course"}, method = RequestMethod.GET)
    public ModelAndView toAddCourse() {
        ModelAndView view = new ModelAndView("admin/add-course");
        List knowledgePointList = knowledgePointService.getPointsByPage(0,null);
        view.addObject("knowledgePointList", knowledgePointList);
        return view;
    }
    @RequestMapping(value={"admin/add-course"}, method = RequestMethod.POST)
    @ResponseBody
    public String addCourse(@RequestBody Course course, HttpSession session) throws JsonProcessingException { User user = (User)session.getAttribute("user");
        course.setCreator(user.getUsername());
        courseService.addCourse(course);
        return ReturnJacksonUtil.resultOk(); }

    @RequestMapping(value={"admin/course-preview/{courseId}"}, method = RequestMethod.GET)
    public ModelAndView coursePreview(@PathVariable("courseId") int courseId) {
        ModelAndView view = new ModelAndView("admin/course-preview");
        Course course = courseService.getCourseById(courseId);
        view.addObject("course", course);
        return view;
    }

    @RequestMapping(value={"admin/course-preview/{courseId}"}, method = RequestMethod.POST)
    @ResponseBody
    public String getCourseById(@PathVariable("courseId") int courseId) throws JsonProcessingException {
        Course course = courseService.getCourseById(courseId);
        return ReturnJacksonUtil.resultOk(course,Locale.CHINA);
    }

    @RequestMapping(value={"admin/changeCourseInfo"}, method = RequestMethod.POST)
    @ResponseBody
    public String changeCourseInfo(@RequestBody Course course) throws JsonProcessingException {
        int result = courseService.changeCourseInfo(course);
        if( result != 1 ){
            return ReturnJacksonUtil.resultWithFailed(WebCodeEnum.UNKNOWN_ERROR);
        }
        return ReturnJacksonUtil.resultOk();
    }


    @RequestMapping(value={"admin/course-upload-video"}, method = RequestMethod.POST)
    @ResponseBody
    public String videoUpload(@RequestParam("upload") MultipartFile file, HttpServletRequest request ) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/") + "resources" + File.separator + "upload" + File.separator + "course";
        String fileName = file.getOriginalFilename();
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        file.transferTo(targetFile);
        return ReturnJacksonUtil.resultOk(path + File.separator + fileName, Locale.CHINA);
    }

    @RequestMapping(value = "admin/course-upload-image",method = RequestMethod.POST)
    @ResponseBody
    public void imageUpload(@RequestParam("upload") MultipartFile file,
                             @RequestParam("url") String url,
                             HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = file.getOriginalFilename();
        response.reset();
        PrintWriter out = response.getWriter();
        String path = request.getSession().getServletContext().getRealPath("/") + "resources" + File.separator + "upload" + File.separator + "course";
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        file.transferTo(targetFile);
        String callback = request.getParameter("CKEditorFuncNum");
        out.println("<script type=\"text/javascript\">");
        out.println("window.parent.CKEDITOR.tools.callFunction("
                + callback + ",'"
                +url+"resources/upload/course/"+ fileName + "','')");
        out.println("</script>");
        out.flush();
        out.close();
    }
}