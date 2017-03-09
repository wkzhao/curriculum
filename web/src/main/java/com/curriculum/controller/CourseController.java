package com.curriculum.controller;

import com.curriculum.domain.Course;
import com.curriculum.domain.PageBean;
import com.curriculum.domain.User;
import com.curriculum.interceptor.annotation.LoginRequired;
import com.curriculum.service.impl.CourseServiceImpl;
import com.curriculum.service.impl.KnowledgePointServiceImpl;
import com.curriculum.util.ReturnJacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletContext;
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
    CourseServiceImpl courseService;

    @Autowired
    KnowledgePointServiceImpl knowledgePointService;

    @RequestMapping(value={"admin/course-list/{currentPage}"}, method = RequestMethod.GET)
    public ModelAndView getCourseList(@RequestParam(value="pointId", required=false) String pointIdString, @PathVariable("currentPage") int currentPage)
    {
        ModelAndView view = new ModelAndView("admin/course-list");
        int pointId = pointIdString == null ? 0 : Integer.parseInt(pointIdString);
        int count = courseService.getCountByPointId(pointId);
        PageBean pageBean = new PageBean(currentPage, 10, count);
        List courseList = courseService.getCourseByPointId(pointId, pageBean);
        view.addObject("courseList", courseList);
        view.addObject("pageBean", pageBean);
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