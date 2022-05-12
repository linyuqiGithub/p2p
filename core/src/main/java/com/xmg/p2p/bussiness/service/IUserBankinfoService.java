package com.xmg.p2p.bussiness.service;

import com.xmg.p2p.bussiness.domain.UserBankinfo;

/**
 * 用户绑定银行卡相关服务
 * 
 * @author Administrator
 * 
 */
public interface IUserBankinfoService {

	/**
	 * 得到当前用户绑定的银行卡信息
	 * 
	 * @param id
	 * @return
	 */
	UserBankinfo getByUser(Long id);

	/**
	 * 绑定银行卡
	 * 
	 * @param bankInfo
	 */
	void bind(UserBankinfo bankInfo);

}
