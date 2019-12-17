package com.dk.oa.entity;

//报销单条目（一个报销单的详细信息）
public class ClaimVoucherItem {
    private Integer id;
    //报销单编号（用于管理报销单）
    private Integer claimVoucherId;

    //类型
    private String item;
    //金额
    private Double amount;
    //说明
    private String comment;

    public ClaimVoucherItem(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClaimVoucherId() {
        return claimVoucherId;
    }

    public void setClaimVoucherId(Integer claimVoucherId) {
        this.claimVoucherId = claimVoucherId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}