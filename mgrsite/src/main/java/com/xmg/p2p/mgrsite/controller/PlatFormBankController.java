package com.xmg.p2p.mgrsite.controller;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.bussiness.domain.PlatFormBank;
import com.xmg.p2p.bussiness.query.PlatFormBankQueryObject;
import com.xmg.p2p.bussiness.service.IPlatFormBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 平台公开银行账号控制器
 */
@Controller
public class PlatFormBankController {

    @Autowired
    private IPlatFormBankService platFormBankService;

    /**
     *开户银行的列表
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("companyBank_list")
    public String companyBankList(PlatFormBankQueryObject qo,Model model){
        PageResult result = platFormBankService.query(qo);
        model.addAttribute("result",result);
        return "platformbankinfo/list";
    }

    /**
     * 开户银行的添加或编辑
     * @param platFormBank
     * @return
     */
    @RequestMapping("companyBank_saveOrUpdate")
    @ResponseBody
    public AjaxResult companyBankSaveOrUpdate(PlatFormBank platFormBank){
        platFormBankService.saveOrUpdate(platFormBank);
        return new AjaxResult();
    }

}
