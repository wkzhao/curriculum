package com.curriculum.service;

import com.curriculum.domain.Course;
import com.curriculum.domain.PageBean;
import java.util.List;

 public interface CourseService {
   int addCourse(Course course);

   List<Course> getCourseByPointId(int pointId, PageBean pageBean);

   int getCountByPointId(int pointId);

   Course getCourseById(int id);

   int changeCourseInfo(Course course);
}
