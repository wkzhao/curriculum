package com.curriculum.service.impl;

import com.curriculum.constant.KnowledgePointEnum;
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
        return this.courseDao.addCourse(course);
    }

    public List<Course> getCourseByPointId(int pointId, PageBean pageBean)
    {
        List<Course> courseList = this.courseDao.getCourseByPointId(pointId, pageBean);
        for (Course course : courseList) {
            course.setKnowledgePointName(KnowledgePointEnum.getEnumById(course.getKnowledgePointId() + "").getTypeName());
        }
        return courseList == null ? Collections.emptyList() : courseList;
    }

    public int getCountByPointId(int pointId)
    {
        return this.courseDao.getCountByPointId(pointId);
    }

    public Course getCourseById(int id)
    {
        Course course = this.courseDao.getCourseById(id);
        if (course != null) {
            course.setKnowledgePointName(KnowledgePointEnum.getEnumById(course.getKnowledgePointId() + "").getTypeName());
            return course;
        }
        return null;
    }

    @Override
    public int changeCourseInfo(Course course) {
        return courseDao.changeCourseInfo(course);
    }
}