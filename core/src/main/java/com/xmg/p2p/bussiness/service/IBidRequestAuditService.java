package com.xmg.p2p.bussiness.service;

import com.xmg.p2p.bussiness.domain.BidRequestAudit;

import java.util.List;


public interface IBidRequestAuditService {
    List<BidRequestAudit> listAll();
    List<BidRequestAudit> listByBidRequestId(Long id);
    // PageResult query(BidRequestAuditQueryObject qo);
}
