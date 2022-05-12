package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.Account;

import java.util.List;


public interface IAccountService {
    /**
     * 所有的账户的修改都必须调用这个方法，这里面有乐观锁的判断
     * @param account
     * @return
     */
    int update(Account account);

    void save(Account account);

    Account get(Long id);

    List<Long> getIds();
}
