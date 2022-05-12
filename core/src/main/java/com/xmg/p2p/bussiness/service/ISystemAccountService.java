package com.xmg.p2p.bussiness.service;

import com.xmg.p2p.bussiness.domain.BidRequest;
import com.xmg.p2p.bussiness.domain.MoneyWithdraw;
import com.xmg.p2p.bussiness.domain.PaymentScheduleDetail;
import com.xmg.p2p.bussiness.domain.SystemAccount;

import java.math.BigDecimal;

/**
 * 系统账户服务
 */
public interface ISystemAccountService {
    void updateByPrimaryKey(SystemAccount record);

    /**
     * 平台收取借款手续费并创建手续费流水
     * @param bidRequest
     * @param managerChargeFee
     */
    void chargeManagerFee(BidRequest bidRequest, BigDecimal managerChargeFee);

    /**
     * 平台收取投资人收到还款时的利息管理费
     * @param paymentScheduleDetail
     * @param managerChargeFee
     */
    void chargeInterestManagerFee(PaymentScheduleDetail paymentScheduleDetail, BigDecimal managerChargeFee);

    /**
     * 平台收取提现手续费
      * @param m
     */
    void chargeWithdrawFee(MoneyWithdraw m);
}
