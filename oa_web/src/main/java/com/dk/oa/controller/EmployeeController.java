package com.dk.oa.controller;

import com.dk.oa.biz.DepartmentBiz;
import com.dk.oa.biz.EmployeeBiz;
import com.dk.oa.entity.Department;
import com.dk.oa.entity.Employee;
import com.dk.oa.global.Contant;
import com.dk.oa.lucene.EmpIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
    private EmpIndex empIndex = new EmpIndex();

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
    public String add(Employee employee) throws Exception {
        employeeBiz.add(employee);
        empIndex.addIndex(employee);
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
    public String update(Employee employee) throws Exception {
        employeeBiz.edit(employee);
        empIndex.updateIndex(employee);
        return "redirect:list";
    }

    //删除
    @RequestMapping(value = "/remove", params = "sn")
    public String remove(String sn) throws Exception {
        employeeBiz.remove(sn);
        empIndex.deleteIndex(sn);
        return "redirect:list";
    }

    //全局搜索（根据关键字）
    @RequestMapping("/q")
    public ModelAndView q(@RequestParam(value = "q",required = false)String q,
                          HttpServletRequest request)throws Exception{
        ModelAndView mav = new ModelAndView();
        mav.addObject("mainPage","employee_list.jsp");
        List<Employee> employeeList = empIndex.searchEmp(q.trim());
        mav.addObject("q",q);
        mav.addObject("result",employeeList);
        mav.addObject("resultTotal",employeeList.size());
        mav.addObject("pageTitle","搜索的关键字"+'q'+"的结果");
        mav.setViewName("employee_list_serch");
        System.out.println(employeeList);
        System.out.println(employeeList.size());
        return mav;
    }
}
