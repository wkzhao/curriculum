package com.curriculum.controller;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController
{

    @Autowired
    UserService userService;

    @RequestMapping(value={"user-register"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView toRegisterPage()
    {
        return new ModelAndView("register");
    }
    @ResponseBody
    @RequestMapping(value={"user-register"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public String register(@ModelAttribute User newUser, HttpSession session) throws JsonProcessingException { User user = this.userService.findUserByUsername(newUser.getUsername());
        if (user != null) {
            return ReturnJacksonUtil.resultWithFailed(WebCodeEnum.USER_ALREADY_EXISTS);
        }
        this.userService.addUser(newUser);
        session.setAttribute("user", newUser);
        return ReturnJacksonUtil.resultOk(); }

    @RequestMapping(value={"admin/user-list/{currentPage}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView getUsersByPage(@PathVariable("currentPage") int currentPage) {
        ModelAndView view = new ModelAndView("admin/user-list");
        int userCount = this.userService.getUsersCount();
        PageBean pageBean = new PageBean(currentPage, 10, userCount);
        List userList = this.userService.getUsersByPage(pageBean);
        view.addObject("userList", userList);
        view.addObject("pageBean", pageBean);
        return view;
    }
    @ResponseBody
    @RequestMapping(value={"admin/changeUserStatus"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public String changeUserStatus(@RequestBody Map<String, String> map) throws JsonProcessingException { int userId = Integer.parseInt((String)map.get("userId"));
        int status = Integer.parseInt((String)map.get("status"));
        User user = this.userService.getUserById(userId);
        user.setStatus(status);
        this.userService.changeUserInfo(user);
        return ReturnJacksonUtil.resultOk(); }

    @RequestMapping(value={"admin/add-user"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView toAddAdmin() {
        ModelAndView view = new ModelAndView("admin/add-user");
        return view;
    }
    @RequestMapping(value={"admin/add-user"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public String addAdmin(@RequestBody User user) throws JsonProcessingException { User u = this.userService.findUserByUsername(user.getUsername());
        if (u != null) {
            return ReturnJacksonUtil.resultWithFailed(WebCodeEnum.USER_ALREADY_EXISTS);
        }
        user.setLoginTime(new Date());
        this.userService.addUser(user);
        return ReturnJacksonUtil.resultOk();
    }
}