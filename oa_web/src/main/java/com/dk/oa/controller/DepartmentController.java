package com.dk.oa.controller;

import com.dk.oa.biz.DepartmentBiz;
import com.dk.oa.biz.EmployeeBiz;
import com.dk.oa.entity.Department;
import com.dk.oa.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//部门模块的控制器
@Controller("departmentController")
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentBiz departmentBiz;
    @Autowired
    private EmployeeBiz employeeBiz;

    @RequestMapping("/list")
    public String list(Map<String, Object> map) {
        map.put("list", departmentBiz.getAll());
        return "department_list";
    }

    @RequestMapping("/to_add")
    public String toAdd(Map<String, Object> map) {
        map.put("department", new Department());
        return "department_add";
    }

    @RequestMapping("/add")
    public String add(Department department) {
        departmentBiz.add(department);
        return "redirect:list";
    }

    @RequestMapping(value = "/to_update", params = "sn")
    public String toUpdate(String sn, Map<String, Object> map) {
        map.put("department", departmentBiz.get(sn));
        return "department_update";
    }

    @RequestMapping("/update")
    public String update(Department department) {
        departmentBiz.edit(department);
        return "redirect:list";
    }

    @RequestMapping(value = "/remove", params = "sn")
    public String remove(String sn) {
        //判断部门是否还有成员
        List<Employee> emplist = employeeBiz.getAll();
        boolean loge = false;
        for (Employee emp: emplist) {
            String id = emp.getDepartmentSn();
            if (sn.equals(id)){
                loge = true;
            }
        }
        if (loge){
            System.out.println("还有成员，不能删除该部门");
        }else {
            departmentBiz.remove(sn);
        }
        return "redirect:list";
    }

}
