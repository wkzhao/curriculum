package com.curriculum.service;

import com.curriculum.domain.PageBean;
import com.curriculum.domain.User;
import java.util.List;

 public interface UserService {
   int addUser(User user);

   User findUserByUsername(String username);

   int changeUserInfo(User user);

   int getUsersCount();

   User getUserById(int id);

   List<User> getUsersByPage(PageBean pageBean);
}