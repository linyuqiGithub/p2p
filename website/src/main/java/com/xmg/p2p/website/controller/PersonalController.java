package com.xmg.p2p.website.controller;

import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.bussiness.query.BidRequestQueryObject;
import com.xmg.p2p.bussiness.service.IBidRequestService;
import com.xmg.p2p.bussiness.service.IExpAccountService;
import com.xmg.p2p.website.util.RequiredLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 个人中心控制器
 */
@Controller
public class PersonalController {

    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IExpAccountService expAccountService;
    @Autowired
    private IBidRequestService bidRequestService;
    //登陆检查标签
    @RequiredLogin
    @RequestMapping("personal")
    public String personal(Model model){
        model.addAttribute("account",accountService.get(UserContext.getCurrent().getId()));
        model.addAttribute("userinfo",userinfoService.get(UserContext.getCurrent().getId()));
        model.addAttribute("expAccount",expAccountService.getExp(UserContext.getCurrent().getId()));
        return "personal";
    }
    @RequestMapping("borrow_list")
    public String borrowList(BidRequestQueryObject qo, Model model){
        qo.setCreateUserId(UserContext.getCurrent().getId());
        model.addAttribute("result",bidRequestService.query(qo));
        return "borrow_list";
    }
}
