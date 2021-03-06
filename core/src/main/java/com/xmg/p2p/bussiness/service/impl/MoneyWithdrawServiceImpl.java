package com.xmg.p2p.bussiness.service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.Consts;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.bussiness.domain.MoneyWithdraw;
import com.xmg.p2p.bussiness.domain.UserBankinfo;
import com.xmg.p2p.bussiness.mapper.MoneyWithdrawMapper;
import com.xmg.p2p.bussiness.query.MoneyWithdrawQueryObject;
import com.xmg.p2p.bussiness.service.IAccountFlowService;
import com.xmg.p2p.bussiness.service.IMoneyWithdrawService;
import com.xmg.p2p.bussiness.service.ISystemAccountService;
import com.xmg.p2p.bussiness.service.IUserBankinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class MoneyWithdrawServiceImpl implements IMoneyWithdrawService {

	@Autowired
	private MoneyWithdrawMapper moneyWithdrawMapper;

	@Autowired
	private IUserinfoService userinfoService;

	@Autowired
	private IAccountService accountService;

	@Autowired
	private IAccountFlowService accountFlowService;

	@Autowired
	private IUserBankinfoService userBankinfoService;

	@Autowired
	private ISystemAccountService systemAccountService;

	@Override
	public void apply(BigDecimal moneyAmount) {
		Userinfo current = this.userinfoService.getCurrent();
		Account account = this.accountService.get(current.getId());
		if (current.getIsBindBank() && !current.getHasWithdrawProcess() // 判断当前用户是否有一个提现申请,已经绑定银行卡
				&& moneyAmount.compareTo(account.getUsableAmount()) <= 0 //判断提现金额<=用户可用余额
				&& moneyAmount.compareTo(Consts.MIN_WITHDRAW_AMOUNT) >= 0) //提现金额>=系统最小提现金额
		{
			// 得到用户绑定的银行卡信息
			UserBankinfo ub = this.userBankinfoService.getByUser(current.getId());

			// 创建一个提现申请对象,设置相关属性;
			MoneyWithdraw m = new MoneyWithdraw();
			m.setAccountName(ub.getAccountName());
			m.setAccountNumber(ub.getAccountNumber());
			m.setAmount(moneyAmount);
			m.setApplier(UserContext.getCurrent());
			m.setApplyTime(new Date());
			m.setBankForkName(ub.getBankForkName());
			m.setBankName(ub.getBankName());
			m.setCharge(Consts.MONEY_WITHDRAW_CHARGEFEE);
			m.setState(MoneyWithdraw.STATE_NORMAL);

			this.moneyWithdrawMapper.insert(m);
			// 对于账户:冻结金额,增加提现申请冻结流水;
			account.setUsableAmount(account.getUsableAmount().subtract(
					moneyAmount));
			account.setFreezedAmount(account.getFreezedAmount()
					.add(moneyAmount));
			this.accountFlowService.moneyWithDrawApplyFlow(m, account);
			this.accountService.update(account);

			// 用户添加状态码
			current.setBitState(BitStatesUtils.addState(current.getBitState(),BitStatesUtils.OP_HAS_MONEYWITHDRAW_PROCESS));
			this.userinfoService.update(current);
		}
	}

	@Override
	public PageResult query(MoneyWithdrawQueryObject qo) {
		int count = this.moneyWithdrawMapper.queryForCount(qo);
		if (count > 0) {
			List<MoneyWithdraw> list = this.moneyWithdrawMapper.query(qo);
			return new PageResult(qo.getCurrentPage(), qo.getPageSize(), count, list);
		}
		return PageResult.getEmpty(qo.getPageSize());

	}

	@Override
	public void audit(Long id, String remark, int state) {
		// 得到提现申请单
		MoneyWithdraw m = this.moneyWithdrawMapper.selectByPrimaryKey(id);
		// 1,判断:提现单状态
		if (m != null && m.getState() == MoneyWithdraw.STATE_NORMAL) {
			// 2,设置相关参数
			m.setAuditor(UserContext.getCurrent());
			m.setAuditTime(new Date());
			m.setRemark(remark);
			m.setState(state);

			Account account = this.accountService.get(m.getApplier().getId());
			// 3,如果审核通过(冻结金额分为两部分扣除，一部分是用户提现的金额，一部分是平台收取的手续费，并分别添加相应的流水)
			if (state == MoneyWithdraw.STATE_AUDIT) {
				// 1,冻结金额减少(减少手续费),生成提现支付手续费流水;
				account.setFreezedAmount(account.getFreezedAmount().subtract(m.getCharge()));
				this.accountFlowService.withDrawChargeFeeFlow(m, account);
				// 2,系统账户增加可用余额,增加收取提现手续费流水;
				this.systemAccountService.chargeWithdrawFee(m);

				// 3,冻结金额减少(减少提现金额);生成提现成功流水;
				BigDecimal realWithdrawFee = m.getAmount().subtract(m.getCharge());
				account.setFreezedAmount(account.getFreezedAmount().subtract(realWithdrawFee));
				this.accountFlowService.withDrawSuccess(realWithdrawFee, account);

			} else {
				// 4,如果审核拒绝
				// 1,取消冻结金额,可用余额增加,增加去掉冻结流水
				account.setFreezedAmount(account.getFreezedAmount().subtract(m.getAmount()));
				account.setUsableAmount(account.getUsableAmount().add(m.getAmount()));
				this.accountFlowService.withDrawFailedFlow(m, account);
			}
			this.accountService.update(account);
			this.moneyWithdrawMapper.updateByPrimaryKey(m);
			// 5,取消用户提现状态码
			Userinfo userinfo = this.userinfoService.get(m.getApplier().getId());
			userinfo.setBitState(BitStatesUtils.removeState(userinfo.getBitState(),BitStatesUtils.OP_HAS_MONEYWITHDRAW_PROCESS));
			this.userinfoService.update(userinfo);
		}
	}
}
