package com.curriculum.service;

import com.curriculum.domain.UserPaper;
import java.util.List;
import java.util.Map;

public interface UserPaperService {
   List<UserPaper> getUserPaperByUserId(int userId);

   UserPaper getUserPaperByUserIdAndPaperId(int userId, int paperId);

   int addUserPaper(int userId, int paperId,Map<String,String> questionIdAnswerMap);

   int getCountByUserAndPaper(int userId, int paperId);
}
