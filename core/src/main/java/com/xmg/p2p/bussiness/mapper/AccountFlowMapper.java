package com.xmg.p2p.bussiness.mapper;

import com.xmg.p2p.bussiness.domain.AccountFlow;
import com.xmg.p2p.bussiness.query.AccountFlowQueryObject;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AccountFlowMapper {

    int insert(AccountFlow record);

    AccountFlow selectByPrimaryKey(Long id);

    List<AccountFlow> selectAll();

    int updateByPrimaryKey(AccountFlow record);

    int queryForCount(AccountFlowQueryObject qo);

    List<AccountFlow> queryAccountFlow(AccountFlowQueryObject qo);

}