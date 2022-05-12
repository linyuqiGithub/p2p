package com.xmg.p2p.bussiness.mapper;

import com.xmg.p2p.bussiness.domain.UserBankinfo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBankinfoMapper {

	int insert(UserBankinfo record);

	UserBankinfo selectByUser(Long userid);

}