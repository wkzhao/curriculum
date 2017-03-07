package com.curriculum.dao;

import com.curriculum.domain.Knowledge;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface KnowledgeDao
{
   String TABLE = "knowledge";

  @Select(""
          +" select id,name,description "
          +" from knowledge")
   List<Knowledge> getAllKnowLedge();

  @Insert(" insert into "
          +TABLE+" (name,description)  "
          +" values (#{knowledge.name},#{knowledge.description})")
  @Options(useGeneratedKeys=true, keyProperty="knowledge.id")
   int addKnowledge(@Param("knowledge") Knowledge knowledge);

  @Select(""
          +" select name,description "
          +" from "+TABLE
          +" where id = #{id}")
   Knowledge getKnowledgeById(@Param("id") int id);
}

