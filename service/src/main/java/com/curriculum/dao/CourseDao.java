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
          +" select course.id,content,knowledge_point.name as knowledge_point_name,knowledge_point_id,title,creator,course.create_time,video_url  "
          +" from  " +TABLE+",knowledge_point "
          +" where course.id = #{id} "
          +" and knowledge_point_id = knowledge_point.id "
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
          +" select course.id,content,knowledge_point_id,knowledge_point.name as knowledge_point_name,title,course.create_time,creator,video_url "
          +" from "+TABLE+",knowledge_point "
          +" where knowledge_point.id = knowledge_point_id "
          +" <if test = 'pointId != 0'> and knowledge_point_id = #{pointId}  </if>  "
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
