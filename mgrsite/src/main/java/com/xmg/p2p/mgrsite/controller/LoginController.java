package com.xmg.p2p.mgrsite.controller;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台登陆控制器
 */
@Controller
public class LoginController {

    @Autowired
    private ILogininfoService logininfoService;


    @RequestMapping("/login")
    @ResponseBody
    public AjaxResult login(String username, String password, HttpServletRequest request){
        String ip = request.getRemoteAddr();
        //传递参数TYPE_MANAGER告诉login方法当前登陆用户为后台用户
        //如果此时使用前台用户的账号登陆后台，由于前台用户的userType是TYPE_CLIENT,此时传递的参数是TYPE_MANAGER，查表后不匹配,导致不匹配，无法登陆
        Logininfo logininfo = logininfoService.login(username, password, ip, Logininfo.TYPE_MANAGER);
         if (logininfo != null){
          return new AjaxResult();
        }else {
            return new AjaxResult("用户名密码错误");
        }

    }

    @RequestMapping("/index")
    public String index(){
        return "main";
    }



}
