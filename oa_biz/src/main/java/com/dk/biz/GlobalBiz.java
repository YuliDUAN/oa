package com.dk.biz;

import com.dk.entity.Employee;

public interface GlobalBiz {

    //登陆
    Employee login (String sn,String password);

    /*退出和个人中心属于表现层，不放在逻辑层实现*/

    //修改密码
    void changePassword(Employee employee);

}
