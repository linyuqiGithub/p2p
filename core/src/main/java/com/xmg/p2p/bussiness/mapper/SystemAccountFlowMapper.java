package com.xmg.p2p.bussiness.mapper;

import com.xmg.p2p.bussiness.domain.SystemAccountFlow;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SystemAccountFlowMapper {

    int insert(SystemAccountFlow record);

    SystemAccountFlow selectByPrimaryKey(Long id);

    List<SystemAccountFlow> selectAll();

    int updateByPrimaryKey(SystemAccountFlow record);
}