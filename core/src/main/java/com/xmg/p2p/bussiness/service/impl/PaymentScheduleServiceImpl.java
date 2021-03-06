package com.xmg.p2p.bussiness.service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.Consts;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.bussiness.domain.BidRequest;
import com.xmg.p2p.bussiness.domain.PaymentSchedule;
import com.xmg.p2p.bussiness.domain.PaymentScheduleDetail;
import com.xmg.p2p.bussiness.mapper.BidMapper;
import com.xmg.p2p.bussiness.mapper.PaymentScheduleDetailMapper;
import com.xmg.p2p.bussiness.mapper.PaymentScheduleMapper;
import com.xmg.p2p.bussiness.query.ReturnMoneyQueryObject;
import com.xmg.p2p.bussiness.service.IAccountFlowService;
import com.xmg.p2p.bussiness.service.IBidRequestService;
import com.xmg.p2p.bussiness.service.IPaymentScheduleService;
import com.xmg.p2p.bussiness.service.ISystemAccountService;
import com.xmg.p2p.bussiness.util.CalculatetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PaymentScheduleServiceImpl implements IPaymentScheduleService {

    @Autowired
    private PaymentScheduleMapper paymentScheduleMapper;
    @Autowired
    private PaymentScheduleDetailMapper paymentScheduleDetailMapper;
    @Autowired
    private IBidRequestService bidRequestService;
    @Autowired
    private BidMapper bidMapper;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IAccountFlowService accountFlowService;
    @Autowired
    private ISystemAccountService systemAccountService;
    @Autowired
    private IUserinfoService userinfoService;

    public PageResult query(ReturnMoneyQueryObject qo) {
        int count = paymentScheduleMapper.queryForCount(qo);
        if(count > 0){
            List<PaymentSchedule> data = paymentScheduleMapper.queryForData(qo);
            return new PageResult(qo.getCurrentPage(),qo.getPageSize(),count,data);
        }
        return PageResult.getEmpty(qo.getPageSize());
    }

    public void returnMoney(Long id) {
        //??????????????????
        PaymentSchedule paymentSchedule = paymentScheduleMapper.selectByPrimaryKey(id);
        Account account = accountService.get(UserContext.getCurrent().getId());
        if (paymentSchedule!=null
            &&paymentSchedule.getReturnType() == Consts.PAYMENT_STATE_NORMAL//??????????????????
            &&paymentSchedule.getBorrowUser().getId().equals(UserContext.getCurrent().getId())//????????????????????????
            &&account.getUsableAmount().compareTo(paymentSchedule.getTotalAmount())>=0){//????????????????????????????????????
             //???????????????
            //1.??????????????????,??????????????????
            account.setUsableAmount(account.getUsableAmount().subtract(paymentSchedule.getTotalAmount()));
            accountFlowService.createReturnFlow(paymentSchedule,account);
            //2.??????????????????
            account.setUnReturnAmount(account.getUnReturnAmount().subtract(paymentSchedule.getTotalAmount()));
            //3.????????????????????????
            account.setRemainBorrowLimit(account.getRemainBorrowLimit().add(paymentSchedule.getPrincipal()));
            accountService.update(account);

             //???????????????
            Map<Long,Account> updates = new HashMap<Long, Account>();
            List<PaymentScheduleDetail> paymentScheduleDetails = paymentSchedule.getPaymentScheduleDetails();
            for (PaymentScheduleDetail paymentScheduleDetail : paymentScheduleDetails) {
                //???????????????id
                Long bidId = paymentScheduleDetail.getToLogininfoId();
                Account bidAccount = updates.get(bidId);
                if(bidAccount == null) {
                    bidAccount = accountService.get(bidId);
                    updates.put(bidId,bidAccount);
                }
                //1.?????????????????????,??????????????????
                bidAccount.setUsableAmount(bidAccount.getUsableAmount().add(paymentScheduleDetail.getTotalAmount()));
                accountFlowService.createReceiveFlow(paymentScheduleDetail,bidAccount);
                //2.??????????????????
                bidAccount.setUnReceiveInterest(bidAccount.getUnReceiveInterest().subtract(paymentScheduleDetail.getInterest()));
                bidAccount.setUnReceivePrincipal(bidAccount.getUnReceivePrincipal().subtract(paymentScheduleDetail.getPrincipal()));
                //3.?????????????????????,????????????
                BigDecimal InterestManagerFee = CalculatetUtil.calInterestManagerCharge(paymentScheduleDetail.getInterest());
                bidAccount.setUsableAmount(bidAccount.getUsableAmount().subtract(InterestManagerFee));
                accountFlowService.payInterestManagerFee(paymentScheduleDetail,bidAccount,InterestManagerFee);
                //4.???????????????????????????,????????????
                systemAccountService.chargeInterestManagerFee(paymentScheduleDetail,InterestManagerFee);
            }
            for (Account bidAccount : updates.values()){
                accountService.update(bidAccount);
            }
                //??????????????????
                paymentSchedule.setState(Consts.PAYMENT_STATE_DONE);
                paymentSchedule.setPayDate(new Date());
                paymentScheduleMapper.updateByPrimaryKey(paymentSchedule);
                //??????????????????????????????????????????????????????????????????
                paymentScheduleDetailMapper.updatePayDate(id,new Date());


                //?????????????????????????????????????????????????????????,??????????????????????????????????????????
                List<PaymentSchedule> paymentSchedules = paymentScheduleMapper.selectByUserId(UserContext.getCurrent().getId());
                int totalCount = 0;
            for (PaymentSchedule schedule : paymentSchedules) {
                if (schedule.getState() == Consts.PAYMENT_STATE_DONE){
                    totalCount ++;
                }
                //?????????????????????????????????????????????????????????????????????,???????????????????????????????????????
                if(totalCount == paymentSchedules.size()){
                    BidRequest bidRequest = bidRequestService.get(paymentSchedule.getBidRequestId());
                    bidRequest.setBidRequestState(Consts.BIDREQUEST_STATE_COMPLETE_PAY_BACK);
                    bidRequestService.updateByPrimaryKey(bidRequest);
                    bidMapper.updateState(paymentSchedule.getBidRequestId(),Consts.BIDREQUEST_STATE_COMPLETE_PAY_BACK);
                    Userinfo userinfo = userinfoService.get(UserContext.getCurrent().getId());
                    userinfo.setBitState(BitStatesUtils.removeState(userinfo.getBitState(),BitStatesUtils.HASBINDREQUEST));
                    userinfoService.update(userinfo);
                }
            }
        }
    }
}
