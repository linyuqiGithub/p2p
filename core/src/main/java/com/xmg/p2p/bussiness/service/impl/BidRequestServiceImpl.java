package com.xmg.p2p.bussiness.service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.bussiness.mapper.BidRequestAuditMapper;
import com.xmg.p2p.bussiness.query.BidRequestQueryObject;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.bussiness.util.CalculatetUtil;
import com.xmg.p2p.base.util.Consts;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.bussiness.domain.*;
import com.xmg.p2p.bussiness.mapper.BidMapper;
import com.xmg.p2p.bussiness.mapper.BidRequestMapper;
import com.xmg.p2p.bussiness.mapper.PaymentScheduleDetailMapper;
import com.xmg.p2p.bussiness.mapper.PaymentScheduleMapper;
import com.xmg.p2p.bussiness.service.IAccountFlowService;
import com.xmg.p2p.bussiness.service.IBidRequestService;
import com.xmg.p2p.bussiness.service.ISystemAccountService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


@Service
public class BidRequestServiceImpl implements IBidRequestService {

    @Autowired
    private BidRequestMapper bidRequestMapper;
    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private BidRequestAuditMapper bidRequestAuditMapper;
    @Autowired
    private BidMapper bidMapper;
    @Autowired
    private IAccountFlowService accountFlowService;
    @Autowired
    private ISystemAccountService systemAccountService;
    @Autowired
    private PaymentScheduleMapper paymentScheduleMapper;
    @Autowired
    private PaymentScheduleDetailMapper paymentScheduleDetailMapper;

    public int updateByPrimaryKey(BidRequest record) {
        int row = bidRequestMapper.updateByPrimaryKey(record);
        if(row == 0){
            throw new RuntimeException("乐观锁失败");
        }
        return row;
    }

    public void apply(BidRequest vo) {
        //得到当前用户
        Userinfo userinfo = userinfoService.get(UserContext.getCurrent().getId());
        Account account = accountService.get(UserContext.getCurrent().getId());
        //前台的所有判断，后台都需要重新判断一次
        if(userinfo.getIsBasicInfo()//用户是否填写基本资料
                && userinfo.getIsRealAuth()//用户是否完成实名认证
                && userinfo.getScore() >= Consts.BASIC_BORROW_SCORE//用户风控分数大于30分
                && userinfo.getIsVedioAuth()//用户完成视频认证
                &&(!BitStatesUtils.hasState(userinfo.getBitState(),BitStatesUtils.HASBINDREQUEST))//当前用户没有借款在审核流程中
                && vo.getBidRequestAmount().compareTo(Consts.SMALLEST_BIDREQUEST_AMOUNT)>=0//借款金额大于系统规定的最小借款金额
                && vo.getBidRequestAmount().compareTo(account.getRemainBorrowLimit())<=0//借款金额小于剩余信用额度
                && vo.getCurrentRate().compareTo(Consts.SMALLEST_CURRENT_RATE)>=0//借款利率大于系统最小借款利息
                && vo.getCurrentRate().compareTo(Consts.MAX_CURRENT_RATE)<=0//借款利率小于系统最大借款利息
                && vo.getMinBidAmount().compareTo(Consts.SMALLEST_BID_AMOUNT)>=0){//投标金额大于系统最小投标金额
              //创建借款对象,传入的借款对象参数只能作为参数的封装，不能直接使用
              BidRequest br = new BidRequest();
              br.setApplyTime(new Date());//申请时间
              br.setCreateUser(UserContext.getCurrent());
              br.setBidRequestAmount(vo.getBidRequestAmount());//借款金额
              br.setCurrentRate(vo.getCurrentRate());
              br.setDisableDays(vo.getDisableDays());//招标天数
              br.setMonthes2Return(vo.getMonthes2Return());//借款期限
              br.setReturnType(vo.getReturnType());//还款类型(只做了按月分期)
              br.setMinBidAmount(vo.getMinBidAmount());//最小投标
              br.setTitle(vo.getTitle());
              br.setDescription(vo.getDescription());
              br.setBidRequestState(Consts.BIDREQUEST_STATE_PUBLISH_PENDING);//待发布状态
              br.setBidRequestType(Consts.BIDREQUEST_TYPE_NORMAL);//标的类型
              br.setTotalRewardAmount(CalculatetUtil.calTotalInterest(
                      vo.getReturnType(),vo.getBidRequestAmount(),vo.getCurrentRate(),vo.getMonthes2Return()));
              bidRequestMapper.insert(br);
              userinfo.setBitState(BitStatesUtils.addState(userinfo.getBitState(),BitStatesUtils.HASBINDREQUEST));
              userinfoService.update(userinfo);
        }
    }

    public void publishAudit(Long id, String remark, int state) {
        //得到借款对象
        BidRequest br = bidRequestMapper.selectByPrimaryKey(id);
        //借款必须是待发布状态
        if(br.getBidRequestState()==Consts.BIDREQUEST_STATE_PUBLISH_PENDING){
            //创建借款审核对象
            BidRequestAudit ba = new BidRequestAudit();
            ba.setApplier(br.getCreateUser());
            ba.setApplyTime(br.getApplyTime());
            ba.setAuditor(UserContext.getCurrent());
            ba.setAuditTime(new Date());
            ba.setRemark(remark);
            ba.setAuditType(BidRequestAudit.PUBLISH_AUDIT);//设置该借款审核对象为发标前审核对象
            ba.setState(state);//通过还是拒绝
            ba.setBidRequestId(id);
            //创建借款审核对象
            bidRequestAuditMapper.insert(ba);

            Userinfo userinfo = userinfoService.get(br.getCreateUser().getId());
            //如果审核成功
            if(state == BidRequestAudit.PASS_AUDI){
                //设置此借款对象招标中
                br.setBidRequestState(Consts.BIDREQUEST_STATE_BIDDING);
                //发布时间
                br.setPublishTime(new Date());
                //设置到期时间(发布时间+还款天数)
                br.setDisableDate(DateUtils.addDays(new Date(),br.getDisableDays()));
                br.setNote(remark);
            }else {
                //审核拒绝
                //设置借款对象的状态码
                br.setBidRequestState(Consts.BIDREQUEST_STATE_PUBLISH_REFUSE);
                //去除用户对象的待发布状态码
                userinfo.setBitState(BitStatesUtils.removeState(userinfo.getBitState(),Consts.BIDREQUEST_STATE_PUBLISH_PENDING));
                //更新用户对象
                userinfoService.update(userinfo);
            }
               //不管审核成功还是失败，都需要更新借款对象
               bidRequestMapper.updateByPrimaryKey(br);

        }

    }

    public BidRequest get(Long id) {
        return bidRequestMapper.selectByPrimaryKey(id);
    }

    /**
     * 投标,更新账户和标对象,同时新增投标流水对象
     * @param bidRequestId
     * @param amount
     */
    public void bid(Long bidRequestId, BigDecimal amount) {
        BidRequest bidRequest = bidRequestMapper.selectByPrimaryKey(bidRequestId);
        Account account = accountService.get(UserContext.getCurrent().getId());
        //判断
        if(bidRequest != null //借款对象不为空
           && bidRequest.getBidRequestState() == Consts.BIDREQUEST_STATE_BIDDING //借款招标中
           && bidRequest.getCreateUser() != UserContext.getCurrent() //投标对象不能为招标对象
           && account.getUsableAmount().compareTo(amount)>=0 //投标人账户余额大于投标金额
           && bidRequest.getRemainAmount().compareTo(amount)>=0 //标剩余可投金额大于投标金额
           && amount.compareTo(bidRequest.getMinBidAmount())>=0){ //投标金额大于最小投标金额
            //开始投标
            //创建投标对象并保存对象
            Bid bid = new Bid();
            bid.setActualRate(bidRequest.getCurrentRate());
            bid.setAvailableAmount(amount);
            bid.setBidRequestId(bidRequestId);
            bid.setBidRequestState(bidRequest.getBidRequestState());
            bid.setBidRequestTitle(bidRequest.getTitle());
            bid.setBidUser(UserContext.getCurrent());
            bid.setBidTime(new Date());
            bidMapper.insert(bid);
            //减少投标人账户可用余额和增加冻结余额并更新
            account.setUsableAmount(account.getUsableAmount().subtract(amount));
            account.setFreezedAmount(account.getFreezedAmount().add(amount));
            accountService.update(account);
            //设置投标次数和已投总金额
            bidRequest.setBidCount(bidRequest.getBidCount()+1);
            bidRequest.setCurrentSum(bidRequest.getCurrentSum().add(amount));
            //判断借款是否已经投满，如果投满，进入满标一审
            if(bidRequest.getCurrentSum().equals(bidRequest.getBidRequestAmount())){
                bidRequest.setBidRequestState(Consts.BIDREQUEST_STATE_APPROVE_PENDING_1);
                //将所有属于当前借款的投标对象状态设置为满标一审
                bidMapper.updateState(bidRequestId,Consts.BIDREQUEST_STATE_APPROVE_PENDING_1);
            }
            //新增一个投标流水
            accountFlowService.createBidFlow(bid,account);
            //更新借款对象
            bidRequestMapper.updateByPrimaryKey(bidRequest);

        }
    }

    public void audit1(Long id, String remark, int state) {
        BidRequest bidRequest = bidRequestMapper.selectByPrimaryKey(id);
        //借款对象不为空且借款对象处于满标一审状态
        if( bidRequest != null && bidRequest.getBidRequestState() == Consts.BIDREQUEST_STATE_APPROVE_PENDING_1){
            //创建审核对象
            BidRequestAudit bidRequestAudit = new BidRequestAudit();
            bidRequestAudit.setBidRequestId(id);
            bidRequestAudit.setState(state);
            bidRequestAudit.setAuditType(BidRequestAudit.FULL_AUDIT_1);
            bidRequestAudit.setRemark(remark);
            bidRequestAudit.setAuditTime(new Date());
            bidRequestAudit.setAuditor(UserContext.getCurrent());
            bidRequestAudit.setApplier(bidRequest.getCreateUser());
            //正规的话应当是最后标的投满满标的一瞬间为满标一审申请时间，这里简单写
            bidRequestAudit.setApplyTime(new Date());
            //存储审核对象
            bidRequestAuditMapper.insert(bidRequestAudit);
            //审核通过，修改借款状态
            if(state == BidRequestAudit.PASS_AUDI){
                //修改借款状态为满标二审状态
                bidRequest.setBidRequestState(Consts.BIDREQUEST_STATE_APPROVE_PENDING_2);
                //修改该借款对应的投标对象为二审状态
                bidMapper.updateState(id,Consts.BIDREQUEST_STATE_APPROVE_PENDING_2);
            }else {
                //审核拒绝
                refuseAudit(bidRequest);
            }
            //更新借款对象的数据(不能直接使用Mapper的updateByPrimaryKey,需要乐观锁)
           this.updateByPrimaryKey(bidRequest);
        }
    }


    //审核拒绝
    private void refuseAudit(BidRequest bidRequest) {
        //将投资人的钱返还
        this.returnMoney(bidRequest);
        //修改借款人的状态码,去除借款状态码(去除后借款人才能再次发起借款)
        Userinfo userinfo = userinfoService.get(bidRequest.getCreateUser().getId());
        userinfo.setBitState(BitStatesUtils.removeState(userinfo.getBitState(),BitStatesUtils.HASBINDREQUEST));
        userinfoService.update(userinfo);
    }
    //退钱
    public void returnMoney(BidRequest bidRequest){
        Map<Long,Account> updates = new HashMap<Long, Account>();
        List<Bid> bids = bidRequest.getBids();
        //设置借款对应的所有投标对象状态为审核拒绝
        bidMapper.updateState(bidRequest.getId(), Consts.BIDREQUEST_STATE_REJECTED);
        //将每一个投标对象的金额进行返还
        for (Bid bid : bids) {
            Long accountId = bid.getBidUser().getId();
            //获取投标对象对应的账户对象
            Account account = updates.get(accountId);
            if(account == null){
                account = accountService.get(accountId);
                updates.put(accountId,account);
            }
            //返还账户的可用余额
            account.setUsableAmount(account.getUsableAmount().add(bid.getAvailableAmount()));
            //减少账户的冻结金额
            account.setFreezedAmount(account.getFreezedAmount().subtract(bid.getAvailableAmount()));
            accountService.update(account);
            //生成退钱流水
            accountFlowService.returnMoneyFlow(bid,account);
        }
        //修改投资对象的状态
        bidRequest.setBidRequestState(Consts.BIDREQUEST_STATE_REJECTED);
    }

    public void audit2(Long id, String remark, int state) {
        BidRequest bidRequest = bidRequestMapper.selectByPrimaryKey(id);
        //借款对象不为空且属于满标二审状态
        if(bidRequest != null && bidRequest.getBidRequestState() == Consts.BIDREQUEST_STATE_APPROVE_PENDING_2){
            //创建审核对象,并设置相关属性
            BidRequestAudit bidRequestAudit = new BidRequestAudit();
            bidRequestAudit.setBidRequestId(id);
            bidRequestAudit.setState(state);
            bidRequestAudit.setAuditType(BidRequestAudit.FULL_AUDIT_2);
            bidRequestAudit.setRemark(remark);
            bidRequestAudit.setAuditTime(new Date());
            bidRequestAudit.setAuditor(UserContext.getCurrent());
            bidRequestAudit.setApplier(bidRequest.getCreateUser());
            bidRequestAudit.setApplyTime(bidRequest.getApplyTime());
            bidRequestAuditMapper.insert(bidRequestAudit);
            //审核通过
            if(state == BidRequestAudit.PASS_AUDI){
                //1.借款对象的变化
                //1.1设置借款对象的状态为还款中状态
                bidRequest.setBidRequestState(Consts.BIDREQUEST_STATE_PAYING_BACK);
                //1.2设置借款对象对应的投标对象的状态为还款状态
                bidMapper.updateState(id,Consts.BIDREQUEST_STATE_PAYING_BACK);
                //2.借款人的变化
                //获取借款人的账户对象
                Account accountCreateUser = accountService.get(bidRequest.getCreateUser().getId());
                //借款人的对象信息
                Userinfo userinfoCreateUser = userinfoService.get(bidRequest.getCreateUser().getId());
                //2.1借款人增加可用余额(原可用余额+借款金额)
                accountCreateUser.setUsableAmount(accountCreateUser.getUsableAmount().add(bidRequest.getBidRequestAmount()));
                //2.2减少剩余信用额度(剩余信用额度-借款金额)
                accountCreateUser.setRemainBorrowLimit(accountCreateUser.getRemainBorrowLimit().subtract(bidRequest.getBidRequestAmount()));
                //2.3增加待还本息(原待还本息+借款待还本金+借款待还利息)
                accountCreateUser.setUnReturnAmount(accountCreateUser.getUnReturnAmount().add(bidRequest.getTotalRewardAmount()).add(bidRequest.getBidRequestAmount()));
                //2.4移除借款状态码
                userinfoCreateUser.setBitState(BitStatesUtils.removeState(userinfoCreateUser.getBitState(),BitStatesUtils.HASBINDREQUEST));
                //2.5生成借款成功流水
                accountFlowService.createRequestSuccessFlow(bidRequest,accountCreateUser);
                //2.6计算支付给平台手续费(由借款人支付)
                BigDecimal managerChargeFee = CalculatetUtil.calAccountManagementCharge(bidRequest.getBidRequestAmount());
                //2.7减少借款人的可用余额(扣除手续费)
                accountCreateUser.setUsableAmount(accountCreateUser.getUsableAmount().subtract(managerChargeFee));
                //2.8生成支付手续费流水(用户的流水)
                accountFlowService.createManagerFeeFlow(managerChargeFee,bidRequest,accountCreateUser);
                //2.9平台账户收取手续费,并生成收到手续费的流水(平台的流水)
                systemAccountService.chargeManagerFee(bidRequest,managerChargeFee);

                //3.投资人的变化
                List<Bid> bids = bidRequest.getBids();
                //缓存操作
                Map<Long,Account> updates = new HashMap<Long, Account>();
                //3.1遍历投标对象
                for (Bid bid : bids) {
                    Account account = updates.get(bid.getBidUser().getId());
                    if (account == null){
                       account = accountService.get(bid.getBidUser().getId());
                        updates.put(bid.getBidUser().getId(),account);
                    }
                    //3.2投资人减少冻结金额
                    account.setFreezedAmount(account.getFreezedAmount().subtract(bid.getAvailableAmount()));
                    //3.4生成投资成功流水
                    accountFlowService.createBidSuccessFlow(bid,account);
                }
                //4.生成还款对象PaymentSchedule(借款人还钱)和回款对象paymentscheduledetail(还款明细)针对投资人(投资人收钱)
                List<PaymentSchedule> paymentSchedules = createPaymentSchedules(bidRequest);
                for (PaymentSchedule paymentSchedule : paymentSchedules) {
                    List<PaymentScheduleDetail> pds = paymentSchedule.getPaymentScheduleDetails();
                    //遍历还款对象对应的每一个回款对象
                    for (PaymentScheduleDetail pd : pds) {
                        //获取投资人的账户
                        Account account = updates.get(pd.getToLogininfoId());
                        //3.3累加投资人待收本金和待收利息
                        //待收利息 = 原待收利息 + 回款对象的利息
                        account.setUnReceiveInterest(account.getUnReceiveInterest().add(pd.getInterest()));
                        //待收本金 = 原待收本金 + 回款对象的本金
                        account.setUnReceivePrincipal(account.getUnReceivePrincipal().add(pd.getPrincipal()));
                    }

                }
                //统一更新投资人的所有的账户对象
                for (Account account :updates.values()){
                    accountService.update(account);
                }
                //更新借款人信息
                userinfoService.update(userinfoCreateUser);
                //更新借款人账户对象
                accountService.update(accountCreateUser);
            }else {
                //审核拒绝
                refuseAudit(bidRequest);
            }
           this.updateByPrimaryKey(bidRequest);
        }
    }
    //生成还款对象
    private List<PaymentSchedule> createPaymentSchedules(BidRequest bidRequest) {
        List<PaymentSchedule> pss = new ArrayList<PaymentSchedule>();
        BigDecimal totalInterest = Consts.ZERO;
        BigDecimal totalPrincipal = Consts.ZERO;
        //针对还款月数，生成还款对象
        for (int i = 0;i< bidRequest.getMonthes2Return();i++){
            //每一个月为还款期,即生成还款对象
            PaymentSchedule paymentSchedule = new PaymentSchedule();
            paymentSchedule.setBidRequestId(bidRequest.getId());
            paymentSchedule.setBidRequestTitle(bidRequest.getTitle());
            paymentSchedule.setBidRequestType(bidRequest.getBidRequestType());
            paymentSchedule.setBorrowUser(bidRequest.getCreateUser());
            paymentSchedule.setDeadLine(DateUtils.addMonths(new Date(),i+1));
            paymentSchedule.setMonthIndex(i+1);
            paymentSchedule.setState(Consts.PAYMENT_STATE_NORMAL);//正常待还
            paymentSchedule.setReturnType(bidRequest.getReturnType());
            //如果不是最后一期
            if(i < bidRequest.getMonthes2Return() - 1) {
                //计算每期还款利息
                paymentSchedule.setInterest(CalculatetUtil.calMonthlyInterest(bidRequest.getReturnType(),
                        bidRequest.getBidRequestAmount(),
                        bidRequest.getCurrentRate(),
                        i+1,
                        bidRequest.getMonthes2Return()));
                //计算每期还款本息和
                paymentSchedule.setTotalAmount(CalculatetUtil.calMonthToReturnMoney(bidRequest.getReturnType(),
                        bidRequest.getBidRequestAmount(),
                        bidRequest.getCurrentRate(),
                        i+1,
                        bidRequest.getMonthes2Return()));
                //计算每期还款本金
                paymentSchedule.setPrincipal(paymentSchedule.getTotalAmount().subtract(paymentSchedule.getInterest()));
                //累积已还总利息和总本金
                totalInterest = totalInterest.add(paymentSchedule.getInterest());
                totalPrincipal = totalPrincipal.add(paymentSchedule.getPrincipal());

            }else {
                //如果是最后一期
                //用总利息-目前累计的已还利息=最后一期应还利息
                paymentSchedule.setInterest(bidRequest.getTotalRewardAmount().subtract(totalInterest));
                //总待还本金-累积已还本金=最后一期待还本金
                paymentSchedule.setPrincipal(bidRequest.getBidRequestAmount().subtract(totalPrincipal));
                //最后一期待还利息+最后一期待还本金=最后一期待还本息和
                paymentSchedule.setTotalAmount(paymentSchedule.getInterest().add(paymentSchedule.getPrincipal()));
            }
            //每一期保存一个还款对象
            paymentScheduleMapper.insert(paymentSchedule);
            //创建还款对象对应的还款明细(回款对象)
            createPaymentSchedulesDetail(bidRequest,paymentSchedule);

            pss.add(paymentSchedule);
        }
        return pss;
    }

    //生成还款对象明细(回款对象)
    private void createPaymentSchedulesDetail(BidRequest bidRequest, PaymentSchedule paymentSchedule) {
        BigDecimal totalInterest = Consts.ZERO;
        BigDecimal totalPrincipal = Consts.ZERO;
         //按照投资次数区分投标对象,相同的投资人投两次标,那么就算是两个不同的投标对象
         for (int i = 0;i<bidRequest.getBidCount();i++){
             Bid bid = bidRequest.getBids().get(i);
             //每一个投标对象每个月(每个还款计划)将会生成一个还款明细
             PaymentScheduleDetail paymentScheduleDetail = new PaymentScheduleDetail();
             paymentScheduleDetail.setBidAmount(bid.getAvailableAmount());
             paymentScheduleDetail.setBidId(bid.getId());
             paymentScheduleDetail.setBidRequestId(bid.getBidRequestId());
             paymentScheduleDetail.setDeadline(paymentSchedule.getDeadLine());
             paymentScheduleDetail.setFromLogininfo(bidRequest.getCreateUser());
             paymentScheduleDetail.setMonthIndex(paymentSchedule.getMonthIndex());
             paymentScheduleDetail.setPaymentScheduleId(paymentSchedule.getId());
             paymentScheduleDetail.setReturnType(paymentSchedule.getReturnType());
             paymentScheduleDetail.setToLogininfoId(bid.getBidUser().getId());
            //如果不是最后一标
             if(i < bidRequest.getBidCount() - 1){
                  //当前标的比例 = 当前标的投标金额/总借款金额
                  BigDecimal rate = bid.getAvailableAmount().divide(bidRequest.getBidRequestAmount(),Consts.CALCULATE_SCALE, RoundingMode.HALF_UP);
                  //当前标的对应的回款对象的本金 = 还款对象本金x比例
                  paymentScheduleDetail.setPrincipal(paymentSchedule.getPrincipal().multiply(rate).setScale(Consts.STORE_SCALE,RoundingMode.HALF_UP));
                  //当前标的对应的回款对象的利息 = 还款对象利息x比例
                  paymentScheduleDetail.setInterest(paymentSchedule.getInterest().multiply(rate).setScale(Consts.STORE_SCALE,RoundingMode.HALF_UP));
                  //当前标对应的回款对象的本息和 = 当前标的对应的回款对象的本金 + 当前标的对应的回款对象的利息
                  paymentScheduleDetail.setTotalAmount(paymentScheduleDetail.getPrincipal().add(paymentScheduleDetail.getInterest()));
                  //累加每一个标的对应的回款本金和利息
                 totalPrincipal = totalPrincipal.add(paymentScheduleDetail.getPrincipal());
                 totalInterest = totalInterest.add(paymentScheduleDetail.getInterest());
             }else {
                 //如果是最后一标
                 //本标回款对象本金 = 还款对象总本金 - 累积回款本金
                 paymentScheduleDetail.setPrincipal(paymentSchedule.getPrincipal().subtract(totalPrincipal));
                 //本标回款对象利息 = 还款对象总利息 - 累积回款利息
                 paymentScheduleDetail.setInterest(paymentSchedule.getInterest().subtract(totalInterest));
                 //本标回款对象本息和 = 本标回款对象本金 + 本标回款对象利息
                 paymentScheduleDetail.setTotalAmount(paymentScheduleDetail.getPrincipal().add(paymentScheduleDetail.getInterest()));
             }
             //保存每一期的回款对象
             paymentScheduleDetailMapper.insert(paymentScheduleDetail);
             //将每一期回款对象关联到还款对象中
             paymentSchedule.getPaymentScheduleDetails().add(paymentScheduleDetail);
         }
    }



    public PageResult query(BidRequestQueryObject qo) {
        int count = bidRequestMapper.queryForCount(qo);
        if(count != 0){
            List<BidRequest> data = bidRequestMapper.queryForData(qo);
            return new PageResult(qo.getCurrentPage(),qo.getPageSize(),count,data);
        }
        return PageResult.getEmpty(qo.getPageSize());
    }


    }


