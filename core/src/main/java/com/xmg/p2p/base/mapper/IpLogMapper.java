package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.IpLog;
import com.xmg.p2p.base.query.IpLogQueryObject;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IpLogMapper {


    int insert(IpLog record);

    Long queryForCount(IpLogQueryObject qo);

    List<IpLog> query(IpLogQueryObject qo);
}