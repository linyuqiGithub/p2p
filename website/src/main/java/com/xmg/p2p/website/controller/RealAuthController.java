package com.xmg.p2p.website.controller;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.website.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

/**
 * 前台实名认证控制器
 */
@Controller
public class RealAuthController {

    @Autowired
    private IRealAuthService realAuthService;
    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private ServletContext servletContext;

    /**
     * 实名认证页面
     * @param model
     * @return
     */
    @RequestMapping("realAuth")
    public String realAuth(Model model){
        Userinfo userinfo = userinfoService.get(UserContext.getCurrent().getId());
        //没有通过实名认证,且realAuthId为空
        //申请页面
        if(!userinfo.getIsRealAuth() && userinfo.getRealAuthId() == null){
            return "realAuth";
        }else if(!userinfo.getIsRealAuth() && userinfo.getRealAuthId() != null){
            //没有通过了实名认证,但realAuthId不为空
            //审核中界面
            model.addAttribute("auditing",true);
            return "realAuth_result";
        }else {
            //通过实名认证
            //审核完成界面
            RealAuth realAuth = realAuthService.get(userinfo.getRealAuthId());
            model.addAttribute("realAuth",realAuth);
            model.addAttribute("auditing",false);
            return "realAuth_result";
        }

    }

    /**
     * 实名认证申请
     * @param vo
     * @return
     */
    @RequestMapping("realAuth_save")
    @ResponseBody
    public AjaxResult realAuth_save(RealAuth vo){
        //RealAuth对象只能作为数据的包装，不能直接将这个RealAuth进行保存，防止用户在网页的地址栏中直接输入数据修改不允许修改的值
        realAuthService.apply(vo);
        return new AjaxResult();
    }

    /**
     * 图片上传控制器
     * @param image
     * @return
     */
    @RequestMapping("uploadImage")
    @ResponseBody
    public String uploadImage(MultipartFile image){
        String realPath = servletContext.getRealPath("/upload");
        String fileName = UploadUtil.upload(image,realPath);
        return "/upload/"+fileName;
    }
}
