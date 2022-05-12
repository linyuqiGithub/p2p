package com.xmg.p2p.website.controller;

import com.xmg.p2p.base.domain.*;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.base.query.UserFileQueryObject;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.base.util.Consts;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.bussiness.domain.BidRequest;
import com.xmg.p2p.bussiness.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 前台借款相关的控制器
 */
@Controller
public class BorrowController {

    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private IBidRequestService bidRequestService;
    @Autowired
    private IRealAuthService realAuthService;
    @Autowired
    private IUserFileService userFileService;

    /**
     * 借款首页
     * @param model
     * @return
     */
    @RequestMapping("borrow")
    public String borrowIndex(Model model){
        Logininfo current = UserContext.getCurrent();
        //用户有登陆，跳转到动态的借款页面
        if(current != null){
            Account account = accountService.get(current.getId());
            Userinfo userinfo = userinfoService.get(current.getId());
            model.addAttribute("account",account);
            model.addAttribute("userinfo",userinfo);
            model.addAttribute("creditBorrowScore", Consts.BASIC_BORROW_SCORE);
            return "borrow";
        }
        //用户没有登陆，跳转到静态的借款页面
        return "redirect:/borrow.html";
    }

    /**
     * 申请借款页面
     * @param model
     * @return
     */
    @RequestMapping("borrowApply")
    public String borrowApply(Model model){
        Userinfo userinfo = userinfoService.get(UserContext.getCurrent().getId());
        Logininfo current = UserContext.getCurrent();
        Account account = accountService.get(current.getId());
        if(userinfo.getIsBasicInfo()//用户是否填写基本资料
                && userinfo.getIsRealAuth()//用户是否完成实名认证
                && userinfo.getScore() >= Consts.BASIC_BORROW_SCORE//用户满足最低风控借款分数
                && userinfo.getIsVedioAuth()){//是否完成视频认证
            if(!userinfo.getHasbidRequetInProcess()){//用户没有借款在审核流程中
                model.addAttribute("account",account);
                //最小借款金额
                model.addAttribute("minBidRequestAmount",Consts.SMALLEST_BIDREQUEST_AMOUNT);
                //最小投标金额
                model.addAttribute("minBidAmount",Consts.SMALLEST_BID_AMOUNT);
                //最小借款利息
                model.addAttribute("minCurrentRate",Consts.SMALLEST_CURRENT_RATE);
                //最大借款利息
                model.addAttribute("maxCurrentRate",Consts.MAX_CURRENT_RATE);
                return "borrow_apply";
            }else {
                //用户有借款在审核流程中
                return "borrow_apply_result";
            }
        }else {
            //用户还没有满足申请借款的条件
            model.addAttribute("account",account);
            model.addAttribute("userinfo",userinfo);
            model.addAttribute("creditBorrowScore", Consts.BASIC_BORROW_SCORE);
            //跳转回借款认证首页
            return "borrow";
        }
    }

    /**
     * 提交借款申请
     * @param vo
     * @return
     */
     @RequestMapping("borrow_apply")
     @ResponseBody
      public AjaxResult borrow_apply(BidRequest vo){
        bidRequestService.apply(vo);
        return new AjaxResult();
     }

    /**
     * 前台借款明细
     * @param id
     * @param model
     * @return
     */
     @RequestMapping("borrow_info")
     public String borrow_info(Long id,Model model){
         //借款人的综合信息
         BidRequest bidRequest = bidRequestService.get(id);
         Userinfo userinfo = userinfoService.get(bidRequest.getCreateUser().getId());
         RealAuth realAuth = realAuthService.get(userinfo.getRealAuthId());
         UserFileQueryObject qo = new UserFileQueryObject();
         qo.setState(UserFile.PASS_AUDI);
         qo.setApplierId(userinfo.getId());
         qo.setPageSize(QueryObject.NO_PAGE);
         List<UserFile> userFiles = userFileService.queryForList(qo);
         //如果登陆了
         if(UserContext.getCurrent() != null) {
             //当前用户不是当前标的借款人
             if (!bidRequest.getCreateUser().getId().equals(UserContext.getCurrent().getId())) {
                 model.addAttribute("self", false);
                 //查询出当前用户的账户信息
                 Account account = accountService.get(UserContext.getCurrent().getId());
                 model.addAttribute("account", account);
             }else {
                 //登陆了但当前用户是当前标的借款人
                 model.addAttribute("self",true);
             }
         }
         model.addAttribute("bidRequest",bidRequest);
         model.addAttribute("userinfo",userinfo);

         model.addAttribute("realAuth",realAuth);
         model.addAttribute("userFiles",userFiles);

         return "borrow_info";
     }




}
