package com.xmg.p2p.website.controller;

import com.xmg.p2p.base.util.UserContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {

    @RequestMapping("/logout")
    public String logout(){
        UserContext.invalidateSession();
        return "redirect:login.html";
    }
}
