package com.xmg.p2p.website.controller;

import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.service.IUserFileService;
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
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 前台风控材料控制器
 */
@Controller
public class UserFileController {

    @Autowired
    private HttpSession session;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private IUserFileService userFileService;

    /**
     * 风控认证首页
     * @param model
     * @return
     */
    @RequestMapping("userFile")
    public String userFile(Model model){
        //获取当前用户没有对风控文件类型分组的风控文件
        List<UserFile> userFiles = userFileService.listNoFileType(UserContext.getCurrent().getId());
        //如果有没有进行风控文件分类的风控文件,跳转到提交页面进行分类
         if(userFiles.size()>0){
             //获取所以风控文件分类组别
             List<SystemDictionaryItem> fileTypes = userFileService.listFileTypeBySn("userFileType");
             model.addAttribute("fileTypes",fileTypes);
             model.addAttribute("userFiles",userFiles);
             return "userFiles_commit";
         }
         //如果当前用户的所有风控文件都已经完成了分类
         //查询当前用户的所有已经完成风控文件类型分组的风控文件
         userFiles = userFileService.listHasFileType(UserContext.getCurrent().getId());
         String sessionid = session.getId();
         //传递参数sessionid解决火狐浏览器使用flash的uploadify是相当于重新打开一个浏览器导致session丢失的问题
         //解决调用userFileUpload方法时设置申请用户信息是session丢失导致无法获取UserContext.getCurrent()
         model.addAttribute("sessionid",sessionid);
         model.addAttribute("userFiles",userFiles);
        return "userFiles";
    }

    /**
     * 申请一个风控材料对象
     * @param file
     */
    @RequestMapping("userFileUpload")
    @ResponseBody
    public void userFileUpload(MultipartFile file){
        String basePath = servletContext.getRealPath("/upload");
        String fileName = UploadUtil.upload(file,basePath);
        fileName = "/upload/" + fileName;
        //申请操作主要是生成风控文件对象并设置参数,最后保存导数据库中
        userFileService.apply(fileName);
    }

    /**
     * 提交风控材料申请,设置对应风控材料的分组
     * @param id
     * @param fileType
     * @return
     */
    @RequestMapping("userFile_selectType")
    @ResponseBody
    public AjaxResult userFile_selectType(Long[] id,Long[] fileType){
        //根据id获取风控文件对象,设置风控文件对象的风控文件类型,并更新,遍历操作
        if(id!=null && fileType!=null && id.length == fileType.length) {
            userFileService.updateFileType(id, fileType);
            return new AjaxResult();
        }else {
            return new AjaxResult("参数不合法,请重新操作");
        }
    }


}
