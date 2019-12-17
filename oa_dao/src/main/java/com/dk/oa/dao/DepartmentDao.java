package com.dk.oa.dao;

import com.dk.oa.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("departmentDao")
public interface DepartmentDao {

    void insert(Department department);

    void update(Department department);

    void delete(String sn);

    //根据id查询部门
    Department select(String sn);

    //查询所有部门
    List<Department> selectAll();
}
