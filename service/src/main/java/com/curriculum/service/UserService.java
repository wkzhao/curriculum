package com.curriculum.service;

import com.curriculum.domain.PageBean;
import com.curriculum.domain.User;
import java.util.List;

 public interface UserService {
   int addUser(User paramUser);

   User findUserByUsername(String paramString);

   int changeUserInfo(User paramUser);

   int getUsersCount();

   User getUserById(int paramInt);

   List<User> getUsersByPage(PageBean paramPageBean);
}