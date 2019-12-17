package com.dk.oa.biz;

import com.dk.oa.entity.Department;

import java.util.List;

public interface DepartmentBiz {
    //添加部门
    void add(Department department);

    //编译部门
    void edit(Department department);

    //删除部门
    void remove(String sn);

    //根据id得到部门
    Department get(String sn);

    //得到所有部门
    List<Department> getAll();
}
