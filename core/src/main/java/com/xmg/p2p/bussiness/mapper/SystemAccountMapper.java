package com.xmg.p2p.bussiness.mapper;

import com.xmg.p2p.bussiness.domain.SystemAccount;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemAccountMapper {


    int insert(SystemAccount record);

    int updateByPrimaryKey(SystemAccount record);

    SystemAccount selectCurrent();
}