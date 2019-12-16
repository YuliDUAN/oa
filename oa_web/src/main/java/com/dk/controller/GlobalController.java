package com.dk.controller;

import com.dk.biz.GlobalBiz;
import com.dk.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller("globalController")
public class GlobalController {

    @Autowired
    private GlobalBiz globalBiz;

    //去登陆
    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    //登陆
    @RequestMapping("/login")
    public String login(HttpSession session, @RequestParam String sn, @RequestParam String password){
        Employee employee = globalBiz.login(sn,password);
        if (employee == null){//失败
            return "redirect:to_login";
        }
        //成功
        session.setAttribute("employee",employee);
        return self();
    }

    //登陆成功跳转
    @RequestMapping("/self")
    public String self(){
        return "self";
    }

    //退出,返回到登陆界面
    @RequestMapping("/quit")
    public String quit(HttpSession session){
        session.setAttribute("employee",null);
        return "redirect:to_login";
    }

    //去修改密码
    @RequestMapping("/to_change_password")
    public String toChangePassword(){
        return "change_password";
    }

    //修改密码,输入两次密码
    /*@RequestMapping("change_password")
    public String changePassword(HttpSession session,@RequestParam String old,@RequestParam String new1,@RequestParam String new2){
        Employee employee = (Employee) session.getAttribute("employee");
        if(employee==null){
            return "redirect:to_login";
        }

        session.setAttribute("employee",employee);
        return "redirect:self";
    }*/
}
