package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Account record);

    Account selectByPrimaryKey(Long id);

    List<Account> selectAll();

    int updateByPrimaryKey(Account record);

    List<Long> getIds();
}