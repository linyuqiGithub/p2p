package com.xmg.p2p.bussiness.domain;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.BaseDomain;
import com.xmg.p2p.base.util.Consts;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户变化流水对象
 */
@Setter
@Getter
public class AccountFlow extends BaseDomain {
     private int accountActionType;//流水变化的类型
     private BigDecimal amount;//流水金额
     private String note;
     private BigDecimal balance;//变化之后的可用金额
     private BigDecimal freezed;//变化之后的冻结金额
     @DateTimeFormat(pattern = "yyyy-MM-dd")
     private Date actionTime;//流水时间
     private Account account;//对应的账户对象

     public String getAccountActionTypeName(){
          if (accountActionType == Consts.ACCOUNT_ACTIONTYPE_RECHARGE_OFFLINE){
               return "线下充值";
          }else if (accountActionType == Consts.ACCOUNT_ACTIONTYPE_WITHDRAW){
               return "提现成功";
          }else if(accountActionType == Consts.ACCOUNT_ACTIONTYPE_BIDREQUEST_SUCCESSFUL){
               return "成功借款";
          }else if (accountActionType == Consts.ACCOUNT_ACTIONTYPE_BID_SUCCESSFUL){
               return "成功投标";
          }else if(accountActionType == Consts.ACCOUNT_ACTIONTYPE_RETURN_MONEY){
               return "还款";
          }else if(accountActionType == Consts.ACCOUNT_ACTIONTYPE_CALLBACK_MONEY){
               return "回款";
          }else if(accountActionType == Consts.ACCOUNT_ACTIONTYPE_CHARGE){
               return "支付平台管理费";
          }else if(accountActionType == Consts.ACCOUNT_ACTIONTYPE_INTEREST_SHARE){
               return "利息管理费";
          }else if(accountActionType == Consts.ACCOUNT_ACTIONTYPE_WITHDRAW_MANAGE_CHARGE){
               return "提现手续费";
          }else if(accountActionType == Consts.ACCOUNT_ACTIONTYPE_BID_FREEZED){
               return "投标冻结金额";
          }else if(accountActionType == Consts.ACCOUNT_ACTIONTYPE_BID_UNFREEZED){
               return "充值手续费";
          }else if(accountActionType == Consts.ACCOUNT_ACTIONTYPE_WITHDRAW_FREEZED){
               return "提现申请冻结金额";
          }else if(accountActionType == Consts.ACCOUNT_ACTIONTYPE_WITHDRAW_UNFREEZED){
               return "提现申请失败取消冻结金额";
          }
          return "无信息";
     }
}
