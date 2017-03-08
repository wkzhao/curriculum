package com.curriculum.controller;

import com.curriculum.constant.Constants;
import com.curriculum.constant.WebCodeEnum;
import com.curriculum.domain.PageBean;
import com.curriculum.domain.User;
import com.curriculum.service.UserService;
import com.curriculum.util.ReturnJacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController
{

    @Autowired
    UserService userService;

    @RequestMapping(value={"user-register"}, method = RequestMethod.GET)
    public ModelAndView toRegisterPage()
    {
        return new ModelAndView("register");
    }
    @ResponseBody
    @RequestMapping(value={"user-register"}, method = RequestMethod.POST)
    public String register(@RequestBody User newUser, HttpSession session) throws JsonProcessingException {
        User user = userService.findUserByUsername(newUser.getUsername());
        if (user != null) {
            return ReturnJacksonUtil.resultWithFailed(WebCodeEnum.USER_ALREADY_EXISTS);
        }
        newUser.setLoginTime(new Date());
        userService.addUser(newUser);
        if( session.getAttribute("user") == null ){
            session.setAttribute("user", newUser);
        }
        return ReturnJacksonUtil.resultOk();
    }

    @RequestMapping(value={"admin/user-list/{currentPage}"}, method = RequestMethod.GET)
    public ModelAndView getUsersByPage(@PathVariable("currentPage") int currentPage) {
        ModelAndView view = new ModelAndView("admin/user-list");
        int userCount = userService.getUsersCount();
        PageBean pageBean = new PageBean(currentPage, Constants.PAGE_SIZE, userCount);
        List userList = userService.getUsersByPage(pageBean);
        view.addObject("userList", userList);
        view.addObject("pageBean", pageBean);
        return view;
    }

    @ResponseBody
    @RequestMapping(value={"admin/changeUserStatus"}, method = RequestMethod.POST)
    public String changeUserStatus(@RequestBody Map<String, String> map) throws JsonProcessingException { int userId = Integer.parseInt((String)map.get("userId"));
        int status = Integer.parseInt((String)map.get("status"));
        User user = userService.getUserById(userId);
        user.setStatus(status);
        userService.changeUserInfo(user);
        return ReturnJacksonUtil.resultOk(); }

    @RequestMapping(value={"admin/add-user"}, method = RequestMethod.GET)
    public ModelAndView toAddAdmin() {
        ModelAndView view = new ModelAndView("admin/add-user");
        return view;
    }
    @RequestMapping(value={"admin/add-user"}, method = RequestMethod.POST)
    @ResponseBody
    public String addAdmin(@RequestBody User user) throws JsonProcessingException { User u = userService.findUserByUsername(user.getUsername());
        if (u != null) {
            return ReturnJacksonUtil.resultWithFailed(WebCodeEnum.USER_ALREADY_EXISTS);
        }
        user.setLoginTime(new Date());
        userService.addUser(user);
        return ReturnJacksonUtil.resultOk();
    }
}