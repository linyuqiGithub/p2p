package com.xmg.p2p.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 模拟短信平台
 */
@Controller
public class MeilianController {
    @RequestMapping("sendSms")
    @ResponseBody
    public String sendSms(String username,String password,String apikey,String phoneNumber,String content){
        System.out.println(content);
        return "success:msgid";
    }
}
