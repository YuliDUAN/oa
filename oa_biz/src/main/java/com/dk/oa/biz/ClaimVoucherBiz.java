package com.dk.oa.biz;

import com.dk.oa.entity.ClaimVoucher;
import com.dk.oa.entity.ClaimVoucherItem;
import com.dk.oa.entity.DealRecord;

import java.util.List;

public interface ClaimVoucherBiz {

    //保存报销单
    void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);

    //获取报销单
    ClaimVoucher get(int id);

    //获取报销单条目详情
    List<ClaimVoucherItem> getItems(int cvid);

    //审核记录
    List<DealRecord> getRecords(int cvid);

    //
    List<ClaimVoucher> getForSelf(String sn);

    List<ClaimVoucher> getForDeal(String sn);

    void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);

    void submit(int id);

    void deal(DealRecord dealRecord);
}
