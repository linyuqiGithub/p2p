package com.xmg.p2p.bussiness.service;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.bussiness.query.MoneyWithdrawQueryObject;

import java.math.BigDecimal;

/**
 * 提现相关
 * 
 * @author Administrator
 * 
 */
public interface IMoneyWithdrawService {

	/**
	 * 提现申请
	 * 
	 * @param moneyAmount
	 */
	void apply(BigDecimal moneyAmount);

	PageResult query(MoneyWithdrawQueryObject qo);

	/**
	 * 提现审核
	 * 
	 * @param id
	 * @param remark
	 * @param state
	 */
	void audit(Long id, String remark, int state);

}
