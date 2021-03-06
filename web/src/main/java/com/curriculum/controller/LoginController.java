package com.curriculum.controller;

import com.curriculum.constant.WebCodeEnum;
import com.curriculum.domain.User;
import com.curriculum.service.UserService;
import com.curriculum.util.ReturnJacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController
{

    @Autowired
    UserService userService;

    @RequestMapping(value={"user-login"}, method = RequestMethod.GET)
    public ModelAndView toLoginPage()
    {
        return new ModelAndView("login");
    }
    @ResponseBody
    @RequestMapping(value={"user-login"}, method = RequestMethod.POST)
    public String login(@RequestBody User u, HttpSession session) throws JsonProcessingException {
        User user = userService.findUserByUsername(u.getUsername());
        if (user == null) {
            return ReturnJacksonUtil.resultWithFailed(WebCodeEnum.NO_USER_ERROR);
        }
        if (!user.getPassword().equals(u.getPassword())) {
            return ReturnJacksonUtil.resultWithFailed(WebCodeEnum.PASSWORD_ERROR);
        }
        user.setLastLoginTime(user.getLoginTime());
        user.setLoginTime(new Date());
        userService.changeUserInfo(user);
        session.setAttribute("user", user);
        return ReturnJacksonUtil.resultOk();
    }
    @RequestMapping(value={"user-logout"}, method = RequestMethod.GET)
    public String logout(HttpSession session) throws JsonProcessingException {
        session.invalidate();
        return "redirect:home";
    }
}