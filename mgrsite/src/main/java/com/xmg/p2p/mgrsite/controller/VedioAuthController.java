package com.xmg.p2p.mgrsite.controller;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.VideoQueryObject;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.service.IVideoService;
import com.xmg.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 视频认证控制器
 */
@Controller
public class VedioAuthController {

    @Autowired
    private IVideoService videoService;
    @Autowired
    private ILogininfoService logininfoService;

    /**
     * 后台视频审核分页
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("vedioAuth")
    public String vedioAuth(@ModelAttribute("qo") VideoQueryObject qo, Model model){
        PageResult result = videoService.query(qo);
        model.addAttribute("result",result);
        return "vedioAuth/list";
    }

    /**
     * 后台视频认证审核
     * @param loginInfoValue
     * @param remark
     * @param state
     * @return
     */
    @RequestMapping("vedioAuth_audit")
    @ResponseBody
    public AjaxResult vedioAuth_audit(Long loginInfoValue, String remark, int state){
        videoService.audit(loginInfoValue,remark,state);
        return new AjaxResult();
    }

    /**
     * 字段自动补全
     * @param keyword
     * @return List<Map<String,Object>> JSON对象的数组
     */
    @RequestMapping("vedioAuth_autocomplate")
    @ResponseBody
    public List<Map<String,Object>> autocomplate(String keyword){
        return logininfoService.autocomplate(keyword);
    }
}
