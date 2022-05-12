package com.xmg.p2p.bussiness.mapper;

import com.xmg.p2p.bussiness.domain.RechargeOffline;
import com.xmg.p2p.bussiness.query.RechargeOfflineQueryObject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RechargeOfflineMapper {

    int insert(RechargeOffline record);

    RechargeOffline selectByPrimaryKey(Long id);

    List<RechargeOffline> selectAll();

    int updateByPrimaryKey(RechargeOffline record);

    int queryForCount(RechargeOfflineQueryObject qo);

    List<RechargeOffline> queryForData(RechargeOfflineQueryObject qo);
}