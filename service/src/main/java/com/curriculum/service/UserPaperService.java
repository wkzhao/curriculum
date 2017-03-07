package com.curriculum.service;

import com.curriculum.domain.UserPaper;
import java.util.List;

public interface UserPaperService {
   List<UserPaper> getUserPaperByUserId(int paramInt);

   int addUserPaper(int paramInt1, int paramInt2);

   int getCountByUserAndPaper(int paramInt1, int paramInt2);
}
