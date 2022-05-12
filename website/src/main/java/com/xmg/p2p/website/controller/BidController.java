package com.xmg.p2p.website.controller;

import com.xmg.p2p.bussiness.service.IBidRequestService;
import com.xmg.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * 投标控制器
 */
@Controller
public class BidController {

    @Autowired
    private IBidRequestService bidRequestService;

    /**
     * 投标操作
     * @param bidRequestId
     * @param amount
     * @return
     */
    @RequestMapping("borrow_bid")
    @ResponseBody
    public AjaxResult borrow_bid(Long bidRequestId, BigDecimal amount){
        bidRequestService.bid(bidRequestId,amount);
        return new AjaxResult();
    }
}
