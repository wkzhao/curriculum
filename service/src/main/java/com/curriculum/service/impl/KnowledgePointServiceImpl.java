package com.curriculum.service.impl;

import com.curriculum.dao.KnowledgePointDao;
import com.curriculum.domain.KnowledgePoint;
import com.curriculum.service.KnowledgePointService;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KnowledgePointServiceImpl
        implements KnowledgePointService
{

    @Autowired
    KnowledgePointDao knowledgePointDao;

    public List<KnowledgePoint> getAllPoints()
    {
        List knowledgePointList = this.knowledgePointDao.getAllPoints();
        return knowledgePointList == null ? Collections.emptyList() : knowledgePointList;
    }

    public List<KnowledgePoint> getPointsByKnowledgeId(int knowledgeId)
    {
        List knowledgePointList = this.knowledgePointDao.getPointsByKnowledgeId(Integer.valueOf(knowledgeId));
        return knowledgePointList == null ? Collections.emptyList() : knowledgePointList;
    }

    public KnowledgePoint getPointById(int pointId) {
        return this.knowledgePointDao.getPointById(pointId);
    }

    public int addPoint(KnowledgePoint knowledgePoint)
    {
        int num = this.knowledgePointDao.addPoint(knowledgePoint);
        return num;
    }
}