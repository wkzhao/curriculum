package com.curriculum.service;

import com.curriculum.domain.Exampaper;
import com.curriculum.domain.PageBean;

import java.util.List;
import java.util.Map;

public interface ExampaperService {
   List<Exampaper>  getExamPaperByPage(int status, PageBean pageBean);

   Exampaper createExamPaper(Exampaper exampaper);

   int getPaperCount(int status);

   List<Exampaper> getSimplePapers();

   int addPaper(Exampaper exampaper);

   Exampaper getPaperById(int id);

   int deletePaper(int id);

   int changePaperStatus(int id, int status);

   int changePaperContent(String content, int id);

   int changePaperQuestionIds(String questionIds, int id);

   int changePaperProperty(Exampaper exampaper);
}
