package com.dk.oa.dao;

import com.dk.oa.entity.ClaimVoucherItem;
import com.dk.oa.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("claimVoucherItemDao")
public interface ClaimVoucherItemDao {

    void insert(ClaimVoucherItem claimVoucherItem);

    void update(ClaimVoucherItem claimVoucherItem);

    void delete(int id);

    //根据报销单编号查询，报销单的详细信息
    List<ClaimVoucherItem> selectByClaimVoucher(int cvid);
}
