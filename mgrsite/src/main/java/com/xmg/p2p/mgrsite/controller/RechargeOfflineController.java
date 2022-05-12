package com.xmg.p2p.mgrsite.controller;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.bussiness.domain.PlatFormBank;
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
 * 后台线下充值审核控制器
 */

@Controller
public class RechargeOfflineController {

    @Autowired
    private IRechargeOfflineService rechargeOfflineService;
    @Autowired
    private IPlatFormBankService platFormBankService;

    /**
     * 后台线下充值审核列表
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("rechargeOffline")
    public String rechargeOffline(@ModelAttribute("qo")RechargeOfflineQueryObject qo, Model model){
        PageResult result = rechargeOfflineService.query(qo);
        List<PlatFormBank> banks = platFormBankService.listAll();
        model.addAttribute("result",result);
        model.addAttribute("banks",banks);
        return "rechargeoffline/list";
    }

    /**
     * 后台线下充值审核操作
     * @param id
     * @param remark
     * @param state
     * @return
     */
    @RequestMapping("rechargeOffline_audit")
    @ResponseBody
    public AjaxResult audit(Long id,String remark,int state){
        rechargeOfflineService.audit(id,remark,state);
        return new AjaxResult();
    }
}
