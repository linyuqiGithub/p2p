package com.xmg.p2p.website.controller;

import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.bussiness.domain.UserBankinfo;
import com.xmg.p2p.bussiness.service.IUserBankinfoService;
import com.xmg.p2p.website.util.RequiredLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户绑定银行卡控制器
 * 
 * @author Administrator
 * 
 */
@Controller
public class UserBankinfoController {

	@Autowired
	private IUserBankinfoService userBankinfoService;

	@Autowired
	private IUserinfoService userinfoService;

	/**
	 * 导向到绑定银行卡界面
	 */
	@RequiredLogin
	@RequestMapping("bankInfo")
	public String bankInfo(Model model) {
		Userinfo current = userinfoService.getCurrent();
		if (!current.getIsBindBank()) {
			// 没有绑定
			model.addAttribute("userinfo", current);
			return "bankInfo";
		} else {
			model.addAttribute("bankInfo",
					userBankinfoService.getByUser(current.getId()));
			return "bankInfo_result";
		}
	}

	/**
	 * 执行绑定
	 */
	@RequiredLogin
	@RequestMapping("bankInfo_save")
	public String bankInfoSave(UserBankinfo bankInfo) {
		this.userBankinfoService.bind(bankInfo);
		return "redirect:/bankInfo.do";
	}
}
