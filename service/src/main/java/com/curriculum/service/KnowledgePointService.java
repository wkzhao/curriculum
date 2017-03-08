package com.curriculum.service;

import com.curriculum.domain.KnowledgePoint;
import com.curriculum.domain.PageBean;

import java.util.List;

public interface KnowledgePointService {
   List<KnowledgePoint> getPointsByPage(int knowledgeId, PageBean pageBean);

   int getPointCount(int knowledgeId);

   List<KnowledgePoint> getPointsByKnowledgeId(int paramInt);

   int addPoint(KnowledgePoint paramKnowledgePoint);

   int changeProperty(KnowledgePoint knowledgePoint);
}
