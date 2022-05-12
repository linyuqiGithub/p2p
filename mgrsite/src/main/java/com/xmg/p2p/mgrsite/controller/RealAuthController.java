package com.xmg.p2p.mgrsite.controller;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.RealAuthQueryObject;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台实名认证审核控制器
 */
@Controller
public class RealAuthController {

    @Autowired
    private IRealAuthService realAuthService;

    /**
     * 实名认证审核页面
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("realAuth")
    public String realAuth(@ModelAttribute("qo")RealAuthQueryObject qo, Model model){
        PageResult result = realAuthService.query(qo);
        model.addAttribute("result",result);
        return "realAuth/list";
    }

    /**
     * 审核操作
     * @param id
     * @param remark
     * @param state
     * @return
     */
    @RequestMapping("realAuth_audit")
    @ResponseBody
    public AjaxResult realAuth_audit(Long id,String remark,int state){
        realAuthService.audit(id,remark,state);
        return new AjaxResult();
    }

}
