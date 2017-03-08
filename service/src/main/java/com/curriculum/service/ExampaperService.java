package com.curriculum.service;

import com.curriculum.domain.Exampaper;
import com.curriculum.domain.PageBean;

import java.util.List;

 public interface ExampaperService {
   List<Exampaper>  getExamPaperByPage(int status, PageBean pageBean);

   int getPaperCount(int status);

   List<Exampaper> getSimplePapers();

   int addPaper(Exampaper paramExampaper);

   Exampaper getPaperById(int paramInt);

   int deletePaper(int paramInt);

   int changePaperStatus(int paramInt1, int paramInt2);

   int changePaperContent(String paramString, int paramInt);

   int changePaperQuestionIds(String paramString, int paramInt);

   int changePaperProperty(Exampaper exampaper);
}
