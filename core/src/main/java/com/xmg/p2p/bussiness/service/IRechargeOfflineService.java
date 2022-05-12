package com.xmg.p2p.bussiness.service;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.bussiness.domain.RechargeOffline;
import com.xmg.p2p.bussiness.query.RechargeOfflineQueryObject;

/**
 * 线下充值服务
 */
public interface IRechargeOfflineService {
    /**
     * 线下充值申请
     * @param rechargeOffline
     */
    void apply(RechargeOffline rechargeOffline);

    PageResult query(RechargeOfflineQueryObject qo);

    void audit(Long id, String remark, int state);
}
