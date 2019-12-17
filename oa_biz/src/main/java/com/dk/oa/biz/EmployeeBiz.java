package com.dk.oa.biz;

import com.dk.oa.entity.Department;
import com.dk.oa.entity.Employee;

import java.util.List;

public interface EmployeeBiz {
    //添加成员
    void add(Employee employee);
    //修改成员
    void edit(Employee employee);
    //删除成员
    void remove(String sn);
    //根据id查询
    Employee get(String sn);
    //查询所有
    List<Employee> getAll();
}
