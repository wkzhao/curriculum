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
          +" select paper_id ,paper.name as paper_name,user_paper.create_time "
          +" from "+TABLE+",paper"
          +" where user_id = #{userId} "
          +" and paper_id = paper.id "
  )
   List<UserPaper> getUserPaperByUserId(@Param("userId") int userId);

    @Select(""
            +" select paper_id ,paper.name as paper_name,user_paper.content,user_paper.create_time "
            +" from "+TABLE+",paper"
            +" where user_id = #{userId} "
            +" and paper_id = paper.id "
            +" and paper_id = #{paperId} "
    )
    UserPaper getUserPaperByUserIdAndPaperId(@Param("userId") int userId,@Param("paperId") int paperId);


  @Insert(""
          +" insert ignore into "
          +TABLE+"(user_id,paper_id,content) "
          +" values (#{userId},#{paperId},#{content}) "
  )
   int addUserPaper(@Param("userId") int userId, @Param("paperId") int paperId,@Param("content") String content);

  @Select(""
          +" select count(1) "
          +" from "+TABLE
          +" where user_id = #{userId} "
          +" and paper_id = #{paperId} "
  )
   int getCountByUserAndPaper(@Param("userId") int userId, @Param("paperId") int paperId);
}
