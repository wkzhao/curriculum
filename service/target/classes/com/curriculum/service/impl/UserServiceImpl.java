package com.curriculum.service.impl;

import com.curriculum.dao.UserDao;
import com.curriculum.domain.PageBean;
import com.curriculum.domain.User;
import com.curriculum.service.UserService;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl
        implements UserService
{

    @Autowired
    UserDao userDao;

    public int addUser(User user)
    {
        return this.userDao.addUser(user);
    }

    public User findUserByUsername(String username)
    {
        return this.userDao.findUserByUsername(username);
    }

    public int changeUserInfo(User user)
    {
        return this.userDao.changeUserInfo(user);
    }

    public int getUsersCount()
    {
        return this.userDao.getUsersCount();
    }

    public User getUserById(int userId)
    {
        return this.userDao.findUserById(userId);
    }

    public List<User> getUsersByPage(PageBean pageBean)
    {
        List userList = this.userDao.getUsersByPage(pageBean);
        return userList == null ? Collections.emptyList() : userList;
    }
}