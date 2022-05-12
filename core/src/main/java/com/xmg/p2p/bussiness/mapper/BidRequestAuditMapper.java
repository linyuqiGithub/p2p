package com.xmg.p2p.bussiness.mapper;

import com.xmg.p2p.bussiness.domain.BidRequestAudit;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BidRequestAuditMapper {

    int insert(BidRequestAudit record);

    BidRequestAudit selectByPrimaryKey(Long id);

    List<BidRequestAudit> selectAll();

    List<BidRequestAudit> selectByBidRequestId(Long id);




    /* int queryForCount(BidRequestAuditQueryObject qo);*/

    //List<BidRequestAudit> queryForData(BidRequestAuditQueryObject qo);
}