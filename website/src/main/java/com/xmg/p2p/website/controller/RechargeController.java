package com.xmg.p2p.website.controller;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.bussiness.domain.PlatFormBank;
import com.xmg.p2p.bussiness.domain.RechargeOffline;
import com.xmg.p2p.bussiness.query.RechargeOfflineQueryObject;
import com.xmg.p2p.bussiness.service.IPlatFormBankService;
import com.xmg.p2p.bussiness.service.IRechargeOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *线下充值控制器
 */
@Controller
public class RechargeController {

    @Autowired
    private IPlatFormBankService platFormBankService;
    @Autowired
    private IRechargeOfflineService rechargeOfflineService;

    /**
     * 充值首页
     * @param model
     * @return
     */
    @RequestMapping("recharge")
    public String recharge(Model model){
        //列出平台的所有账户
        List<PlatFormBank> banks =  platFormBankService.listAll();
        model.addAttribute("banks",banks);
        return "recharge";
    }

    /**
     * 提交线下充值申请
     * @param rechargeOffline
     * @return
     */
    @RequestMapping("recharge_save")
    @ResponseBody
    public AjaxResult rechargeSave(RechargeOffline rechargeOffline){
         rechargeOfflineService.apply(rechargeOffline);
         return new AjaxResult();
    }

    /**
     * 充值记录
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("recharge_list")
    public String rechargeList(@ModelAttribute("qo")RechargeOfflineQueryObject qo,Model model){
        qo.setUserId(UserContext.getCurrent().getId());
        PageResult result = rechargeOfflineService.query(qo);
        model.addAttribute("result",result);
        return "recharge_list";
    }
}
