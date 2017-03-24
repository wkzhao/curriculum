package com.curriculum.dao;

import com.curriculum.domain.Exampaper;
import java.util.List;

import com.curriculum.domain.PageBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PaperDao
{
   String TABLE = "paper";


    @Select(" <script> "
            +" select id,name,time,creator,paper_type_id,status,question_ids  "
            +" from "+ TABLE
            +" <trim prefix=\"WHERE\" prefixoverride=\"AND |OR\"> "
            +" <if test=\"status != 0\"> status = #{status} </if> "
            +" </trim> "
            +" <if test = 'pageBean != null'> "
            +" limit #{pageBean.recordIndex},#{pageBean.pageSize} "
            +" </if> "
            +" </script> "
    )
    List<Exampaper>  getExamPaperByPage(@Param("status") int status, @Param("pageBean") PageBean pageBean);


    @Select(" <script> "
            +" select count(1) "
            +" from "+TABLE
            +" <if test = 'status != 0 '> "
            +" where status = #{status} "
            +" </if> "
            +" </script> "
    )
    int getPaperCount(@Param("status") int status);

  @Select(""
          +" select id,name,time,content,creator,paper_type_id,status,question_ids "
          +" from " +TABLE
          +" where id = #{id}"
  )
   Exampaper getPaperById(@Param("id") int id);

  @Select(""
          +" select id,name "
          +" from " +TABLE
          +" where status = 2 "
  )
   List<Exampaper> getSimplePapers();

  @Insert(""
          +" insert into "
          +TABLE+"(name,content,time,creator,paper_type_id,question_ids) "
          +" values (#{paper.name},#{paper.content},#{paper.time},#{paper.creator},#{paper.paperTypeId},#{paper.questionIds})"
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


  @Update(""
          +" update paper "
          +" set time = #{paper.time},name = #{paper.name} "
          +" where id = #{paper.id} "
  )
   int changePaperProperty(@Param("paper") Exampaper exampaper);
}

