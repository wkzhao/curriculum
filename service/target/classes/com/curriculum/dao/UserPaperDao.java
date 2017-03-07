package com.curriculum.dao;

import com.curriculum.domain.UserPaper;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserPaperDao
{
   String TABLE = "user_paper";

  @Select(""
          +" select paper_id ,paper.name "
          +" from "+TABLE
          +" where user_id = #{userId} "
          +" and paper_id = paper.id "
  )
   List<UserPaper> getUserPaperByUserId(@Param("userId") int userId);

  @Insert(""
          +" insert ignore into "
          +TABLE+"(user_id,paper_id) "
          +" values (#{userId},#{paperId}) "
  )
   int addUserPaper(@Param("userId") int userId, @Param("paperId") int paperId);

  @Select(""
          +" select count(1) "
          +" from "+TABLE
          +" where user_id = #{userId} "
  )
   int getCountByUserAndPaper(@Param("userId") int userId, @Param("paperId") int paperId);
}
