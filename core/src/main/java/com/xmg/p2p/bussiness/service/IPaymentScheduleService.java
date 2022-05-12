package com.xmg.p2p.bussiness.service;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.bussiness.query.ReturnMoneyQueryObject;

/**
 * 还款相关的服务
 */
public interface IPaymentScheduleService {
    /**
     * 还款列表分页
     * @param qo
     * @return
     */
    PageResult query(ReturnMoneyQueryObject qo);

    /**
     * 还款操作
     * @param id
     */
    void returnMoney(Long id);
}
