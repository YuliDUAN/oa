package com.dk.controller;

import com.dk.biz.DepartmentBiz;
import com.dk.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller("departmentController")
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentBiz departmentBiz;
    //部门列表显示
    @RequestMapping("/list")
    public String list(Map<String,Object> map){
        map.put("list",departmentBiz.getAll());
        return "department_list";
    }
    //去添加
    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object> map){
        map.put("department",new Department());
        return "department_add";
    }
    //添加
    @RequestMapping("/add")
    public String toAdd(Department department){
        departmentBiz.add(department);
        /*重定向到列表页面，直接到department_list，是不会显示内容的*/
        return "redirect:list";
    }
    //去修改
    @RequestMapping(value = "/to_update",params = "sn")
    public String toUpdate(String sn,Map<String,Object> map){
        map.put("department",departmentBiz.get(sn));
        return "department_update";
    }
    //修改
    @RequestMapping("/update")
    public String update(Department department){
        departmentBiz.edit(department);
        /*重定向到列表页面，直接到department_list，是不会显示内容的*/
        return "redirect:list";
    }
    //删除
    @RequestMapping(value = "/remove",params = "sn")
    public String remove(String sn,Department department){
        departmentBiz.remove(sn);
        /*重定向到列表页面，直接到department_list，是不会显示内容的*/
        return "redirect:list";
    }
}
