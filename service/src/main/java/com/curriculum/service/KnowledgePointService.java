package com.curriculum.service;

import com.curriculum.domain.KnowledgePoint;
import com.curriculum.domain.PageBean;

import java.util.List;

public interface KnowledgePointService {
   List<KnowledgePoint> getPointsByPage(int knowledgeId, PageBean pageBean);

   int getPointCount(int knowledgeId);

   List<KnowledgePoint> getPointsByKnowledgeId(int knowledgeId);

   int addPoint(KnowledgePoint knowledgePoint);

   int changeProperty(KnowledgePoint knowledgePoint);

   KnowledgePoint getPointById(int pointId);
}
