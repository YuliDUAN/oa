package com.dk.oa.dao;

import com.dk.oa.entity.DealRecord;
import com.dk.oa.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

//对报销单进行处理的处理记录
@Repository("dealRecordDao")
public interface DealRecordDao {
    //不能修改和删除（相当于日志记录）

    void insert(DealRecord dealRecord);

    //根据报销单，来查看其处理流程
    List<DealRecord> selectByClaimVoucher(int cvid);
}
