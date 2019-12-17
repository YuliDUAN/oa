package com.dk.oa.controller;

import com.dk.oa.biz.GlobalBiz;
import com.dk.oa.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

//登陆和个人中心模块
@Controller("globalController")
public class GloablController {
    @Autowired
    private GlobalBiz globalBiz;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    //登陆
    @RequestMapping("/login")
    public String login(HttpSession session, @RequestParam String sn, @RequestParam String password) {
        Employee employee = globalBiz.login(sn, password);
        if (employee == null) {
            return "redirect:to_login";
        }
        session.setAttribute("employee", employee);
        return "redirect:self";
    }

    //个人中心页面
    @RequestMapping("/self")
    public String self() {
        return "self";
    }

    //退出
    @RequestMapping("/quit")
    public String quit(HttpSession session) {
        session.setAttribute("employee", null);
        return "redirect:to_login";
    }

    //去修改密码
    @RequestMapping("/to_change_password")
    public String toChangePassword() {
        return "change_password";
    }

    //修改密码
    @RequestMapping("/change_password")
    public String changePassword(HttpSession session, @RequestParam String old, @RequestParam String new1, @RequestParam String new2) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee.getPassword().equals(old)) {
            if (new1.equals(new2)) {//成功
                employee.setPassword(new1);
                globalBiz.changePassword(employee);
                return "redirect:self";
            }
        }
        //失败，返回去修改，重新修改
        return "redirect:to_change_password";
    }

}
