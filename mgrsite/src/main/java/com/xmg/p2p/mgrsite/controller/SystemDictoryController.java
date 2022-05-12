package com.xmg.p2p.mgrsite.controller;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.SystemDictionaryItemQueryObject;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;
import com.xmg.p2p.base.service.ISystemDicService;
import com.xmg.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 数据字典控制器
 */
@Controller
public class SystemDictoryController {

    @Autowired
    private ISystemDicService systemDicService;

    /**
     * 数据字典目录分页列表
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("systemDictionary_list")
    public String systemDictionary_list(@ModelAttribute("qo") SystemDictionaryQueryObject qo, Model model){
        PageResult result = systemDicService.query(qo);

        model.addAttribute("result",result);
        return "systemdic/systemDictionary_list";
    }

    /**
     * 数据字典分类的添加或修改
     * @param systemDictionary
     * @return
     */
    @RequestMapping("systemDictionary_saveOrUpdate")
    @ResponseBody
    public AjaxResult systemDictionary_saveOrUpdate(SystemDictionary systemDictionary){
          systemDicService.saveOrUpdate(systemDictionary);
          return new AjaxResult();
    }

    /**
     * 数据字典明细分页页表
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("systemDictionaryItem_list")
    public String systemDictionaryItem_list(@ModelAttribute("qo")SystemDictionaryItemQueryObject qo,Model model){
        PageResult result = systemDicService.queryItem(qo);
        List<SystemDictionary> systemDictionaryGroups = systemDicService.getAllDictionary();
        model.addAttribute("result",result);
        model.addAttribute("systemDictionaryGroups",systemDictionaryGroups);

        return "systemdic/systemDictionaryItem_list";
    }

    /**
     * 数据字典明细的添加和修改
     * @param systemDictionaryItem
     * @return
     */
    @RequestMapping("systemDictionaryItem_saveOrUpdate")
    @ResponseBody
    public AjaxResult systemDictionaryItem_saveOrUpdate(SystemDictionaryItem systemDictionaryItem){
        systemDicService.saveOrUpdateItem(systemDictionaryItem);
        return new AjaxResult();
    }
}
