package com.curriculum.service.impl;

import com.curriculum.dao.PaperDao;
import com.curriculum.domain.Exampaper;
import com.curriculum.domain.PageBean;
import com.curriculum.service.ExampaperService;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExampaperServiceImpl
        implements ExampaperService
{

    @Autowired
    PaperDao paperDao;

    public List<Exampaper>  getExamPaperByPage(int status, PageBean pageBean)
    {
        List exampaperList = this.paperDao.getExamPaperByPage(status,pageBean);
        return exampaperList == null ? Collections.emptyList() : exampaperList;
    }

    @Override
    public int getPaperCount(int status) {
        return paperDao.getPaperCount(status);
    }

    public List<Exampaper> getSimplePapers()
    {
        List exampaperList = this.paperDao.getSimplePapers();
        return exampaperList == null ? Collections.emptyList() : exampaperList;
    }

    public int addPaper(Exampaper exampaper)
    {
        return this.paperDao.addPaper(exampaper);
    }

    public Exampaper getPaperById(int id)
    {
        return this.paperDao.getPaperById(id);
    }

    public int deletePaper(int id)
    {
        if (getPaperById(id) == null) {
            return 0;
        }
        return this.paperDao.deletePaper(id);
    }

    public int changePaperStatus(int id, int status)
    {
        return this.paperDao.changePaperStatus(id, status);
    }

    public int changePaperContent(String content, int id)
    {
        return this.paperDao.changePaperContent(content, id);
    }

    public int changePaperQuestionIds(String questionIds, int id)
    {
        return this.paperDao.changePaperQuestionIds(questionIds, id);
    }

    @Override
    public int changePaperProperty(Exampaper exampaper) {
        return paperDao.changePaperProperty(exampaper);
    }
}