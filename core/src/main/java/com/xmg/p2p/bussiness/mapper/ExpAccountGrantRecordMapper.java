package com.xmg.p2p.bussiness.mapper;

import com.xmg.p2p.bussiness.domain.ExpAccountGrantRecord;
import java.util.List;

public interface ExpAccountGrantRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ExpAccountGrantRecord record);

    ExpAccountGrantRecord selectByPrimaryKey(Long id);

    List<ExpAccountGrantRecord> selectAll();

    int updateByPrimaryKey(ExpAccountGrantRecord record);
}