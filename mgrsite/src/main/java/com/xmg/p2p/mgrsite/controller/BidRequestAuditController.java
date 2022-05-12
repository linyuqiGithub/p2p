package com.xmg.p2p.mgrsite.controller;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.base.query.UserFileQueryObject;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.base.util.Consts;
import com.xmg.p2p.bussiness.domain.BidRequest;
import com.xmg.p2p.bussiness.domain.BidRequestAudit;
import com.xmg.p2p.bussiness.query.BidRequestQueryObject;
import com.xmg.p2p.bussiness.service.IBidRequestAuditService;
import com.xmg.p2p.bussiness.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 后台借款相关审核控制器
 */
@Controller
public class BidRequestAuditController {

    @Autowired
    private IBidRequestAuditService bidRequestAuditService;
    @Autowired
    private IBidRequestService bidRequestService;
    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private IRealAuthService realAuthService;
    @Autowired
    private IUserFileService userFileService;

    /**
     * 借款发标前审核列表
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("bidrequest_publishaudit_list")
    public String bidrequest_publishaudit_list(@ModelAttribute("qo")BidRequestQueryObject qo, Model model){
        //设置查询条件为待发布的借款
        qo.setBidRequestState(Consts.BIDREQUEST_STATE_PUBLISH_PENDING);
        //查询的是所有的借款对象(待发布的借款)
        PageResult result = bidRequestService.query(qo);
        model.addAttribute("result",result);
        return "bidrequest/publish_audit";
    }

    /**
     * 发标前审核操作
     * @param id
     * @param remark
     * @param state
     * @return
     */
    @RequestMapping("bidrequest_publishaudit")
    @ResponseBody
    public AjaxResult bidrequest_publishaudit_list(Long id,String remark,int state){
      bidRequestService.publishAudit(id,remark,state);
      return new AjaxResult();
    }

    /**
     * 借款明细
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("borrow_info")
    public String borrow_info(Long id,Model model){
        //借款对象
        BidRequest bidRequest = bidRequestService.get(id);
        //借款人信息
        Userinfo userinfo = userinfoService.get(bidRequest.getCreateUser().getId());
        //查询一个借款的所有借款审核对象
        List<BidRequestAudit> audits = bidRequestAuditService.listByBidRequestId(id);
        //借款人的实名认证对象(通过借款人信息获取对应的实名认证对象id)
        RealAuth realAuth = realAuthService.get(userinfo.getRealAuthId());
        //借款人的所有的审核通过的风控材料(不做分页)
        UserFileQueryObject qo = new UserFileQueryObject();
        qo.setApplierId(userinfo.getId());
        qo.setPageSize(QueryObject.NO_PAGE);
        qo.setState(UserFile.PASS_AUDI);
        List<UserFile> userFiles = userFileService.queryForList(qo);
        model.addAttribute("bidRequest",bidRequest);
        model.addAttribute("userinfo",userinfo);
        model.addAttribute("audits",audits);
        model.addAttribute("realAuth",realAuth);
        model.addAttribute("userFiles",userFiles);
        return "bidrequest/borrow_info";
    }

    /**
     * 满标一审审核列表
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("bidrequest_audit1_list")
    public String audit1List(BidRequestQueryObject qo,Model model){
        qo.setBidRequestState(Consts.BIDREQUEST_STATE_APPROVE_PENDING_1);
        PageResult result = bidRequestService.query(qo);
        model.addAttribute("result",result);
        return "bidrequest/audit1";
    }

    /**
     * 满标一审审核操作
     * @param id
     * @param remark
     * @param state
     * @return
     */
    @RequestMapping("bidrequest_audit1")
    @ResponseBody
    public AjaxResult audit1(Long id,String remark,int state){
        bidRequestService.audit1(id,remark,state);
        return new AjaxResult();
    }

    /**
     * 满标二审审核列表
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("bidrequest_audit2_list")
    public String audit2List(BidRequestQueryObject qo,Model model){
        qo.setBidRequestState(Consts.BIDREQUEST_STATE_APPROVE_PENDING_2);
        PageResult result = bidRequestService.query(qo);
        model.addAttribute("result",result);
        return "bidrequest/audit2";
    }

    /**
     * 满标二审审核操作
     * @param id
     * @param remark
     * @param state
     * @return
     */
    @RequestMapping("bidrequest_audit2")
    @ResponseBody
    public AjaxResult audit2(Long id,String remark,int state){
        bidRequestService.audit2(id,remark,state);
        return new AjaxResult();
    }
}
