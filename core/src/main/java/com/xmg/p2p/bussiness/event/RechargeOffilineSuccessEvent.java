package com.xmg.p2p.bussiness.event;

import com.xmg.p2p.bussiness.domain.RechargeOffline;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 线下充值成功的消息
 */
@Getter
public class RechargeOffilineSuccessEvent extends ApplicationEvent {
    private RechargeOffline ro;
    public RechargeOffilineSuccessEvent(Object source,RechargeOffline ro) {
        super(source);
        this.ro = ro;
    }
}
