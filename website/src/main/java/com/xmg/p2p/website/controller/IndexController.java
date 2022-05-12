package com.xmg.p2p.website.controller;

import com.xmg.p2p.bussiness.domain.BidRequest;
import com.xmg.p2p.bussiness.query.BidRequestQueryObject;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.bussiness.service.IBidRequestService;
import com.xmg.p2p.base.util.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 前台首页控制器
 */
@Controller
public class IndexController {

    @Autowired
    private IBidRequestService bidRequestService;

    /**
     * 首页
     * @param qo 显示出招标中、还款中和已还清三种类型的借款对象，且只显示全部的前5个
     * @param model
     * @return
     */
    @RequestMapping("index")
    public String index(@ModelAttribute("qo")BidRequestQueryObject qo, Model model){
        qo.setBidRequestStates(new int[]{Consts.BIDREQUEST_STATE_BIDDING,//招标中
                Consts.BIDREQUEST_STATE_PAYING_BACK,//还款中
                Consts.BIDREQUEST_STATE_COMPLETE_PAY_BACK});//已还清
        //根据借款的状态进行排序
        qo.setOrderBy("bidRequestState");
        //升序
        qo.setOrderByType("ASC");
        //只显示前5个借款对象
        qo.setPageSize(5);
        PageResult result = bidRequestService.query(qo);
        List<BidRequest> bidRequests = (List<BidRequest>) result.getData();
        model.addAttribute("bidRequests",bidRequests);
        return "main";
    }

    /**
     * 加载投资列表外面的框架
     * @return
     */
    @RequestMapping("invest")
    public String invest(){
        //第一次进入invest页面后会自动提交一次表单,发送ajax请求invest_list.do，拉取投资列表里面的内容
        return "invest";
    }

    /**
     * 加载投资列表里面的数据内容
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("invest_list")  //由于列表外面的框架没有刷新，所以不需要做高级查询的数据回显，所以不需要@ModelAttribute("qo")
    public String investList(BidRequestQueryObject qo, Model model){
        //如果列表高级查询中选择的是查看全部标的，那只能查看以下3种标的
        if(qo.getBidRequestState() == -1) {
            qo.setBidRequestStates(new int[]{Consts.BIDREQUEST_STATE_BIDDING,
                    Consts.BIDREQUEST_STATE_PAYING_BACK,
                    Consts.BIDREQUEST_STATE_COMPLETE_PAY_BACK});
        }
        qo.setOrderBy("bidRequestState");
        qo.setOrderByType("ASC");
        PageResult result = bidRequestService.query(qo);
         model.addAttribute("result",result);
        return "invest_list";
    }
}
