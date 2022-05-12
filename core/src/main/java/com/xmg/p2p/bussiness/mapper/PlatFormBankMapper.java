package com.xmg.p2p.bussiness.mapper;

import com.xmg.p2p.bussiness.domain.PlatFormBank;
import com.xmg.p2p.bussiness.query.PlatFormBankQueryObject;

import java.util.List;

public interface PlatFormBankMapper {

    int insert(PlatFormBank record);

    PlatFormBank selectByPrimaryKey(Long id);

    List<PlatFormBank> selectAll();

    int updateByPrimaryKey(PlatFormBank record);

    int queryForCount(PlatFormBankQueryObject qo);

    List<PlatFormBank> queryForData(PlatFormBankQueryObject qo);
}