package com.dk.controller;

import com.dk.biz.DepartmentBiz;
import com.dk.biz.EmployeeBiz;
import com.dk.entity.Employee;
import com.dk.global.Contant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller("employeeController")
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private DepartmentBiz departmentBiz;
    @Autowired
    private EmployeeBiz employeeBiz;
    //部门列表显示
    @RequestMapping("/list")
    public String list(Map<String,Object> map){
        map.put("list",employeeBiz.getAll());
        return "employee_list";
    }
    //去添加
    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object> map){
        map.put("employee",new Employee());
        map.put("dlist",departmentBiz.getAll());
        map.put("plist", Contant.getPosts());
        return "employee_add";
    }
    //添加
    @RequestMapping("/add")
    public String toAdd(Employee employee){
        employeeBiz.add(employee);
        /*重定向到列表页面，直接到department_list，是不会显示内容的*/
        return "redirect:list";
    }
    //去修改
    @RequestMapping(value = "/to_update",params = "sn")
    public String toUpdate(String sn,Map<String,Object> map){
        map.put("employee",departmentBiz.get(sn));
        map.put("dlist",departmentBiz.getAll());
        map.put("plist",Contant.getPosts());
        return "employee_update";
    }
    //修改
    @RequestMapping("/update")
    public String update(Employee employee){
        employeeBiz.edit(employee);
        /*重定向到列表页面，直接到department_list，是不会显示内容的*/
        return "redirect:list";
    }
    //删除
    @RequestMapping(value = "/remove",params = "sn")
    public String remove(String sn){
        employeeBiz.remove(sn);
        /*重定向到列表页面，直接到department_list，是不会显示内容的*/
        return "redirect:list";
    }
}
