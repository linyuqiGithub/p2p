package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.bussiness.event.RealAuthSuccessEvent;
import com.xmg.p2p.bussiness.event.RechargeOffilineSuccessEvent;
import com.xmg.p2p.base.service.IEmailService;
import com.xmg.p2p.bussiness.domain.RechargeOffline;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class SemailServiceImp implements IEmailService, ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if(applicationEvent instanceof RealAuthSuccessEvent) {
            RealAuthSuccessEvent realAuthSuccessEvent = (RealAuthSuccessEvent)applicationEvent;
            this.Semail(realAuthSuccessEvent.getRa());
        }else if(applicationEvent instanceof RechargeOffilineSuccessEvent){
            RechargeOffilineSuccessEvent rechargeOffilineSuccessEvent = (RechargeOffilineSuccessEvent) applicationEvent;
            this.Semail(rechargeOffilineSuccessEvent.getRo());
        }
    }

    private void Semail(RealAuth ra){
        System.out.println("用户"+ra.getApplier().getUsername()+"实名认证成功，发送邮件");
    }
    private void Semail(RechargeOffline ro){
        System.out.println("用户"+ro.getApplier().getUsername()+"充值成功，发送邮件");
    }
}
