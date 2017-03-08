package com.curriculum.dao;

import com.curriculum.domain.KnowledgePoint;
import java.util.List;

import com.curriculum.domain.PageBean;
import org.apache.ibatis.annotations.*;

public interface KnowledgePointDao
{
    String TABLE = "knowledge_point";

    @Select(" <script> "
            +" select knowledge_point.id,knowledge_point.name,knowledge_point.description,knowledge_id,knowledge.name as knowledgeName "
            +" from "+TABLE+" ,knowledge  "
            +" <trim prefix=\"WHERE\" prefixoverride=\"AND |OR\"> "
            +" knowledge.id = knowledge_id "
            +" <if test=\"knowledgeId != 0\"> AND knowledge_id = #{knowledgeId} </if> "
            +" </trim> "
            +" <if test = 'pageBean != null'> "
            +" limit #{pageBean.recordIndex},#{pageBean.pageSize} "
            +" </if> "
            +" </script> "
    )
    List<KnowledgePoint> getPointsByPage(@Param("knowledgeId") int knowledgeId, @Param("pageBean")PageBean pageBean);

    @Select(" <script> "
            +" select count(1) "
            +" from "+TABLE
            +" <if test = ' knowledgeId != 0 '> "
            +" where knowledge_id = #{knowledgeId} "
            +" </if> "
            +" </script> "
    )
    int getPointCount(@Param("knowledgeId") int knowledgeId);

   @Select(""
            +" select knowledge_point.id,knowledge_point.name,knowledge_point.description,knowledge_id,knowledge.name as knowledgeName "
            +" from "+TABLE+" ,knowledge  "
            +" where knowledge.id = knowledge_id "
           +" and knowledge_point.id = #{pointId} "
   )
   KnowledgePoint getPointById(@Param("pointId") int pointId);

   @Select(""
            +" select knowledge_point.id,knowledge_point.name,knowledge_point.description,knowledge_id,knowledge.name as knowledgeName "
            +" from "+TABLE+" ,knowledge  "
            +" where knowledge_id = #{knowledgeId}  "
            +" and knowledge.id = knowledge_id "
    )
   List<KnowledgePoint> getPointsByKnowledgeId(@Param("knowledgeId") int knowledgeId);

  @Insert(""
          +" insert into "
          +TABLE+" (name,description,knowledge_id) "
          +" values(#{knowledgePoint.name},#{knowledgePoint.description},#{knowledgePoint.knowledgeId}) ")
  @Options(useGeneratedKeys=true, keyProperty="knowledgePoint.id")
   int addPoint(@Param("knowledgePoint") KnowledgePoint paramKnowledgePoint);

  @Update(""
          +" update "+TABLE
          +" set name = #{knowledgePoint.name},description = #{knowledgePoint.description},knowledge_id = #{knowledgePoint.knowledgeId} "
          +" where id = #{knowledgePoint.id} "
  )
   int changeProperty(@Param("knowledgePoint") KnowledgePoint knowledgePoint);
}
