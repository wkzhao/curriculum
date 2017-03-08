package com.curriculum.service.impl;

import com.curriculum.dao.UserPaperDao;
import com.curriculum.domain.UserPaper;
import com.curriculum.service.UserPaperService;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPaperServiceImpl
        implements UserPaperService
{

    @Autowired
    UserPaperDao userPaperDao;

    public List<UserPaper> getUserPaperByUserId(int userId)
    {
        List userPaperList = userPaperDao.getUserPaperByUserId(userId);
        return userPaperList == null ? Collections.emptyList() : userPaperList;
    }

    public int addUserPaper(int userId, int paperId)
    {
        return userPaperDao.addUserPaper(userId, paperId);
    }

    public int getCountByUserAndPaper(int userId, int paperId)
    {
        return userPaperDao.getCountByUserAndPaper(userId, paperId);
    }
}