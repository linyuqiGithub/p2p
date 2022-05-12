package com.xmg.p2p.bussiness.service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.util.Consts;
import com.xmg.p2p.bussiness.domain.*;
import com.xmg.p2p.bussiness.mapper.AccountFlowMapper;
import com.xmg.p2p.bussiness.mapper.PaymentScheduleMapper;
import com.xmg.p2p.bussiness.query.AccountFlowQueryObject;
import com.xmg.p2p.bussiness.service.IAccountFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class AccountFlowServiceImpl implements IAccountFlowService {
    @Autowired
    private AccountFlowMapper accountFlowMapper;
    @Autowired
    private PaymentScheduleMapper paymentScheduleMapper;

    //每个流水对象的共同代码进行抽取
    public AccountFlow createBaseFlow(Account account){
        AccountFlow ac = new AccountFlow();
        ac.setAccount(account);
        //设置变化后的可用金额
        ac.setBalance(account.getUsableAmount());
        //设置变化后的冻结金额
        ac.setFreezed(account.getFreezedAmount());
        ac.setActionTime(new Date());
        return ac;
    }

    public void rechargeAccountFlow(RechargeOffline rechargeOffline, Account account) {
        //创建基本流水对象
        AccountFlow ac = createBaseFlow(account);
        ac.setNote("线下充值成功,充值总额为"+rechargeOffline.getAmount());
        ac.setAccountActionType(Consts.ACCOUNT_ACTIONTYPE_RECHARGE_OFFLINE);
        ac.setAmount(rechargeOffline.getAmount());
        accountFlowMapper.insert(ac);
    }

    public void createBidFlow(Bid bid, Account account) {
        AccountFlow ac = createBaseFlow(account);
        ac.setNote("已投资"+bid.getBidRequestTitle()+",冻结金额增加："+bid.getAvailableAmount()+"可用余额减少："+bid.getAvailableAmount());
        ac.setAccountActionType(Consts.ACCOUNT_ACTIONTYPE_BID_FREEZED);
        ac.setAmount(bid.getAvailableAmount());
        accountFlowMapper.insert(ac);
    }

    public void returnMoneyFlow(Bid bid, Account account) {
        //创建基本流水对象
        AccountFlow ac = createBaseFlow(account);
        ac.setNote("投资"+bid.getBidRequestTitle()+"失败,退款成功,退款金额为"+bid.getAvailableAmount()+",取消冻结金额："+bid.getAvailableAmount());
        ac.setAccountActionType(Consts.ACCOUNT_ACTIONTYPE_BID_UNFREEZED);
        ac.setAmount(bid.getAvailableAmount());
        accountFlowMapper.insert(ac);
    }

    public void createRequestSuccessFlow(BidRequest bidRequest, Account accountCreateUser) {
        AccountFlow ac = createBaseFlow(accountCreateUser);
        ac.setNote("借款"+bidRequest.getTitle()+"成功,收到借款金额为"+bidRequest.getBidRequestAmount());
        ac.setAccountActionType(Consts.ACCOUNT_ACTIONTYPE_BIDREQUEST_SUCCESSFUL);
        ac.setAmount(bidRequest.getBidRequestAmount());
        accountFlowMapper.insert(ac);

    }

    public void createManagerFeeFlow(BigDecimal managerChargeFee,BidRequest bidRequest, Account accountCreateUser) {
        AccountFlow ac = createBaseFlow(accountCreateUser);
        ac.setNote("支付借款"+bidRequest.getTitle()+"相关手续费为"+managerChargeFee);
        ac.setAccountActionType(Consts.ACCOUNT_ACTIONTYPE_CHARGE);
        ac.setAmount(managerChargeFee);
        accountFlowMapper.insert(ac);
    }

    public void createBidSuccessFlow(Bid bid, Account account) {
        AccountFlow ac = createBaseFlow(account);
        ac.setNote("投资"+bid.getBidRequestTitle()+"成功,投资金额为"+bid.getAvailableAmount());
        ac.setAccountActionType(Consts.ACCOUNT_ACTIONTYPE_BID_SUCCESSFUL);
        ac.setAmount(bid.getAvailableAmount());
        accountFlowMapper.insert(ac);

    }

    public void createReturnFlow(PaymentSchedule paymentSchedule, Account account) {
        AccountFlow ac =createBaseFlow(account);
        ac.setNote("借款"+paymentSchedule.getBidRequestTitle()+",第"+paymentSchedule.getMonthIndex()+"期还款成功,还款金额为"+paymentSchedule.getTotalAmount());
        ac.setAccountActionType(Consts.ACCOUNT_ACTIONTYPE_RETURN_MONEY);
        ac.setAmount(paymentSchedule.getTotalAmount());
        accountFlowMapper.insert(ac);
    }

    public void createReceiveFlow(PaymentScheduleDetail paymentScheduleDetail, Account account) {
        AccountFlow ac = createBaseFlow(account);
        PaymentSchedule paymentSchedule = paymentScheduleMapper.selectByPrimaryKey(paymentScheduleDetail.getPaymentScheduleId());
        ac.setNote("收到借款"+paymentSchedule.getBidRequestTitle()+"的第"+paymentSchedule.getMonthIndex()+"期的还款,还款金额为"+paymentScheduleDetail.getTotalAmount());
        ac.setAccountActionType(Consts.ACCOUNT_ACTIONTYPE_CALLBACK_MONEY);
        ac.setAmount(paymentScheduleDetail.getBidAmount());
        accountFlowMapper.insert(ac);
    }

    public void payInterestManagerFee(PaymentScheduleDetail paymentScheduleDetail, Account bidAccount,BigDecimal InterestManagerFee) {
        AccountFlow ac = createBaseFlow(bidAccount);
        PaymentSchedule paymentSchedule = paymentScheduleMapper.selectByPrimaryKey(paymentScheduleDetail.getPaymentScheduleId());
        ac.setNote("收到借款"+paymentSchedule.getBidRequestTitle()+"的第"+paymentSchedule.getMonthIndex()+"期的还款时支付的利息管理费："+InterestManagerFee);
        ac.setAccountActionType(Consts.ACCOUNT_ACTIONTYPE_INTEREST_SHARE);
        ac.setAmount(paymentScheduleDetail.getBidAmount());
        accountFlowMapper.insert(ac);
    }

    @Override
    public void moneyWithDrawApplyFlow(MoneyWithdraw m, Account account) {
        AccountFlow ac = createBaseFlow(account);
        ac.setNote("提现冻结金额:"+m.getAmount());
        ac.setAccountActionType(Consts.ACCOUNT_ACTIONTYPE_WITHDRAW_FREEZED);
        ac.setAmount(m.getAmount());
        accountFlowMapper.insert(ac);
    }

    @Override
    public void withDrawChargeFeeFlow(MoneyWithdraw m, Account account) {
        AccountFlow ac = createBaseFlow(account);
        ac.setNote("提现成功扣除手续费:"+m.getCharge()+"元");
        ac.setAccountActionType(Consts.ACCOUNT_ACTIONTYPE_WITHDRAW_MANAGE_CHARGE);
        ac.setAmount(m.getCharge());
        accountFlowMapper.insert(ac);
    }

    @Override
    public void withDrawSuccess(BigDecimal realWithdrawFee, Account account) {
        AccountFlow ac = createBaseFlow(account);
        ac.setNote("成功提现金额:"+realWithdrawFee+"元");
        ac.setAccountActionType(Consts.ACCOUNT_ACTIONTYPE_WITHDRAW);
        ac.setAmount(realWithdrawFee);
        accountFlowMapper.insert(ac);
    }

    @Override
    public void withDrawFailedFlow(MoneyWithdraw m, Account account) {
        AccountFlow ac = createBaseFlow(account);
        ac.setNote("提现失败,减少冻结金额"+m.getAmount()+"元");
        ac.setAccountActionType(Consts.ACCOUNT_ACTIONTYPE_WITHDRAW_UNFREEZED);
        ac.setAmount(m.getAmount());
        accountFlowMapper.insert(ac);
    }

    @Override
    public PageResult QueryAccountFlow(AccountFlowQueryObject qo) {
        int count = accountFlowMapper.queryForCount(qo);
        if (count != 0){
            List<AccountFlow> accountFlows = accountFlowMapper.queryAccountFlow(qo);
            return new PageResult(qo.getCurrentPage(), qo.getPageSize(), Integer.valueOf(count),accountFlows);
        }
        return PageResult.getEmpty(qo.getPageSize());
    }


}
