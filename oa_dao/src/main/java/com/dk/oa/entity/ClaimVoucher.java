package com.dk.oa.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

//报销单实体类
public class ClaimVoucher {
    private Integer id;
    //原因
    private String cause;
    //创建者（用于关联Employee对象）
    private String createSn;
    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date createTime;
    //处理人编号（用于关联Employee对象）
    private String nextDealSn;
    //报销金额
    private Double totalAmount;
    //报销单状态
    private String status;

    //声明关联对象的属性
    private Employee creater;

    private Employee dealer;

    public ClaimVoucher(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getCreateSn() {
        return createSn;
    }

    public void setCreateSn(String createSn) {
        this.createSn = createSn;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNextDealSn() {
        return nextDealSn;
    }

    public void setNextDealSn(String nextDealSn) {
        this.nextDealSn = nextDealSn;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employee getCreater() {
        return creater;
    }

    public void setCreater(Employee creater) {
        this.creater = creater;
    }

    public Employee getDealer() {
        return dealer;
    }

    public void setDealer(Employee dealer) {
        this.dealer = dealer;
    }
}