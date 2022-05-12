package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.mapper.AccountMapper;
import com.xmg.p2p.base.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private AccountMapper accountMapper;
    public int update(Account account) {
       //row为影响行数，用于判断乐观锁是否生效
       int row = accountMapper.updateByPrimaryKey(account);
       if(row != 0){
           return 1;
       }else {
           //影响行数为0，乐观锁生效，抛出异常，让事务回滚
           throw new RuntimeException("乐观锁失败"+account.getId());
       }
    }

    public void save(Account account) {
        accountMapper.insert(account);
    }

    public Account get(Long id) {
        Account account = accountMapper.selectByPrimaryKey(id);
        //如果没有通过数据校验，数据库数据可能被篡改，抛出异常，阻止业务继续进行
        if(!account.checkVerifyCode()){
            throw new RuntimeException("数据库校验失败，请检查数据库是否被篡改，异常账户信息"+id);
        }
        return account;
    }

    @Override
    public List<Long> getIds() {
        List<Long> ids = accountMapper.getIds();
        return ids;
    }


}
