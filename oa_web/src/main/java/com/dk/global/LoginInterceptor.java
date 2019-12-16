package com.dk.global;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//登陆功能的拦截器，拦截地址栏非法访问，也可以用过滤器
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

       //对登陆必须的部分进行放行
        String url = httpServletRequest.getRequestURI();
        if (url.toLowerCase().indexOf("login")>=0){
            return true;
        }

        //通过session来判断是否已经登陆
        HttpSession httpSession = httpServletRequest.getSession();
            //已经登陆
        if (httpSession.getAttribute("employee")!=null){
            return true;
        }

        //还未登陆，跳转至登陆界面
        httpServletResponse.sendRedirect("/to_login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
