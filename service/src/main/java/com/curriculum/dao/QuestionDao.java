package com.curriculum.dao;

import com.curriculum.domain.PageBean;
import com.curriculum.domain.Question;
import com.curriculum.domain.QuestionFilter;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface QuestionDao
{
   String TABLE = "question";

  @Insert(""
          +" insert into question (name,answer,content,analysis,creator,points,question_type_id) "
          +" values(#{question.name},#{question.answer},#{question.content},#{question.analysis},#{question.creator},#{question.points},#{question.questionTypeId}) "
  )
  @Options(useGeneratedKeys=true, keyProperty="question.id")
   int addQuestion(@Param("question") Question question);

  @Select(""
          +" select count(1) "
          +" from "+ TABLE
          +" where points like '%,${pointId},%' "
  )
   int getCountByPoints(@Param("pointId") String pointId);

  @Select(""
          +" select count(1) "
          +" from "+ TABLE
  )
   int getQuestionCount();

  @Select(""
          +" select  question.id,points,question.name,answer,content,analysis,creator,question_type_id,question_type.name as question_type_name "
          +" from "+ TABLE+",question_type "
          +" where question.id = #{id} "
          +" and question_type_id = question_type.id "
  )
   Question getQuestionById(@Param("id") int id);

  @Select(""
          +" select  answer "
          +" from "+ TABLE
          +" where id = #{id} "
  )
   String getQuestionAnswerById(@Param("id") int id);

  @Select(""
          +" select  question.id,points,question.name,answer,content,analysis,creator,question_type_id,question_type.name as question_type_name "
          +" from "+ TABLE+" ,question_type "
          +" where question_type_id = question_type.id "
  )
   List<Question> getAllQuestions();

  @Select(""
          +" select  question.id,points,question.name,answer,content,analysis,creator,question_type_id,question_type.name as question_type_name "
          +" from "+ TABLE+",question_type "
          +" where question_type_id = question_type.id "
          +" and knowledge = #{knowledge}")
   List<Question> getQuestionByKnowledge(@Param("knowledge") String knowledge);

  @Select(" <script> "
          +" select question.id,points,question.name,answer,content,analysis,creator,question_type_id,question_type.name as question_type_name "
          +" from "+ TABLE+",question_type "
          +" <trim prefix=\"WHERE\" prefixoverride=\"AND |OR\"> "
          +" question_type_id = question_type.id "
          +" <if test=\"questionFilter.knowledgePointId != 0\"> AND points like '%,${questionFilter.knowledgePointId},%'</if> "
          +" <if test=\"questionFilter.questionTypeId != 0\"> AND question_type_id = #{questionFilter.questionTypeId}</if> "
          +" <if test=\"questionFilter.knowledgeId != 0\"> AND knowledge_id = #{questionFilter.knowledgeId}</if> "
          +" </trim> "
          +" <if test = 'pageBean != null'> "
          +" limit #{pageBean.recordIndex},#{pageBean.pageSize} "
          +" </if> "
          +" </script> ")
   List<Question> getQuestionsByFilter(@Param("questionFilter") QuestionFilter paramQuestionFilter, @Param("pageBean") PageBean paramPageBean);

  @Delete(""
          +" delete "
          +" from "+ TABLE
          +" where id = #{id}")
   int deleteQuestionById(@Param("id") int id);

  @Select("<script> "
          +" select  question.id,points,question.name,answer,content,analysis,creator,question_type_id,question_type.name as question_type_name "
          +" from "+ TABLE+",question_type  "
          +" where question.id in "
          +" <foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'> "
          +" #{item} "
          +" </foreach> "
          +" and question_type_id = question_type.id "
          +" </script>"
  )
   List<Question> getQuestionByIds(@Param("ids") List<Integer> ids);

    @Select("<script> "
            +" select  question.id,points,question.name,answer,content,analysis,creator,question_type_id,question_type.name as question_type_name "
            +" from "+ TABLE+",question_type  "
            +" where question_type_id = question_type.id  "
            +" and "
            +" <foreach item='item' index='index' collection='points' open='(' separator='or ' close=')'> "
            +"  question.points like '%,${item},%' "
            +" </foreach> "
            +" </script>"
    )
    List<Question> getQuestionByKnowledgePoints(@Param("points") List<Integer> knowledgePointIds);

}

