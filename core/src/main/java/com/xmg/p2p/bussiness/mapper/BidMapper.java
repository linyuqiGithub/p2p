package com.xmg.p2p.bussiness.mapper;

import com.xmg.p2p.bussiness.domain.Bid;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BidMapper {

    int insert(Bid record);

    Bid selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Bid record);

    /**
     * 批量修改借款对应的投标对象的状态
     * @param bidRequestId
     * @param bidRequestState
     */
    void updateState(@Param("bidRequestId") Long bidRequestId,@Param("bidRequestState") int bidRequestState);
}