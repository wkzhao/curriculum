package com.curriculum.interceptor;

import com.curriculum.domain.User;
import com.curriculum.interceptor.annotation.LoginRequired;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor
        implements HandlerInterceptor
{
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler)
            throws Exception
    {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        LoginRequired loginRequired = handlerMethod.getMethodAnnotation(LoginRequired.class);
        if (loginRequired == null) {
            loginRequired = handlerMethod.getBeanType().getAnnotation(LoginRequired.class);
        }
        if (loginRequired == null) {
            return true;
        }
        User user = (User)httpServletRequest.getSession().getAttribute("user");
        if (user == null) {
            httpServletRequest.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(httpServletRequest, httpServletResponse);
            return false;
        }
        String uri = httpServletRequest.getRequestURI();
        if (uri.contains("admin/")) {
            if(user.getRoleId() != 0 ){
                httpServletRequest.getRequestDispatcher("/WEB-INF/views/no-privilege.jsp").forward(httpServletRequest, httpServletResponse);
                return false;
            }
        }
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView)
            throws Exception
    {
    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e)
            throws Exception
    {
    }
}