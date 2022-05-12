package com.xmg.p2p.website.controller;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.query.IpLogQueryObject;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IIpLogService;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.website.util.RequiredLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前台登录日志控制器
 */
@Controller
public class IpLogController {
    @Autowired
    IIpLogService ipLogService;
    /*@RequiredLogin
    @RequestMapping("ipLog")
    //ModelAttribute自己设计名字qo，这样将IpLogQueryObject存入Model时，key就是qo
    public String ipLog(@ModelAttribute("qo")IpLogQueryObject qo, Model model){
        //用户只能看到自己的的前台登录记录
        qo.setUsername(UserContext.getCurrent().getUsername());
        qo.setUserType(Logininfo.TYPE_CLIENT);

        PageResult result = ipLogService.query(qo);
        qo.setCurrentPage(1);
        //默认情况下，springmvc会自动将IpLogQueryObject存入Model中，不需要自己另外存，key为IpLogQueryObject小写
        //model.addAttribute("qo",qo);
        model.addAttribute("result",result);
        return "iplog_list";
    }*/ //以上这种方式是常用的高级查询，下面这种方式是将高级查询中外面的框架和里面列表的内容进行分开显示，每次点击高级查询时，仅发送iplog_list请求，刷新列表中的内容
        //列表外面的内容，包括高级查询中的按钮，都不会重新提交并刷新，所以也不存在数据回显的问题

    @RequiredLogin
    @RequestMapping("ipLog")
    public String ipLog(){
        return "iplog";
    }

    @RequestMapping("iplog_list")
    public String iplog_list(IpLogQueryObject qo, Model model){
        //用户只能看到自己的的前台登录记录
        qo.setUsername(UserContext.getCurrent().getUsername());
        qo.setUserType(Logininfo.TYPE_CLIENT);

        PageResult result = ipLogService.query(qo);
        qo.setCurrentPage(1);
        model.addAttribute("result",result);
        return "iplog_list";
    }
}
