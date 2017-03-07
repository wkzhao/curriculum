package com.curriculum.dao;

import com.curriculum.domain.UserQuestion;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserQuestionDao
{
   String TABLE = "user_question";

  @Insert(" <script> "
          +" insert ignore into "
          +TABLE+"(user_id,question_id,wrong_or_right,user_answer,question_points) "
          +" values  "
          +" <foreach item='item' collection='userQuestions' index = 'index' separator=','> "
          +" (#{item.userId},#{item.questionId},#{item.wrongOrRight},#{item.userAnswer},#{item.questionPoints}) "
          +" </foreach>"
          +" </script> "
  )
   int addUserQuestions(@Param("userQuestions") List<UserQuestion> paramList, @Param("id") Integer paramInteger);

  @Select(""
          +" select question_id,user_answer "
          +" from "+TABLE
          +" where user_id = #{userId} "
          +" and question_points like '%,${points},%' "
          +" and wrong_or_right = 0 "
  )
   List<Integer> getErrorQuestionIds(@Param("userId") int paramInt, @Param("points") String paramString);

  @Select(""
          +" select count(wrong_or_right) "
          +" from "+TABLE
          +" where user_id = #{userId} "
          +" and question_points like '%,${pointId},%' "
          +" and wrong_or_right = #{wrong_or_right} "
  )
   int getCountByPointId(@Param("pointId") String paramString, @Param("userId") int userId, @Param("wrong_or_right") int wrongOrRight);
}

