package com.xmg.p2p.bussiness.service.impl;

import com.xmg.p2p.bussiness.domain.*;
import com.xmg.p2p.base.util.Consts;
import com.xmg.p2p.bussiness.mapper.PaymentScheduleMapper;
import com.xmg.p2p.bussiness.mapper.SystemAccountFlowMapper;
import com.xmg.p2p.bussiness.mapper.SystemAccountMapper;
import com.xmg.p2p.bussiness.service.ISystemAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class SystemAccountServiceImpl implements ISystemAccountService {
    @Autowired
    private SystemAccountMapper systemAccountMapper;
    @Autowired
    private SystemAccountFlowMapper systemAccountFlowMapper;
    @Autowired
    private PaymentScheduleMapper paymentScheduleMapper;
    public void updateByPrimaryKey(SystemAccount record) {
        int row = systemAccountMapper.updateByPrimaryKey(record);
        if(row == 0){
            throw new RuntimeException("乐观锁失败");
        }
    }

    public void chargeManagerFee(BidRequest bidRequest, BigDecimal managerChargeFee) {
        //获取当前系统账户
        SystemAccount sa = systemAccountMapper.selectCurrent();
        //收取手续费,增加可用余额
        sa.setTotalBalance(sa.getTotalBalance().add(managerChargeFee));
        this.updateByPrimaryKey(sa);
        //创建手续费流水对象
        SystemAccountFlow sf = new SystemAccountFlow();
        sf.setAccountActionType(Consts.SYSTEM_ACCOUNT_ACTIONTYPE_MANAGE_CHARGE);
        sf.setAmount(managerChargeFee);
        sf.setBalance(sa.getTotalBalance());
        sf.setCreateDate(new Date());
        sf.setNote("借款"+bidRequest.getTitle()+"成功,平台收取手续费"+managerChargeFee);
        sf.setFreezedAmount(sa.getFreezedAmount());
        systemAccountFlowMapper.insert(sf);
    }

    public void chargeInterestManagerFee(PaymentScheduleDetail paymentScheduleDetail, BigDecimal interestManagerChargeFee) {
       //获取当前系统账户
       SystemAccount sa = systemAccountMapper.selectCurrent();
       sa.setTotalBalance(sa.getTotalBalance().add(interestManagerChargeFee));
       systemAccountMapper.updateByPrimaryKey(sa);
       //生成收取利息管理费流水
        SystemAccountFlow sf = new SystemAccountFlow();
        sf.setAccountActionType(Consts.SYSTEM_ACCOUNT_ACTIONTYPE_INTREST_MANAGE_CHARGE);
        sf.setAmount(interestManagerChargeFee);
        sf.setBalance(sa.getTotalBalance());
        sf.setCreateDate(new Date());
        PaymentSchedule paymentSchedule = paymentScheduleMapper.selectByPrimaryKey(paymentScheduleDetail.getPaymentScheduleId());
        sf.setNote("收款成功,平台收取借款"+paymentSchedule.getBidRequestTitle()+"的第"+paymentSchedule.getMonthIndex()+"期的利息管理费"+interestManagerChargeFee);
        sf.setFreezedAmount(sa.getFreezedAmount());
        systemAccountFlowMapper.insert(sf);
    }

    @Override
    public void chargeWithdrawFee(MoneyWithdraw m) {
        //获取当前系统用户
        SystemAccount sa = systemAccountMapper.selectCurrent();
        //增加系统用户可用余额
        sa.setTotalBalance(sa.getTotalBalance().add(m.getCharge()));
        systemAccountMapper.updateByPrimaryKey(sa);
        //生成收取提现手续费流水
        SystemAccountFlow sf = new SystemAccountFlow();
        sf.setAccountActionType(Consts.ACCOUNT_ACTIONTYPE_WITHDRAW_MANAGE_CHARGE);
        sf.setAmount(m.getCharge());
        sf.setBalance(sa.getTotalBalance());
        sf.setCreateDate(new Date());
        sf.setNote("收款成功，平台收取提现手续费:"+m.getCharge());
        sf.setFreezedAmount(sa.getFreezedAmount());
        systemAccountFlowMapper.insert(sf);
    }


}
