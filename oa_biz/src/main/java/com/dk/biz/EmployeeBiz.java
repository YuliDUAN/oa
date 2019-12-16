package com.dk.biz;

import com.dk.entity.Department;
import com.dk.entity.Employee;

import java.util.List;

/*
* 实现增删改查的业务方法
* */
public interface EmployeeBiz {

    void add(Employee employee);
    void edit(Employee employee);
    void remove(String sn);
    Employee get(String sn);
    List<Employee> getAll();

}
