package com.xmg.p2p.bussiness.mapper;

import com.xmg.p2p.bussiness.domain.ExpAccount;
import java.util.List;

public interface ExpAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ExpAccount record);

    ExpAccount selectByPrimaryKey(Long id);

    List<ExpAccount> selectAll();

    int updateByPrimaryKey(ExpAccount record);
}