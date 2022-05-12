package com.xmg.p2p.bussiness.mapper;

import com.xmg.p2p.bussiness.domain.ExpAccountFlow;
import java.util.List;

public interface ExpAccountFlowMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ExpAccountFlow record);

    ExpAccountFlow selectByPrimaryKey(Long id);

    List<ExpAccountFlow> selectAll();

    int updateByPrimaryKey(ExpAccountFlow record);
}