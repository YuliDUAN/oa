package com.dk.oa.dao;

import com.dk.oa.entity.Department;
import com.dk.oa.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("employeeDao")
public interface EmployeeDao {

    void insert(Employee employee);

    void update(Employee employee);

    void delete(String sn);

    Employee select(String sn);

    List<Employee> selectAll();

    //根据部门查询所有部门经理
    List<Employee> selectByDepartmentAndPost(@Param("dsn") String dsn, @Param("post") String post);
}
