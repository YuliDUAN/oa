package com.dk.oa.controller;

import com.dk.oa.biz.DepartmentBiz;
import com.dk.oa.biz.EmployeeBiz;
import com.dk.oa.entity.Department;
import com.dk.oa.entity.Employee;
import com.dk.oa.global.Contant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

//成员模块控制器
@Controller("employeeController")
@RequestMapping("/employee")
public class EmployeeController {

    //自动注入
    @Autowired
    private DepartmentBiz departmentBiz;
    @Autowired
    private EmployeeBiz employeeBiz;

    //显示列表
    @RequestMapping("/list")
    public String list(Map<String, Object> map) {
        map.put("list", employeeBiz.getAll());
        return "employee_list";
    }

    //去添加
    @RequestMapping("/to_add")
    public String toAdd(Map<String, Object> map) {
        map.put("employee", new Employee());
        map.put("dlist", departmentBiz.getAll());
        map.put("plist", Contant.getPosts());
        return "employee_add";
    }

    //添加
    @RequestMapping("/add")
    public String add(Employee employee) {
        employeeBiz.add(employee);
        return "redirect:list";
    }

    //去更新
    @RequestMapping(value = "/to_update", params = "sn")
    public String toUpdate(String sn, Map<String, Object> map) {
        map.put("employee", employeeBiz.get(sn));
        map.put("dlist", departmentBiz.getAll());
        map.put("plist", Contant.getPosts());
        return "employee_update";
    }

    //更新
    @RequestMapping("/update")
    public String update(Employee employee) {
        employeeBiz.edit(employee);
        return "redirect:list";
    }

    //删除
    @RequestMapping(value = "/remove", params = "sn")
    public String remove(String sn) {
        employeeBiz.remove(sn);
        return "redirect:list";
    }

}
