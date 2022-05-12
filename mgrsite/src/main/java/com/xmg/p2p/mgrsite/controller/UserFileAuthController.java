package com.xmg.p2p.mgrsite.controller;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.UserFileQueryObject;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台风控材料审核控制器
 */
@Controller
public class UserFileAuthController {

    @Autowired
    private IUserFileService userFileService;

    /**
     * 风控材料分页
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("userFileAuth")
    public String userFileAuth(@ModelAttribute("qo")UserFileQueryObject qo, Model model){
          PageResult result = userFileService.query(qo);
          model.addAttribute("result",result);
        return "userFileAuth/list";
    }

    /**
     * 风控材料审核
     * @param id
     * @param state
     * @param score
     * @param remark
     * @return
     */
    @RequestMapping("userFile_audit")
    @ResponseBody
    public AjaxResult userFile_audit(Long id, int state, int score, String remark){
          userFileService.audit(id,state,score,remark);
          return new AjaxResult();
    }
}
