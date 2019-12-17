package com.dk.oa.dao;

import com.dk.oa.entity.ClaimVoucher;
import com.dk.oa.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("claimVoucherDao")
public interface ClaimVoucherDao {

    void insert(ClaimVoucher claimVoucher);

    void update(ClaimVoucher claimVoucher);

    void delete(int id);

    ClaimVoucher select(int id);

    List<ClaimVoucher> selectByCreateSn(String csn);

    List<ClaimVoucher> selectByNextDealSn(String ndsn);
}
