package com.curriculum.dao;

import com.curriculum.domain.Course;
import com.curriculum.domain.PageBean;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface CourseDao {
   String TABLE = "course";

  @Insert(""
          +" insert into "
          +TABLE+" (content,knowledge_point_id,title,creator,video_url) "
          +" values(#{course.content},#{course.knowledgePointId},#{course.title},#{course.creator},#{course.videoUrl}) "
  )
  @Options(useGeneratedKeys=true, keyProperty="course.id")
   int addCourse(@Param("course") Course course);

  @Select(""
          +" select id,content,knowledge_point_id,title,creator,create_time,video_url  "
          +" from  " +TABLE
          +" where id = #{id} "
  )
   Course getCourseById(@Param("id") int id);

    @Select("<script>"
            +" select count(1) "
            +" from "+TABLE
            +" <if test = 'pointId != 0'>"
            +" where knowledge_point_id = #{pointId}"
            +"</if>"
            +"</script>"
    )
  int getCountByPointId(@Param("pointId") int pointId);

  @Select(" <script> "
          +" select id,content,knowledge_point_id,title,create_time,creator,video_url " +
          " from "+TABLE
          +" <if test = 'pointId != 0'> where knowledge_point_id = #{pointId}  </if>  "
          +" limit #{pageBean.recordIndex},#{pageBean.pageSize} "
          +" </script> "
  )
   List<Course> getCourseByPointId(@Param("pointId") int pointId, @Param("pageBean") PageBean pageBean);

  @Update(""
          +" update " +TABLE
          +" set title = #{course.title}, knowledge_point_id = #{course.knowledgePointId} "
          +" where id = #{course.id} "
  )
  int changeCourseInfo(@Param("course") Course course);
}
