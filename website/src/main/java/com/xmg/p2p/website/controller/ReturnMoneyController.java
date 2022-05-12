package com.xmg.p2p.website.controller;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.bussiness.query.ReturnMoneyQueryObject;
import com.xmg.p2p.bussiness.service.IPaymentScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ReturnMoneyController {

    @Autowired
    private IPaymentScheduleService paymentScheduleService;
    @Autowired
    private IAccountService accountService;

    /**
     * 还款列表
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("borrowBidReturn_list")
    public String borrowBidReturnList(@ModelAttribute("qo")ReturnMoneyQueryObject qo, Model model){
       qo.setUserinfoId(UserContext.getCurrent().getId());
       PageResult result = paymentScheduleService.query(qo);
        Account account = accountService.get(UserContext.getCurrent().getId());
        model.addAttribute("result",result);
        model.addAttribute("account",account);
       return "returnmoney_list";
    }

    /**
     * 还款操作
     * @param id
     * @return
     */
    @RequestMapping("returnMoney")
    @ResponseBody
    public AjaxResult returnMoney(Long id){
        paymentScheduleService.returnMoney(id);
        return new AjaxResult();
    }
}
