package com.curriculum.dao;

import com.curriculum.domain.KnowledgePoint;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface KnowledgePointDao
{
   String TABLE = "knowledge_point";

  @Select(""
          +" select knowledge_point.id,knowledge_point.name,knowledge_point.description,knowledge_id,knowledge.name as knowledgeName "
          +" from "+TABLE+" ,knowledge  "
          +" where knowledge.id = knowledge_id "
  )
   List<KnowledgePoint> getAllPoints();
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
}
