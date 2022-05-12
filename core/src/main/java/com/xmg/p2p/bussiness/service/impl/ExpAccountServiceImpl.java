package com.xmg.p2p.bussiness.service.impl;

import com.xmg.p2p.bussiness.domain.ExpAccount;
import com.xmg.p2p.bussiness.mapper.ExpAccountMapper;
import com.xmg.p2p.bussiness.service.IExpAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Service
public class ExpAccountServiceImpl implements IExpAccountService {
    @Autowired
    private ExpAccountMapper expAccountMapper;
    public ExpAccount getExp(Long id) {
        return expAccountMapper.selectByPrimaryKey(id);
    }

    public void createExp(Long id) {
        ExpAccount expAccount = new ExpAccount();
        expAccount.setId(id);
        expAccount.setUsableAmount(new BigDecimal("2000").setScale(2, RoundingMode.HALF_UP));
        expAccountMapper.insert(expAccount);
    }

    public void update(ExpAccount expAccount) {
       int row = expAccountMapper.updateByPrimaryKey(expAccount);
       if(row == 0){
           throw new RuntimeException("乐观锁失败");
       }
    }
}
