package com.dk.oa.dto;

import com.dk.oa.entity.ClaimVoucher;
import com.dk.oa.entity.ClaimVoucherItem;

import java.util.List;

//收集报销单里面的表单数据
public class ClaimVoucherInfo {
    //报销单对象
    private ClaimVoucher claimVoucher;
    //Iteam集合
    private List<ClaimVoucherItem> items;

    public ClaimVoucher getClaimVoucher() {
        return claimVoucher;
    }

    public void setClaimVoucher(ClaimVoucher claimVoucher) {
        this.claimVoucher = claimVoucher;
    }

    public List<ClaimVoucherItem> getItems() {
        return items;
    }

    public void setItems(List<ClaimVoucherItem> items) {
        this.items = items;
    }
}
