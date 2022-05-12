package com.xmg.p2p.bussiness.service;

import com.xmg.p2p.bussiness.domain.BidRequest;
import com.xmg.p2p.bussiness.query.BidRequestQueryObject;
import com.xmg.p2p.base.query.PageResult;

import java.math.BigDecimal;

/**
 * 借款的相关服务
 */

public interface IBidRequestService {

    int updateByPrimaryKey(BidRequest record);

    PageResult query(BidRequestQueryObject qo);

    /**
     * 提交借款申请
     * @param vo
     */
    void apply(BidRequest vo);

    /**
     * 发标前审核
     * @param id
     * @param remark
     * @param state
     */
    void publishAudit(Long id, String remark, int state);

    BidRequest get(Long id);

    /**
     * 投标操作
     * @param bidRequestId
     * @param amount
     */
    void bid(Long bidRequestId, BigDecimal amount);

    /**
     * 满标一审审核操作
     * @param id
     * @param remark
     * @param state
     */
    void audit1(Long id, String remark, int state);

    /**
     * 满标二审审核操作
     * @param id
     * @param remark
     * @param state
     */
    void audit2(Long id, String remark, int state);
}
