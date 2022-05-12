package com.xmg.p2p.website.controller;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.bussiness.domain.ExpAccount;
import com.xmg.p2p.bussiness.service.IExpAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 前台注册控制器
 */
@Controller
public class RegisterController {

    @Autowired
    private ILogininfoService logininfoService;
    @Autowired
    private IExpAccountService expAccountService;

    @RequestMapping("regist")
    @ResponseBody
    public AjaxResult regist(String username, String password){
        try {
            logininfoService.regist(username,password);
            return new AjaxResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new AjaxResult(e.getMessage());
        }
    }
    @RequestMapping("check")
    @ResponseBody
    public boolean check(String username){
       return logininfoService.check(username);
    }

    @RequestMapping("login")
    @ResponseBody
    public AjaxResult login(String username, String password, HttpServletRequest request){
       //由于当前方法只会给前台用户调用，而service的login方法前台后台用户都会调用，所以调用login方法时需要传递参数TYPE_CLIENT说明该用户是前台用户
        Logininfo logininfo = logininfoService.login(username, password, request.getRemoteAddr(),Logininfo.TYPE_CLIENT);
        if (logininfo!= null){
        //判断该用户是否有体验金账户
           ExpAccount expAccount = expAccountService.getExp(logininfo.getId());
            if (expAccount == null){
                //没有则创建一个体验金账户
                expAccountService.createExp(logininfo.getId());
            }
        return new AjaxResult();
        }
        return new AjaxResult("用户名或密码错误");
    }


}
