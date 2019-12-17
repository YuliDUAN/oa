package com.dk.oa.dao;

import com.dk.oa.entity.DealRecord;
import com.dk.oa.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("dealRecordDao")
public interface DealRecordDao {
    void insert(DealRecord dealRecord);

    List<DealRecord> selectByClaimVoucher(int cvid);
}
