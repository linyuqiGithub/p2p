package com.xmg.p2p.mgrsite.controller;

import com.xmg.p2p.base.query.IpLogQueryObject;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IIpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台登陆日志控制器
 */
@Controller
public class IplogController {

    @Autowired
    private IIpLogService ipLogService;

    @RequestMapping("ipLog")
    //ModelAttribute自己设计名字qo，这样将IpLogQueryObject存入Model时，key就是qo,方便回显
    public String ipLog(@ModelAttribute("qo") IpLogQueryObject qo, Model model){
        //后台可以看到所有的登录记录
        //qo.setUsername(UserContext.getCurrent().getUsername());
        PageResult result = ipLogService.query(qo);
        model.addAttribute("result",result);
        //默认情况下，springmvc会自动将IpLogQueryObject存入Model中，不需要自己另外存，key为IpLogQueryObject小写
        //model.addAttribute("qo",qo);
        return "ipLog/list";
    }
}
