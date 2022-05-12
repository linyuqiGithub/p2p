package com.xmg.p2p.bussiness.service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.bussiness.event.RechargeOffilineSuccessEvent;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.bussiness.domain.RechargeOffline;
import com.xmg.p2p.bussiness.mapper.RechargeOfflineMapper;
import com.xmg.p2p.bussiness.query.RechargeOfflineQueryObject;
import com.xmg.p2p.bussiness.service.IAccountFlowService;
import com.xmg.p2p.bussiness.service.IRechargeOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class RechargeOfflineServiceImpl implements IRechargeOfflineService {
    @Autowired
    private RechargeOfflineMapper rechargeOfflineMapper;
    @Autowired
    private IAccountFlowService accountFlowService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private ApplicationContext ctx;

    public void apply(RechargeOffline rechargeOffline) {
        //前台传过来的rechargeOffline参数只能作为参数的封装，不能直接保存
        RechargeOffline ro = new RechargeOffline();
        ro.setAmount(rechargeOffline.getAmount());
        ro.setApplier(UserContext.getCurrent());
        ro.setApplyTime(new Date());
        ro.setBankinfo(rechargeOffline.getBankinfo());
        ro.setTradeTime(rechargeOffline.getTradeTime());
        ro.setTradeCode(rechargeOffline.getTradeCode());
        ro.setNote(rechargeOffline.getNote());
        ro.setState(RechargeOffline.NORMAL_AUDI);
        rechargeOfflineMapper.insert(ro);
    }

    public PageResult query(RechargeOfflineQueryObject qo) {
        //DataSourceContext.setSlaveDS();
        int count = rechargeOfflineMapper.queryForCount(qo);
        if(count > 0){
            List<RechargeOffline> data = rechargeOfflineMapper.queryForData(qo);
            return new PageResult(qo.getCurrentPage(),qo.getPageSize(),count,data);
        }
        return PageResult.getEmpty(qo.getPageSize());
    }

    public void audit(Long id, String remark, int state) {
        RechargeOffline rechargeOffline = rechargeOfflineMapper.selectByPrimaryKey(id);
        //线下充值对象不为空且该对象还未经过审核
        if(rechargeOffline != null && rechargeOffline.getState() == RechargeOffline.NORMAL_AUDI){
             rechargeOffline.setAuditor(UserContext.getCurrent());
             rechargeOffline.setAuditTime(new Date());
             rechargeOffline.setRemark(remark);
             rechargeOffline.setState(state);
             //如果审核通过
             if(state == RechargeOffline.PASS_AUDI){
                 Account account = accountService.get(rechargeOffline.getApplier().getId());
                 //对用户的账户进行充值
                 account.setUsableAmount(account.getUsableAmount().add(rechargeOffline.getAmount()));
                 //添加充值流水
                 accountFlowService.rechargeAccountFlow(rechargeOffline,account);
                 accountService.update(account);
                 //发布一个充值成功的消息
                ctx.publishEvent(new RechargeOffilineSuccessEvent(this,rechargeOffline));
             }
             rechargeOfflineMapper.updateByPrimaryKey(rechargeOffline);
        }
    }
}
