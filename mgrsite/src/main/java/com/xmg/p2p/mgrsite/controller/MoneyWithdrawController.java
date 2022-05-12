package com.xmg.p2p.mgrsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.bussiness.query.MoneyWithdrawQueryObject;
import com.xmg.p2p.bussiness.service.IMoneyWithdrawService;

/**
 * 后台提现申请审核
 * @author Administrator
 * 
 */
@Controller
public class MoneyWithdrawController {

	@Autowired
	private IMoneyWithdrawService moneyWithdrawService;

	@RequestMapping("moneyWithdraw")
	public String moneyWithdrawList(@ModelAttribute("qo") MoneyWithdrawQueryObject qo, Model model) {
		model.addAttribute("pageResult", this.moneyWithdrawService.query(qo));
		return "moneywithdraw/list";
	}

	/**
	 * 提现审核
	 * @param id
	 * @param remark
	 * @param state
	 * @return
	 */
	@RequestMapping("moneyWithdraw_audit")
	@ResponseBody
	public AjaxResult audit(Long id, String remark, int state) {
		this.moneyWithdrawService.audit(id, remark, state);
		return new AjaxResult();
	}

}
