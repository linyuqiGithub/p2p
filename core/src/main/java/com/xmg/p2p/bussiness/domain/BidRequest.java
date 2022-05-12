package com.xmg.p2p.bussiness.domain;

import com.alibaba.fastjson.JSON;
import com.xmg.p2p.base.domain.BaseDomain;
import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.util.Consts;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static com.xmg.p2p.base.util.Consts.*;

/**
 * 借款对象模型(由多个投标对象组成)
 */
@Setter
@Getter
public class BidRequest extends BaseDomain {

   private int version;//版本信息(乐观锁)
   private int returnType;//还款方式
   private int bidRequestType;//借款类型，在我们项目中，就是信用借款
   private int bidRequestState;//标的的状态
   private BigDecimal bidRequestAmount;//借款的金额
   private BigDecimal currentRate;//借款的年利率
   private BigDecimal minBidAmount;//借款允许的最小的投标金额
   private int monthes2Return;//借款的还款时间
   private int bidCount;//借款现在已经有多少次投标
   private BigDecimal totalRewardAmount;//总回报金额(标总利息的金额)
   private BigDecimal currentSum = Consts.ZERO;//当前已经投了多少钱了
   private String title;//借款标题
   private String description;//借款描述
   private String note;//风控评审意见
   private Date disableDate;//招标到期时间
   private int disableDays;//招标天数
   private Logininfo createUser;//借款人
   private List<Bid> bids = new ArrayList<Bid>();//标的的投标记录
   private Date applyTime;//申请时间
   private Date publishTime;//发布时间

   public String getJsonString(){
      Map<String,Object> map = new HashMap<String, Object>();
      map.put("id",id);
      map.put("username",createUser.getUsername());
      map.put("title",title);
      map.put("bidRequestAmount",bidRequestAmount);
      map.put("currentRate",currentRate);
      map.put("monthes2Return",monthes2Return);
      map.put("returnType",returnType);
      map.put("totalRewardAmount",totalRewardAmount);
      return JSON.toJSONString(map);
   }

   public String getBidRequestStateDisplay() {
      switch (this.bidRequestState) {
         case BIDREQUEST_STATE_PUBLISH_PENDING:
            return "待发布";
         case BIDREQUEST_STATE_BIDDING:
            return "招标中";
         case BIDREQUEST_STATE_UNDO:
            return "已撤销";
         case BIDREQUEST_STATE_BIDDING_OVERDUE:
            return "流标";
         case BIDREQUEST_STATE_APPROVE_PENDING_1:
            return "满标一审";
         case BIDREQUEST_STATE_APPROVE_PENDING_2:
            return "满标二审";
         case BIDREQUEST_STATE_REJECTED:
            return "满标审核被拒";
         case BIDREQUEST_STATE_PAYING_BACK:
            return "还款中";
         case BIDREQUEST_STATE_COMPLETE_PAY_BACK:
            return "完成";
         case BIDREQUEST_STATE_PAY_BACK_OVERDUE:
            return "逾期";
         case BIDREQUEST_STATE_PUBLISH_REFUSE:
            return "发标拒绝";
         default:
            return "";
      }
   }

   public String getReturnTypeDisplay(){
     if(returnType == Consts.RETURN_TYPE_MONTH_INTEREST_PRINCIPAL){
        return "按月分期";
     }else
        return "按月到期";
     }
   //借款金额还差多少才投满
   public BigDecimal getRemainAmount(){
      return bidRequestAmount.subtract(currentSum).setScale(2, RoundingMode.HALF_UP);
   }
   //投资进度=已投金额/借款金额百分比(一定是整数)
   public BigDecimal getPersent(){
      return currentSum.divide(bidRequestAmount,2,RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
   }

}
