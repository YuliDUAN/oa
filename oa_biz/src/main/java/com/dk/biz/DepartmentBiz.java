package com.dk.biz;

import com.dk.entity.Department;

import java.util.List;

/*
* 实现增删改查的业务方法
* */
public interface DepartmentBiz {

    void add(Department department);
    void edit(Department department);
    void remove(String sn);
    Department get(String sn);
    List<Department> getAll();

}
