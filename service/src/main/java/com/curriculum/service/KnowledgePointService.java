package com.curriculum.service;

import com.curriculum.domain.KnowledgePoint;
import java.util.List;

public interface KnowledgePointService {
   List<KnowledgePoint> getAllPoints();

   List<KnowledgePoint> getPointsByKnowledgeId(int paramInt);

   int addPoint(KnowledgePoint paramKnowledgePoint);
}
