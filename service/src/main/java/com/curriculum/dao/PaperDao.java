package com.curriculum.dao;

import com.curriculum.domain.Exampaper;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PaperDao
{
   String TABLE = "paper";

  @Select(""
          +" select id,name,paper_point,pass_point,time,content,creator,paper_type_id,status,question_ids  "
          +" from " +TABLE
  )
   List<Exampaper> getAllPapers();

  @Select(""
          +" select id,name,paper_point,pass_point,time,content,creator,paper_type_id,status,question_ids "
          +" from " +TABLE
          +" where id = #{id}"
  )
   Exampaper getPaperById(@Param("id") int id);

  @Select(""
          +" select id,name "
          +" from " +TABLE
  )
   List<Exampaper> getSimplePapers();

  @Insert(""
          +" insert into "
          +" paper (name,paper_point,pass_point,content,time,creator,paper_type_id) "
          +" values (#{paper.name},#{paper.paperPoint},#{paper.passPoint},#{paper.content},#{paper.time},#{paper.creator},#{paper.paperTypeId})"
  )
  @Options(useGeneratedKeys=true, keyProperty="paper.id")
   int addPaper(@Param("paper") Exampaper exampaper);

  @Delete(""
          +" delete  "
          +" from " +TABLE
          +" where id = #{id}"
  )
   int deletePaper(@Param("id") int id);

  @Update(""
          +" update paper "
          +" set status = #{status} "
          +" where id =#{id}"
  )
   int changePaperStatus(@Param("id") int id, @Param("status") int status);

  @Update(""
          +" update paper "
          +" set content = #{content} "
          +" where id = #{id}"
  )
   int changePaperContent(@Param("content") String content, @Param("id") int id);

  @Update(""
          +" update paper "
          +" set question_ids = #{questionIds} "
          +" where id = #{id} "
  )
   int changePaperQuestionIds(@Param("questionIds") String questionsIds, @Param("id") int id);
}

