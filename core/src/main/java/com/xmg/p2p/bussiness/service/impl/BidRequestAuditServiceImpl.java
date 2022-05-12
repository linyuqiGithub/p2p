package com.xmg.p2p.bussiness.service.impl;

import com.xmg.p2p.bussiness.mapper.BidRequestAuditMapper;
import com.xmg.p2p.bussiness.service.IBidRequestAuditService;
import com.xmg.p2p.bussiness.domain.BidRequestAudit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BidRequestAuditServiceImpl implements IBidRequestAuditService {

     @Autowired
    private BidRequestAuditMapper bidRequestAuditMapper;

    public List<BidRequestAudit> listAll() {
        return bidRequestAuditMapper.selectAll();
    }

    @Override
    public List<BidRequestAudit> listByBidRequestId(Long id) {
        return bidRequestAuditMapper.selectByBidRequestId(id);
    }


    /*
    public PageResult query(BidRequestAuditQueryObject qo) {
        int count = bidRequestAuditMapper.queryForCount(qo);
        if(count != 0){
            List<BidRequestAudit> data = bidRequestAuditMapper.queryForData(qo);
            return new PageResult(qo.getCurrentPage(),qo.getPageSize(),count,data);
        }
        return PageResult.getEmpty(qo.getPageSize());
    }*/
}
