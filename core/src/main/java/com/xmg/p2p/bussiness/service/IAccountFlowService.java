package com.xmg.p2p.bussiness.service;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.bussiness.domain.*;
import com.xmg.p2p.bussiness.query.AccountFlowQueryObject;

import java.math.BigDecimal;

/**
 * 账户流水服务
 */
public interface IAccountFlowService {
    /**
     * 创建线下充值流水对象
     * @param rechargeOffline
     * @param account
     */
    void rechargeAccountFlow(RechargeOffline rechargeOffline, Account account);

    /**
     * 创建投标流水对象(还没通过审核，不算投标成功)
     * @param bid
     * @param account
     */
    void createBidFlow(Bid bid, Account account);

    /**
     * 创建退钱流水
     * @param bid
     * @param account
     */
    void returnMoneyFlow(Bid bid, Account account);

    /**
     * 创建借款成功流水
     * @param bidRequest
     * @param accountCreateUser
     */
    void createRequestSuccessFlow(BidRequest bidRequest, Account accountCreateUser);

    /**
     * 创建支付借款手续费流水
     * @param managerChargeFee
     * @param bidRequest
     * @param accountCreateUser
     */
    void createManagerFeeFlow(BigDecimal managerChargeFee,BidRequest bidRequest, Account accountCreateUser);

    /**
     * 创建投标成功流水
     * @param bid
     * @param account
     */
    void createBidSuccessFlow(Bid bid, Account account);

    /**
     * 生成还款流水
     * @param paymentSchedule
     * @param account
     */
    void createReturnFlow(PaymentSchedule paymentSchedule, Account account);

    /**
     * 创建投资人收款流水
     * @param paymentScheduleDetail
     * @param account
     */
    void createReceiveFlow(PaymentScheduleDetail paymentScheduleDetail, Account account);

    /**
     * 生成支付还款利息管理费流水
     * @param paymentScheduleDetail
     * @param bidAccount
     * @param InterestManagerFee
     */
    void payInterestManagerFee(PaymentScheduleDetail paymentScheduleDetail, Account bidAccount,BigDecimal InterestManagerFee);

    /**
     * 创建提现冻结流水
     * @param m
     * @param account
     */
    void moneyWithDrawApplyFlow(MoneyWithdraw m, Account account);

    /**
     * 创建提现手续费的流水
     * @param m
     * @param account
     */
    void withDrawChargeFeeFlow(MoneyWithdraw m, Account account);

    /**
     * 创建提现成功的流水
     * @param realWithdrawFee
     * @param account
     */
    void withDrawSuccess(BigDecimal realWithdrawFee, Account account);

    /**
     * 创建提现拒绝流水
     * @param m
     * @param account
     */
    void withDrawFailedFlow(MoneyWithdraw m, Account account);

    /**
     * 查询流水
     */
    PageResult QueryAccountFlow(AccountFlowQueryObject qo);
}
