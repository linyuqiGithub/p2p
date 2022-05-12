package com.xmg.p2p.website.controller;

import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.bussiness.service.IMoneyWithdrawService;
import com.xmg.p2p.bussiness.service.IUserBankinfoService;
import com.xmg.p2p.website.util.RequiredLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * 前台提现控制器
 * @author Administrator
 * 
 */
@Controller
public class MoneyWithdrawController {

	@Autowired
	private IMoneyWithdrawService moneyWithdrawService;
	
	@Autowired
	private IUserinfoService userinfoService;

	@Autowired
	private IUserBankinfoService userBankinfoService;

	@Autowired
	private IAccountService accountService;

	/**
	 * 导向到提现申请界面
	 */
	@RequiredLogin
	@RequestMapping("moneyWithdraw")
	public String moenyWithdraw(Model model) {
		Userinfo current = this.userinfoService.getCurrent();
		model.addAttribute("haveProcessing", current.getHasWithdrawProcess());
		model.addAttribute("bankInfo",
				this.userBankinfoService.getByUser(current.getId()));
		model.addAttribute("account", this.accountService.get(current.getId()));
		return "moneyWithdraw_apply";
	}
	
	/**
	 * 提现申请
	 */
	@RequiredLogin
	@RequestMapping("moneyWithdraw_apply")
	@ResponseBody
	public AjaxResult apply(BigDecimal moneyAmount){
		this.moneyWithdrawService.apply(moneyAmount);
		return new AjaxResult();
	}
	
}
