package com.dk.oa.biz.impl;

import com.dk.oa.biz.ClaimVoucherBiz;
import com.dk.oa.dao.ClaimVoucherDao;
import com.dk.oa.dao.ClaimVoucherItemDao;
import com.dk.oa.dao.DealRecordDao;
import com.dk.oa.dao.EmployeeDao;
import com.dk.oa.entity.ClaimVoucher;
import com.dk.oa.entity.ClaimVoucherItem;
import com.dk.oa.entity.DealRecord;
import com.dk.oa.entity.Employee;
import com.dk.oa.global.Contant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("cliamVoucherBiz")
public class ClaimVoucherBizImpl implements ClaimVoucherBiz {
    @Autowired
    private ClaimVoucherDao claimVoucherDao;
    @Autowired
    private ClaimVoucherItemDao claimVoucherItemDao;
    @Autowired
    private DealRecordDao dealRecordDao;
    @Autowired
    private EmployeeDao employeeDao;

    //保存报销单
    @Override
    public void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        //设置创建时间为系统当前时间
        claimVoucher.setCreateTime(new Date());
        //待处理人设置为创建者
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        //设置报销单状态
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        //保存到数据库
        claimVoucherDao.insert(claimVoucher);

        for (ClaimVoucherItem item : items) {
            //报销单编号
            item.setClaimVoucherId(claimVoucher.getId());
            claimVoucherItemDao.insert(item);
        }
    }

    @Override
    public ClaimVoucher get(int id) {
        return claimVoucherDao.select(id);
    }

    @Override
    public List<ClaimVoucherItem> getItems(int cvid) {
        return claimVoucherItemDao.selectByClaimVoucher(cvid);
    }

    @Override
    public List<DealRecord> getRecords(int cvid) {
        return dealRecordDao.selectByClaimVoucher(cvid);
    }

    @Override
    public List<ClaimVoucher> getForSelf(String sn) {
        return claimVoucherDao.selectByCreateSn(sn);
    }

    @Override
    public List<ClaimVoucher> getForDeal(String sn) {
        return claimVoucherDao.selectByNextDealSn(sn);
    }

    //修改报销单
    @Override
    public void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        //1,更新基本信息
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        //待处理人是等于用户
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        claimVoucherDao.update(claimVoucher);

        //2，更新条目信息
        //根据id获取数据库原有的集合
        List<ClaimVoucherItem> olds = claimVoucherItemDao.selectByClaimVoucher(claimVoucher.getId());
        for (ClaimVoucherItem old : olds) {
            boolean isHave = false;
            //迭代将要更新的条目集合
            for (ClaimVoucherItem item : items) {
                if (item.getId() == old.getId()) {
                    isHave = true;
                    break;
                }
            }
            if (!isHave) {
                //（1）删除不需要的item
                claimVoucherItemDao.delete(old.getId());
            }
        }

        for (ClaimVoucherItem item : items) {
            //为item设置报销单id
            item.setClaimVoucherId(claimVoucher.getId());
            if (item.getId() != null && item.getId()>0) {
                //（2）修改item
                claimVoucherItemDao.update(item);
            } else {
                //（3）插入item新
                claimVoucherItemDao.insert(item);
            }
        }

    }

    //提交报销单
    @Override
    public void submit(int id) {
        //得到报销单
        ClaimVoucher claimVoucher = claimVoucherDao.select(id);
        //得到创建人
        Employee employee = employeeDao.select(claimVoucher.getCreateSn());
        //报销单更新
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_SUBMIT);
        claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(employee.getDepartmentSn(), Contant.POST_FM).get(0).getSn());
        claimVoucherDao.update(claimVoucher);

        //保存操作记录
        DealRecord dealRecord = new DealRecord();
        dealRecord.setDealWay(Contant.DEAL_SUBMIT);
        dealRecord.setDealSn(employee.getSn());
        dealRecord.setClaimVoucherId(id);
        dealRecord.setDealResult(Contant.CLAIMVOUCHER_SUBMIT);
        dealRecord.setDealTime(new Date());
        dealRecord.setComment("无");
        dealRecordDao.insert(dealRecord);
    }

    //处理报销单
    @Override
    public void deal(DealRecord dealRecord) {
        //获得报销单
        ClaimVoucher claimVoucher = claimVoucherDao.select(dealRecord.getClaimVoucherId());
        Employee employee = employeeDao.select(dealRecord.getDealSn());
        //设置处理时间为当前时间
        dealRecord.setDealTime(new Date());

        //审核通过（条件1：审核金额<5000，条件2：审核人为总经理）
        if (dealRecord.getDealWay().equals(Contant.DEAL_PASS)) {
                //小于5000不需要复审
            if (claimVoucher.getTotalAmount() <= Contant.LIMIT_CHECK || employee.getPost().equals(Contant.POST_GM)) {
                //改变状态为已审核
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_APPROVED);
                //得到待处理人（财务部门人员）
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null, Contant.POST_CASHIER).get(0).getSn());

                //处理结果
                dealRecord.setDealResult(Contant.CLAIMVOUCHER_APPROVED);
            } else {
                //待复审
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_RECHECK);
                //总经理处理
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null, Contant.POST_GM).get(0).getSn());

                dealRecord.setDealResult(Contant.CLAIMVOUCHER_RECHECK);
            }
        }
        //打回审核
        else if (dealRecord.getDealWay().equals(Contant.DEAL_BACK)) {
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_BACK);
            //创建者处理
            claimVoucher.setNextDealSn(claimVoucher.getCreateSn());

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_BACK);
        }
        //拒绝
        else if (dealRecord.getDealWay().equals(Contant.DEAL_REJECT)) {
            //直接终止报销单
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_TERMINATED);
            claimVoucher.setNextDealSn(null);

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_TERMINATED);
        }
        //打款
        else if (dealRecord.getDealWay().equals(Contant.DEAL_PAID)) {
            //打款成功
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_PAID);
            claimVoucher.setNextDealSn(null);

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_PAID);
        }
        //更新报销单
        claimVoucherDao.update(claimVoucher);
        //插入操作记录
        dealRecordDao.insert(dealRecord);
    }

}
