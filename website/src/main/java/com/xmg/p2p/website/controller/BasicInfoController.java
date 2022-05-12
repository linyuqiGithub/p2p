package com.xmg.p2p.website.controller;

import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.ISystemDicService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 个人用户资料控制器
 */
@Controller
public class BasicInfoController {
     @Autowired
     private IUserinfoService userinfoService;
     @Autowired
     private ISystemDicService systemDicService;

    /**
     * 个人用户资料页面显示
     * @param model
     * @return
     */
    @RequestMapping("basicInfo")
    public String basicInfo(Model model){
        Userinfo userinfo = userinfoService.get(UserContext.getCurrent().getId());
        List<SystemDictionaryItem> educationBackgrounds = systemDicService.listBySn("educationBackground");
        List<SystemDictionaryItem> incomeGrades = systemDicService.listBySn("incomeGrade");
        List<SystemDictionaryItem> marriages = systemDicService.listBySn("marriage");
        List<SystemDictionaryItem> kidCounts = systemDicService.listBySn("kidCount");
        List<SystemDictionaryItem> houseConditions = systemDicService.listBySn("houseCondition");
        model.addAttribute("userinfo",userinfo);
        model.addAttribute("educationBackgrounds",educationBackgrounds);
        model.addAttribute("incomeGrades",incomeGrades);
        model.addAttribute("marriages",marriages);
        model.addAttribute("kidCounts",kidCounts);
        model.addAttribute("houseConditions",houseConditions);
        return "userInfo";
    }

    /**
     * 个人用户资料的更新
     * @param vo
     * @return
     */
     @RequestMapping("basicInfo_save")
     @ResponseBody
     public AjaxResult basicInfo_save(Userinfo vo){
         //传递过来的Userinfo只能看成对需要保存的数据的包装，不能直接将Userinfo进行保存，否则用户就可以通过前台地址栏保存Userinfo中不允许修改的数据
         //因此不能直接调用userinfo的update方法，需要重写一个针对特定数据的更新方法
         userinfoService.updateBaseInfo(vo);
         return new AjaxResult();
     }
}
