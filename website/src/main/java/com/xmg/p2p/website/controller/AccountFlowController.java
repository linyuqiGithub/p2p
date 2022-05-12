package com.xmg.p2p.website.controller;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.bussiness.query.AccountFlowQueryObject;
import com.xmg.p2p.bussiness.service.IAccountFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccountFlowController {
    @Autowired
    private IAccountFlowService accountFlowService;

    @RequestMapping("/accountFlowList")
    private String accountFlowList(Model model, AccountFlowQueryObject qo){
        qo.setAccountId(UserContext.getCurrent().getId());
        PageResult pageResult = accountFlowService.QueryAccountFlow(qo);
        model.addAttribute("result",pageResult);
        return "accountFlow_list";
    }

}
