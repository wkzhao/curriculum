package com.curriculum.service.impl;

import com.curriculum.dao.KnowledgePointDao;
import com.curriculum.domain.KnowledgePoint;
import com.curriculum.domain.PageBean;
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


    @Override
    public List<KnowledgePoint> getPointsByPage(int knowledgeId, PageBean pageBean) {
        List<KnowledgePoint> knowledgePointList =  knowledgePointDao.getPointsByPage(knowledgeId,pageBean);
        return knowledgePointList == null ? Collections.emptyList() : knowledgePointList;
    }

    @Override
    public int getPointCount(int knowledgeId) {
        return knowledgePointDao.getPointCount(knowledgeId);
    }

    public List<KnowledgePoint> getPointsByKnowledgeId(int knowledgeId)
    {
        List knowledgePointList = knowledgePointDao.getPointsByKnowledgeId(Integer.valueOf(knowledgeId));
        return knowledgePointList == null ? Collections.emptyList() : knowledgePointList;
    }

    @Override
    public KnowledgePoint getPointById(int pointId) {
        return knowledgePointDao.getPointById(pointId);
    }

    public int addPoint(KnowledgePoint knowledgePoint)
    {
        int num = knowledgePointDao.addPoint(knowledgePoint);
        return num;
    }

    @Override
    public int changeProperty(KnowledgePoint knowledgePoint) {
        return knowledgePointDao.changeProperty(knowledgePoint);
    }
}