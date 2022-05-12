package com.xmg.p2p.bussiness.service;

import com.xmg.p2p.bussiness.domain.ExpAccount;


public interface IExpAccountService {
    ExpAccount getExp(Long id);

    void createExp(Long id);

    void update(ExpAccount expAccount);
}
