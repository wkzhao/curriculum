package com.curriculum.service;

import com.curriculum.domain.Knowledge;
import java.util.List;

public interface KnowledgeService
{
   List<Knowledge> getAllKnowledge();

   int addKnowledge(Knowledge knowledge);

   int changeProperty(Knowledge knowledge);
}
