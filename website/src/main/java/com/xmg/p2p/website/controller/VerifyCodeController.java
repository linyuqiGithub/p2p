package com.xmg.p2p.website.controller;

import com.xmg.p2p.base.service.IVerifyCodeService;
import com.xmg.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 发送验证码控制器
 */
@Controller
public class VerifyCodeController {

    @Autowired
    IVerifyCodeService verifyCodeService;

    /**
     * 发送验证码
     * @param phoneNumber
     * @return
     */
    @RequestMapping("sendVerifyCode")
    @ResponseBody
    public AjaxResult sendVerifyCode(String phoneNumber){
        try {
            verifyCodeService.sendVerifyCode(phoneNumber);
            return new AjaxResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new AjaxResult(e.getMessage());
        }
        /*return verifyCodeService.sendVerifyCode(phoneNumber);*/
    }

    /**
     * 绑定手机
     * @param phoneNumber
     * @param verifyCode
     * @return
     */
    @RequestMapping("bindPhone")
    @ResponseBody
    public AjaxResult bindPhone(String phoneNumber,String verifyCode) {
        try {
            verifyCodeService.bindPhone(phoneNumber,verifyCode);
            return new AjaxResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new AjaxResult(e.getMessage());
        }
    }

    /**
     * 发送验证邮件
     * @param email
     * @return
     */
    @RequestMapping("sendEmail")
    @ResponseBody
    public AjaxResult sendEmail(String email){
        try {
            verifyCodeService.sendEmail(email);
            return new AjaxResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new AjaxResult(e.getMessage());
        }
    }

    /**
     * 绑定邮箱
     * @param key
     * @param model
     * @return
     */
    @RequestMapping("bindEmail")
    public String bindEmail(String key, Model model){
        try {
            boolean ret = verifyCodeService.bindEmail(key);
            if(ret) {
                model.addAttribute("success", true);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            model.addAttribute("success",false);
            model.addAttribute("msg",e.getMessage());
        }
        return "checkmail_result";
    }
}
