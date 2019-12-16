package com.dk.entity;
/*
* 部门表的实体
* */
public class Department {
    private String sn;
    private String name;
    private String address;

    public Department(){}

    public Department(String sn, String name, String address) {
        this.sn = sn;
        this.name = name;
        this.address = address;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
