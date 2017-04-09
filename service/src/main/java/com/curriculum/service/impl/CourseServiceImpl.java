package com.curriculum.service.impl;

import com.curriculum.dao.CourseDao;
import com.curriculum.domain.Course;
import com.curriculum.domain.PageBean;
import com.curriculum.service.CourseService;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl
        implements CourseService
{

    @Autowired
    CourseDao courseDao;

    public int addCourse(Course course)
    {
        return courseDao.addCourse(course);
    }

    public List<Course> getCourseByPointId(int pointId, PageBean pageBean)
    {
        List<Course> courseList = courseDao.getCourseByPointId(pointId, pageBean);
        return courseList == null ? Collections.emptyList() : courseList;
    }

    public int getCountByPointId(int pointId)
    {
        return courseDao.getCountByPointId(pointId);
    }

    public Course getCourseById(int id)
    {
        Course course = courseDao.getCourseById(id);
        return course;
    }

    @Override
    public int changeCourseInfo(Course course) {
        return courseDao.changeCourseInfo(course);
    }
}