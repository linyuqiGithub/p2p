package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.bussiness.event.RealAuthSuccessEvent;
import com.xmg.p2p.bussiness.event.RechargeOffilineSuccessEvent;
import com.xmg.p2p.base.service.ISmsService;
import com.xmg.p2p.bussiness.domain.RechargeOffline;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements ISmsService, ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if(applicationEvent instanceof RealAuthSuccessEvent) {
            RealAuthSuccessEvent realAuthSuccessEvent = (RealAuthSuccessEvent)applicationEvent;
            this.sendSms(realAuthSuccessEvent.getRa());
        }else if(applicationEvent instanceof RechargeOffilineSuccessEvent){
            RechargeOffilineSuccessEvent rechargeOffilineSuccessEvent = (RechargeOffilineSuccessEvent) applicationEvent;
            this.sendSms(rechargeOffilineSuccessEvent.getRo());
        }
    }
    private void sendSms(RealAuth ra){
        System.out.println("用户"+ra.getRealName()+"实名认证成功,发送短信");
    }
    private void sendSms(RechargeOffline ro){
        System.out.println("用户"+ro.getApplier().getUsername()+"充值成功，发送短信");
    }
}
