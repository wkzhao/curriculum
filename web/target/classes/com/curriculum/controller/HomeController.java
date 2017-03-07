package com.curriculum.controller;

import com.curriculum.domain.User;
import com.curriculum.interceptor.annotation.LoginRequired;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({""})
public class HomeController
{
    @RequestMapping(value={"", "home"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String toHome(HttpSession session)
    {
        User user = (User)session.getAttribute("user");
        if ((user != null) && (user.getRoleId() == 0)) {
            return "redirect:admin/home";
        }
        return "home";
    }
    @LoginRequired
    @RequestMapping(value={"admin/home"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String toAdminHome() { return "redirect:knowledge-list";
    }
}