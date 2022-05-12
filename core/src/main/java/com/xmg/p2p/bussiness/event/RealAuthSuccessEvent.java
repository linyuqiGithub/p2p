package com.xmg.p2p.bussiness.event;

import com.xmg.p2p.base.domain.RealAuth;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 实名认证审核通过的消息
 */
@Getter
public class RealAuthSuccessEvent extends ApplicationEvent {
    /**
     * 事件关联的对象
     */
    private RealAuth ra;
    public RealAuthSuccessEvent(Object source,RealAuth ra) {
        super(source);
        this.ra = ra;
    }
}
