package com.xmg.p2p.base.domain;

import com.xmg.p2p.base.util.Consts;
import com.xmg.p2p.base.util.MD5;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 用户账户信息
 */
@Setter@Getter
public class Account extends BaseDomain {
   //版本信息
   private int version;
   //交易密码
   private String tradePassword;
   //可用余额
   private BigDecimal usableAmount = Consts.ZERO;
   //冻结金额
   private BigDecimal freezedAmount = Consts.ZERO;
   //待收利息
   private BigDecimal unReceiveInterest = Consts.ZERO;
   //待收本金
   private BigDecimal unReceivePrincipal = Consts.ZERO;
   //待还金额(本息)
   private BigDecimal unReturnAmount = Consts.ZERO;
   //剩余授信额度
   private BigDecimal remainBorrowLimit = Consts.INIT_BORROW_LIMIT;
   //授信额度
   private BigDecimal borrowLimit = Consts.INIT_BORROW_LIMIT;
   //数据校验码
   private String verifyCode;
   //返回账户总额
   public BigDecimal getAmount(){
      //可用余额+冻结金额+待收利息+待收本金
      return usableAmount.add(freezedAmount).add(unReceiveInterest).add(unReceivePrincipal);
   }
   //重写getter方法，通过两个字段计算出校验码
   public String getVerifyCode(){
      return MD5.encode(usableAmount.hashCode()+" "+freezedAmount.hashCode());
   }
   //通过数据库现有数据计算后和校验码进行匹配，检测数据是否被篡改
   public boolean checkVerifyCode(){
      return MD5.encode(usableAmount.hashCode()+" "+freezedAmount.hashCode()).equals(verifyCode);
   }
}
