package com.dk.oa.biz;

import com.dk.oa.entity.Employee;

public interface GlobalBiz {
    //登陆校验
    Employee login(String sn, String password);

    //修改密码
    void changePassword(Employee employee);
}
