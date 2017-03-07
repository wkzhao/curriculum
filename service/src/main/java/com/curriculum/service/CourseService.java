package com.curriculum.service;

import com.curriculum.domain.Course;
import com.curriculum.domain.PageBean;
import java.util.List;

 public interface CourseService {
   int addCourse(Course paramCourse);

   List<Course> getCourseByPointId(int paramInt, PageBean paramPageBean);

   int getCountByPointId(int paramInt);

   Course getCourseById(int paramInt);
}
