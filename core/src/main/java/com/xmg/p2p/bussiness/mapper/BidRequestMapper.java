package com.xmg.p2p.bussiness.mapper;

import com.xmg.p2p.bussiness.domain.BidRequest;
import com.xmg.p2p.bussiness.query.BidRequestQueryObject;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BidRequestMapper {

    int insert(BidRequest record);

    BidRequest selectByPrimaryKey(Long id);

    int updateByPrimaryKey(BidRequest record);

    int queryForCount(BidRequestQueryObject qo);

    List<BidRequest> queryForData(BidRequestQueryObject qo);
}