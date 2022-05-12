package com.xmg.p2p.bussiness.service;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.bussiness.domain.PlatFormBank;
import com.xmg.p2p.bussiness.query.PlatFormBankQueryObject;

import java.util.List;


public interface IPlatFormBankService {
    PageResult query(PlatFormBankQueryObject qo);

    void saveOrUpdate(PlatFormBank platFormBank);

    List<PlatFormBank> listAll();
}
