package com.xmg.p2p.bussiness.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.bussiness.domain.UserBankinfo;
import com.xmg.p2p.bussiness.mapper.UserBankinfoMapper;
import com.xmg.p2p.bussiness.service.IUserBankinfoService;

@Service
public class UserBankinfoServiceImpl implements IUserBankinfoService {

	@Autowired
	private UserBankinfoMapper userBankinfoMapper;

	@Autowired
	private IUserinfoService userinfoService;

	@Override
	public UserBankinfo getByUser(Long id) {
		return this.userBankinfoMapper.selectByUser(id);
	}

	@Override
	public void bind(UserBankinfo bankInfo) {
		// 判断当前用户有没有绑定银行卡且已经实名认证;
		Userinfo current = this.userinfoService.getCurrent();
		if (!current.getIsBindBank() && current.getIsRealAuth()) {
			// 创建一个UserBankinfo,设置相关属性;
			UserBankinfo b = new UserBankinfo();
			b.setAccountName(current.getRealName());
			b.setAccountNumber(bankInfo.getAccountNumber());
			b.setBankForkName(bankInfo.getBankForkName());
			b.setBankName(bankInfo.getBankName());
			b.setLogininfo(UserContext.getCurrent());
			this.userBankinfoMapper.insert(b);
			// 修改用户状态码为已绑定银行卡
			current.setBitState(BitStatesUtils.addState(current.getBitState(),BitStatesUtils.OP_BIND_BANK));
			this.userinfoService.update(current);
		}
	}
	
	

}
