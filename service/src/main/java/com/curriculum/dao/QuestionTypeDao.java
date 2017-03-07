package com.curriculum.dao;

import com.curriculum.domain.QuestionType;
import java.util.List;
import org.apache.ibatis.annotations.Select;

public interface QuestionTypeDao
{
   String TABLE = "question_type";

  @Select(""
          +" select id,name "
          +" from "+TABLE
  )
   List<QuestionType> getAllTypes();
}
