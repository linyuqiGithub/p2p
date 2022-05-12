package com.xmg.p2p.bussiness.mapper;

import com.xmg.p2p.bussiness.domain.MoneyWithdraw;
import com.xmg.p2p.bussiness.query.MoneyWithdrawQueryObject;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MoneyWithdrawMapper {

	int insert(MoneyWithdraw record);

	MoneyWithdraw selectByPrimaryKey(Long id);

	int updateByPrimaryKey(MoneyWithdraw record);

	int queryForCount(MoneyWithdrawQueryObject qo);

	List<MoneyWithdraw> query(MoneyWithdrawQueryObject qo);
}