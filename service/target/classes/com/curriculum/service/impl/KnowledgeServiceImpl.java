package com.curriculum.service.impl;

import com.curriculum.dao.KnowledgeDao;
import com.curriculum.domain.Knowledge;
import com.curriculum.service.KnowledgeService;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeServiceImpl
        implements KnowledgeService
{

    @Autowired
    KnowledgeDao knowledgeDao;

    public List<Knowledge> getAllKnowledge()
    {
        List knowledgeList = this.knowledgeDao.getAllKnowLedge();
        return knowledgeList == null ? Collections.emptyList() : knowledgeList;
    }

    public int addKnowledge(Knowledge knowledge)
    {
        int num = this.knowledgeDao.addKnowledge(knowledge);
        return num;
    }
}